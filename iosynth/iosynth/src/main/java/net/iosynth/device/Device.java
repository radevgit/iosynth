/**
 * 
 */
package net.iosynth.device;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

import net.iosynth.sensor.Sensor;
import net.iosynth.util.Delay;
import net.iosynth.util.Message;

/**
 * @author rradev
 *
 */
public abstract class Device implements Runnable, Serializable {
	private static final long serialVersionUID = 1L;
	protected String uuid;
	protected long   seed;
	protected BlockingQueue<Message> msgQueue;
	protected BlockingQueue<Delay>   delayQueue;

	protected Arrival arrival;
	protected int copy;
	/**
	 * id in the delay devices list
	 */
	protected int delayId;

	protected List<Sensor> sensors;
	
	final static protected SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * 
	 */
	public Device() {
		this.uuid = UUID.randomUUID().toString();
		this.arrival = new ArrivalFixed();
		this.copy    = 1;
		this.sensors = new ArrayList<>();
	}
	
	/**
	 * Check the correctness of instance parameters after deserialization from json
	 */
	abstract public void checkParameters();
	/**
	 * @return Returns list of replicated devices.
	 */
	abstract public List<Device> replicate();
	
	/**
	 * @param uuid
	 */
	public void setId(String uuid){
		this.uuid = uuid;
	}

	/**
	 * @return uuid
	 */
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

	/**
	 * @return internal message queue
	 */
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

	/**
	 * @return internal delay queue
	 */
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
	
	/**
	 * @return the deviceCopy
	 */
	public int getDeviceCopy() {
		return copy;
	}

	/**
	 * @param copy
	 */
	public void setDeviceCopy(int copy) {
		this.copy = copy;
	}
	
	/**
	 * @return List of sensors
	 */
	public List<Sensor> getSensors() {
		return sensors;
	}

	/**
	 * @param sensors
	 */
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	
	/**
	 * @param name
	 * @param sensor
	 */
	public void addSensor(String name, Sensor sensor){
		sensor.setName(name);
		sensors.add(sensor);
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
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		for(final Sensor sensor : sensors) {
		    sensor.step(1);
		}
		getQueue().add(toJson());
		if (arrival.getClass() != ArrivalFixed.class) {
			long delay = arrival.getInterval();
			//setDealy(delay);
			Delay d = new Delay(getDelayId(), delay);
			getDelayQueue().add(d);
		}
	}
	

	
	/**
	 * @return Json representation of sensor data
	 */
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
