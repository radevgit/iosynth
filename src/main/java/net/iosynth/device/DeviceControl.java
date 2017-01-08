package net.iosynth.device;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.iosynth.util.Delay;
import net.iosynth.util.Message;
import net.iosynth.util.Xoroshiro128;

/**
 * @author rradev
 *
 */
public class DeviceControl {
	protected BlockingQueue<Message> msgQueue;
	protected BlockingQueue<Delay>   delayQueue;
	protected List<Runnable> devsFixedList;
	protected List<Runnable> devsDelayList;
	protected List<ScheduledFuture<?>> devsHandleFixedList;
	protected List<ScheduledFuture<?>> devsHandleDelayList;

	protected ScheduledExecutorService scheduler;
	
	/**
	 * @param msgQueue
	 */
	public DeviceControl(BlockingQueue<Message> msgQueue){
		scheduler = Executors.newScheduledThreadPool(5);
		devsFixedList = new ArrayList<Runnable>(0);
		devsDelayList = new ArrayList<Runnable>(0);
		this.msgQueue = msgQueue;
		this.delayQueue = new LinkedBlockingQueue<Delay>();
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
		devsHandleFixedList = new ArrayList<ScheduledFuture<?>>(0);
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
				final Delay delay = delayQueue.take();
				final Runnable r = devsDelayList.get(delay.getId());
				if(k%100000==0){
					System.out.println("arrival queue: " + 	msgQueue.size());
				}
				ScheduledFuture<?> devHandle = scheduler.schedule(r, ((Device)r).getArrival().getInterval(), TimeUnit.MILLISECONDS);
				k++;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
