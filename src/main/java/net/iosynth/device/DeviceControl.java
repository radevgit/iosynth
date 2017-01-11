package net.iosynth.device;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.iosynth.adapter.Message;
import net.iosynth.adapter.MqttAdapter;
import net.iosynth.util.Delay;

/**
 * @author rradev
 *
 */
public class DeviceControl {
	protected BlockingQueue<Message> msgQueue;
	protected BlockingQueue<Device>   delayQueue;
	protected List<Runnable> devsFixedList;
	protected List<Runnable> devsDelayList;
	protected List<ScheduledFuture<?>> devsHandleFixedList;
	protected List<ScheduledFuture<?>> devsHandleDelayList;

	protected ScheduledExecutorService scheduler;
	private final Logger logger = Logger.getLogger(DeviceControl.class.getName());
	
	/**
	 * @param msgQueue
	 */
	public DeviceControl(BlockingQueue<Message> msgQueue){
		scheduler = Executors.newScheduledThreadPool(5);
		devsFixedList = new ArrayList<Runnable>(0);
		devsDelayList = new ArrayList<Runnable>(0);
		this.msgQueue = msgQueue;
		this.delayQueue = new LinkedBlockingQueue<Device>();
	}
	
	/**
	 * @param dev
	 */
	public void addDevice(Device dev){
		dev.setQueue(msgQueue);
		if (dev.getArrival().getClass() == ArrivalFixed.class) {
			devsFixedList.add(dev);
		} else {
			dev.setDelayQueue(delayQueue);
			int delayId = devsDelayList.size();
			dev.setDelayId(delayId);
			devsDelayList.add(dev);
		}
	}
	
	
	/**
	 * 
	 */
	public void forever() {
		// Devices with fixed arrival interval
		for(final Runnable devR: devsFixedList){
			final Device dev = (Device)devR;
			ScheduledFuture<?> devHandle = scheduler.scheduleAtFixedRate(dev, ((ArrivalFixed)dev.getArrival()).getJitter(), dev.getArrival().getInterval(), TimeUnit.MILLISECONDS);
			devsHandleFixedList.add(devHandle);
		}
		// Devices with variable arrival interval. They have to be re-scheduled each time.
		for(Runnable dev: devsDelayList){
			ScheduledFuture<?> devHandle = scheduler.schedule(dev, ((Device)dev).getArrival().getInterval(), TimeUnit.MILLISECONDS);
			//devsHandleDelayList.add(devHandle);
		}
		
		try {
			long k = 0;
			while (true) { // reschedule variable rate devices
				final Device dev = delayQueue.take();
				if(k%100000==0){
					logger.info("arrival queue: " + 	msgQueue.size());
				}
				ScheduledFuture<?> devHandle = scheduler.schedule(dev, dev.getArrival().getInterval(), TimeUnit.MILLISECONDS);
				k++;
			}
		} catch (InterruptedException ie) {
			logger.log(Level.SEVERE, ie.toString(), ie);
		}

	}
	
}
