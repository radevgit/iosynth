package net.iosynth.sensor;

import java.io.Serializable;

/**
 * @author rradev
 *
 */
public abstract class Sensor implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String name;
	protected long epoch;
	
	/**
	 * 
	 */
	public Sensor(){
		this.name  = new String("sensor");
		this.epoch = 0L;
	}
	
	/**
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * @return Sensor name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return internal epoch
	 */
	public long getEpoch(){
		return epoch;
	}
	
	/**
	 * @param step
	 */
	abstract public void step(long step);
	/**
	 * @return String representation of sensor value
	 */
	abstract public String getString();
	/**
	 * 
	 */
	abstract public void checkParameters();
	/**
	 * 
	 */
	abstract public void replicate();
}
