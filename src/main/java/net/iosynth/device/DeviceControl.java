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

/**
 * @author rradev
 *
 */
public class DeviceControl {
	protected BlockingQueue<Device>   delayQueue;
	protected List<Runnable> devsFixedList;
	protected List<Runnable> devsDelayList;
	protected List<ScheduledFuture<?>> devsHandleFixedList;
	protected List<ScheduledFuture<?>> devsHandleDelayList;

	protected ScheduledExecutorService scheduler;
	private final Logger logger = Logger.getLogger(DeviceControl.class.getName());
	
	/**
	 * @param threads 
	 */
	public DeviceControl(int threads){
		scheduler = Executors.newScheduledThreadPool(threads);
		devsFixedList = new ArrayList<Runnable>(0);
		devsDelayList = new ArrayList<Runnable>(0);
		this.delayQueue = new LinkedBlockingQueue<Device>();
	}
	
	/**
	 * @param dev
	 * @param msgQueue 
	 */
	public void addDevice(Device dev, BlockingQueue<Message> msgQueue){
		dev.setQueue(msgQueue);
		if (dev.getSampling().getClass() == SamplingFixed.class) {
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
		devsHandleFixedList = new ArrayList<>();
		devsHandleDelayList = new ArrayList<>();
		
		// Devices with fixed sampling interval
		for(final Runnable devR: devsFixedList){
			final Device dev = (Device)devR;
			scheduler.scheduleAtFixedRate(dev, ((SamplingFixed)dev.getSampling()).getJitter(), dev.getSampling().getInterval(), TimeUnit.MILLISECONDS);
		}
		// Devices with variable sampling interval. They have to be re-scheduled each time.
		for(Runnable dev: devsDelayList){
			scheduler.schedule(dev, ((Device)dev).getSampling().getInterval(), TimeUnit.MILLISECONDS);
		}
		
		try {
			long k = 0;
			while (true) { // reschedule variable rate devices
				final Device dev = delayQueue.take();
				if(k%100000==0){
					logger.info("sampling queue: " + delayQueue.size());
				}
				scheduler.schedule(dev, dev.getSampling().getInterval(), TimeUnit.MILLISECONDS);
				k++;
			}
		} catch (InterruptedException ie) {
			logger.log(Level.SEVERE, ie.toString(), ie);
		}

	}
	
}
