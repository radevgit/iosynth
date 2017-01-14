/**
 * 
 */
package net.iosynth.device;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

import net.iosynth.adapter.Message;
import net.iosynth.sensor.Sensor;
import net.iosynth.util.Xoroshiro128;

/**
 * @author rradev
 *
 */
public abstract class Device implements Runnable {
	protected String uuid;
	protected BlockingQueue<Message> msgQueue;
	protected BlockingQueue<Device>  delayQueue;

	protected Arrival arrival;
	protected int copy;
	protected double out_of_order;
	protected double message_loss;
	protected volatile List<Message> outOrder; // out of order queue
	protected String json_template;
	protected transient DeviceTemplate deviceTemplate;

	/**
	 * id in the delay devices list
	 */
	protected int delayId;

	protected Sensor sensors[];
	
	protected Xoroshiro128 rnd;
	
	final static protected SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * 
	 */
	public Device() {
		this.uuid = UUID.randomUUID().toString();
		this.arrival = new ArrivalFixed();
		this.copy    = 1;
		this.out_of_order = 0.0;
		this.message_loss = 0.0;
		this.outOrder  = new LinkedList<Message>();
		//this.sensors = new ArrayList<>();
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
	public void setUUID(String uuid){
		this.uuid = uuid;
	}

	/**
	 * @return uuid
	 */
	public String getUUID(){
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
	public void setDelayQueue(BlockingQueue<Device> delayQueue){
		this.delayQueue = delayQueue;
	}

	/**
	 * @return internal delay queue
	 */
	public BlockingQueue<Device> getDelayQueue(){
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
	public Sensor[] getSensors() {
		return sensors;
	}

	/**
	 * @param sensors
	 */
	//public void setSensors(List<Sensor> sensors) {
	//	this.sensors = sensors;
	//}

	/**
	 * @return the rnd
	 */
	public Xoroshiro128 getRnd() {
		return rnd;
	}

	/**
	 * @param rnd the rnd to set
	 */
	public void setRnd(Xoroshiro128 rnd) {
		this.rnd = rnd;
		for(final Sensor sen: sensors){
			sen.setRnd(rnd);
		}
	}
	
	/**
	 * @return the json_template
	 */
	public String getJson_template() {
		return json_template;
	}

	/**
	 * @param json_template the json_template to set
	 */
	public void setJson_template(String json_template) {
		this.json_template = json_template;
	}

	/**
	 * @return the deviceTemplate
	 */
	public DeviceTemplate getDeviceTemplate() {
		return deviceTemplate;
	}

	/**
	 * @param deviceTemplate the deviceTemplate to set
	 */
	public void setDeviceTemplate(DeviceTemplate deviceTemplate) {
		this.deviceTemplate = deviceTemplate;
	}

	
	/**
	 * @param name
	 * @param sensor
	 */
	//public void addSensor(String name, Sensor sensor){
	//	sensor.setName(name);
	//	sensors.add(sensor);
	//}
	
	
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
			if(message_loss > 0.00000001 && rnd.nextDouble() < message_loss) {
				sensor.step(2);
			} else {
				sensor.step(1);
			}
		}
		boolean s1 = out_of_order > 0.00000001 && rnd.nextDouble() < out_of_order;
		boolean s2 = outOrder.size() > 10 || (rnd.nextDouble() < 0.1 && outOrder.size() > 0);
		int a=1;
		if(s1){
			outOrder.add(toJsonMessage()); // out of order queue
		} else {
			if(s2){
				getQueue().add(outOrder.remove(0)); // too long time or too many are delayed
			} else { 
				getQueue().add(toJsonMessage()); // normal case
			}
		}
		
		if (arrival.getClass() != ArrivalFixed.class) {
			getDelayQueue().add(this);
		}
	}
	
	
	/**
	 * @return json message
	 */
	public Message toJsonMessage(){
		if(deviceTemplate == null){
			return toJsonListMessage();
		} else {
			return new Message(getUUID(), getDeviceTemplate().getJson(sensors));
		}
	}
	
	/**
	 * @return Json representation of sensor data
	 */
	public Message toJsonListMessage(){
		// TODO: performance improvement
		StringBuilder m = new StringBuilder();
		m.append("{");
		for(final Sensor sensor : sensors) {
		    String name = sensor.getName();
		    m.append("\"").append(name).append("\":").append(sensor.getString()).append(",");
		}
		m.deleteCharAt(m.length()-1) ; // remove last comma
		m.append("}");
		return new Message(getUUID(), m.toString());
	}
	
}
