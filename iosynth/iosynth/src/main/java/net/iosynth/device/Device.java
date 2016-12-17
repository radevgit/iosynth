/**
 * 
 */
package net.iosynth.device;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import net.iosynth.app.Message;
import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorDefault;

/**
 * @author ross
 *
 */
public abstract class Device implements Runnable {
	private String uuid;
	private BlockingQueue<Message> msgQueue;
	/**
	 * Timer jitter in milliseconds
	 */
	private long jitter;
	/**
	 * Timer polling rate in milliseconds
	 */
	private long rate;
	
	protected Map<String, Sensor> sens;

	/**
	 * 
	 */
	public Device() {
		this.uuid = UUID.randomUUID().toString();
		this.jitter = ThreadLocalRandom.current().nextInt(0, 200); // At most 2s jitter
		this.rate   = 100*10; // Default 10s polling rate
		this.sens   = new HashMap<>(0);
	}

	public void setId(String uuid){
		this.uuid = uuid;
	}

	public String getId(){
		return uuid;
	}

	/**
	 * Sets message queue to communicate with adapter. 
	 * @param msgQueue
	 */
	public void setQueue(BlockingQueue<Message> msgQueue){
		this.msgQueue = msgQueue;
	}

	public BlockingQueue<Message> getQueue(){
		return msgQueue;
	}

	public void setJitter(long jitter) {
		this.jitter = jitter;
	}
	
	public long getJitter(){
		return jitter;
	}
	
	public long getRate() {
		return rate;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}
	
	public Map<String, Sensor> getSens() {
		return sens;
	}

	public void setSens(Map<String, Sensor> sens) {
		this.sens = sens;
	}

	
	public void addSensor(String name, Sensor sen){
		sens.put(name, sen);
	}
	
	/**
	 * Adds default sensor
	 * @param name
	 */
	public void addSensor(String name){
		sens.put(name, new SensorDefault());
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		System.out.println("Run Run");
	}
}
