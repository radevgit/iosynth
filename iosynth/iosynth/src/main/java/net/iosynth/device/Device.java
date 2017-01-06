/**
 * 
 */
package net.iosynth.device;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
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
public class Device implements Runnable {
	private String uuid;
	private BlockingQueue<Message> msgQueue;
	private BlockingQueue<Delay>   delayQueue;
	/**
	 * Timer jitter in milliseconds
	 */
	//private long jitter;
	/**
	 * Timer polling rate in milliseconds for fixed rate devices
	 */
	//private long rate;
	/**
	 * Delay for random rate devices
	 */
	//private long delay;
	private Arrival arrival;

	/**
	 * id in the delay devices list
	 */
	private int delayId;

	protected List<Sensor> sensors;
	
	final static private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * 
	 */
	public Device() {
		this.uuid = UUID.randomUUID().toString();
		this.sensors   = new ArrayList<>();
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

	/**
	 * @return the arrival
	 */
	public Arrival getArrival() {
		return arrival;
	}

	/**
	 * @param arrival the arrival to set
	 */
	public void setArrival(Arrival arrival) {
		this.arrival = arrival;
	}
	
	public List<Sensor> getSens() {
		return sensors;
	}

	public void setSens(List<Sensor> sens) {
		this.sensors = sens;
	}

	
	public void addSensor(String name, Sensor sen){
		sen.setName(name);
		sensors.add(sen);
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
	 * Adds default sensor
	 * @param name
	 */
	public void addSensor(String name){
		SensorDefault sen = new SensorDefault();
		sen.setName(name);
		sensors.add(sen);
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		for(final Sensor sensor : sensors) {
		    sensor.step(1);
		}
		getQueue().add(toJson());
		if (!arrival.isFixed()) {
			long delay = arrival.getNextInterval();
			//setDealy(delay);
			Delay d = new Delay(getDelayId(), delay);
			getDelayQueue().add(d);
		}
	}
	
	
	public Message toJson(){
		StringBuilder m = new StringBuilder();
		m.append("{");
		Date now = new Date(System.currentTimeMillis());
		m.append("\"time\":\"");
		m.append(fmt.format(now));
		m.append("\",");
		for(final Sensor sensor : sensors) {
		    String name = sensor.getName();
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
