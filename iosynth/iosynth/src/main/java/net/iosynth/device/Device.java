/**
 * 
 */
package net.iosynth.device;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import net.iosynth.sensor.Sensor;
import net.iosynth.sensor.SensorDefault;
import net.iosynth.util.Delay;
import net.iosynth.util.Message;

/**
 * @author ross
 *
 */
public abstract class Device implements Runnable {
	private String uuid;
	private BlockingQueue<Message> msgQueue;
	private BlockingQueue<Delay>   delayQueue;
	/**
	 * Timer jitter in milliseconds
	 */
	private long jitter;
	/**
	 * Timer polling rate in milliseconds for fixed rate devices
	 */
	private long rate;
	/**
	 * Delay for random rate devices
	 */
	private long delay;
	/**
	 * id in the delay devices list
	 */
	private int delayId;

	protected Map<String, Sensor> sens;
	
	final static private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * 
	 */
	public Device() {
		this.uuid = UUID.randomUUID().toString();
		this.jitter = ThreadLocalRandom.current().nextInt(0, 2000); // At most 2s jitter
		this.rate   = 1000*10; // Default 10s polling rate
		this.delay  = ThreadLocalRandom.current().nextLong(10*1000)+1000; // Default delay 1-10 s
		this.sens   = new LinkedHashMap<String, Sensor>(0);   // Ordered Map
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
	
	/**
	 * Sets delay queue for variable rate devices. 
	 * @param delayQueue
	 */
	public void setDelayQueue(BlockingQueue<Delay> delayQueue){
		this.delayQueue = delayQueue;
	}

	public BlockingQueue<Delay> getDelayQueue(){
		return delayQueue;
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

	public long getDelay(){
		return delay;
	}
	
	public void setDealy(long delay){
		this.delay = delay;
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
	 * @return the delayId
	 */
	public int getDelayId() {
		return delayId;
	}

	/**
	 * @param delayId the delayId to set
	 */
	public void setDelayId(int delayId) {
		this.delayId = delayId;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(long delay) {
		this.delay = delay;
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
	abstract public void run();
	
	
	public Message toJson(){
		StringBuilder m = new StringBuilder();
		m.append("{");
		Date now = new Date(System.currentTimeMillis());
		m.append("\"time\":\"");
		m.append(fmt.format(now));
		m.append("\",");
		for(Map.Entry<String, Sensor> sen : sens.entrySet()) {
		    String name = sen.getKey();
		    Sensor sensor = sen.getValue();
		    m.append("\"");
		    m.append(name);
		    m.append("\":");
		    m.append(sensor.getString());
		    m.append(",");
		}
		m.deleteCharAt(m.length()-1) ; // remove last comma
		m.append("}");
		return new Message(getId(), m.toString());
	}
	
}
