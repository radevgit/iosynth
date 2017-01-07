package net.iosynth.device;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.iosynth.util.Delay;
import net.iosynth.util.Message;

public class DeviceControl {
	public BlockingQueue<Message> msgQueue;
	public BlockingQueue<Delay>   delayQueue;
	public List<Runnable> devsFixedList;
	public List<Runnable> devsDelayList;
	public List<ScheduledFuture<?>> devsHandleFixedList;
	public List<ScheduledFuture<?>> devsHandleDelayList;

	public ScheduledExecutorService scheduler;
	
	public DeviceControl(BlockingQueue<Message> msgQueue){
		scheduler = Executors.newScheduledThreadPool(5);
		devsFixedList = new ArrayList<Runnable>(0);
		devsDelayList = new ArrayList<Runnable>(0);
		this.msgQueue = msgQueue;
		this.delayQueue = new LinkedBlockingQueue<Delay>();
	}
	
	/*
	public void addFixed(Device r){
		r.setQueue(msgQueue);
		devsFixedList.add(r);
	}*/
	
	public void addDevice(Device r){
		r.setQueue(msgQueue);
		if (r.getArrival().getClass() == ArrivalFixed.class) {
			devsFixedList.add(r);
		} else {
			r.setDelayQueue(delayQueue);
			int delayId = devsDelayList.size();
			r.setDelayId(delayId);
			devsDelayList.add(r);
		}
	}
	
	
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
			while (true) { // reschedule variable rate devices
				final Delay delay = delayQueue.take();
				final Runnable r = devsDelayList.get(delay.getId());
				ScheduledFuture<?> devHandle = scheduler.schedule(r, ((Device)r).getArrival().getInterval(), TimeUnit.MILLISECONDS);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static void main(String[] args) {
	}

}
