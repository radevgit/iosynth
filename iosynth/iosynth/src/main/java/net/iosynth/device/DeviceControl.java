package net.iosynth.device;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.iosynth.util.Message;

public class DeviceControl {
	public BlockingQueue<Message> msgQueue;
	public List<Runnable> devsList;
	public List<ScheduledFuture<?>> devsHandleList;

	public ScheduledExecutorService scheduler;
	
	public DeviceControl(BlockingQueue<Message> msgQueue){
		scheduler = Executors.newScheduledThreadPool(5);
		devsList = new ArrayList<Runnable>(0);
		this.msgQueue = msgQueue; 
	}
	
	public void add(Device r){
		r.setQueue(msgQueue);
		devsList.add(r);
	}
	
	
	public void forever() {
		devsHandleList = new ArrayList<ScheduledFuture<?>>(0);
		for(Runnable dev: devsList){
			ScheduledFuture<?> sensHandle = scheduler.scheduleAtFixedRate(dev, ((Device)dev).getJitter(), ((Device)dev).getRate(), TimeUnit.MILLISECONDS);
			devsHandleList.add(sensHandle);
		}
	}
	
	
	public static void main(String[] args) {
	}

}
