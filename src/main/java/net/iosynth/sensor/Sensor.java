package net.iosynth.sensor;

import net.iosynth.util.Xoroshiro128;

/**
 * @author rradev
 *
 */
public abstract class Sensor {
	protected String name;
	protected String format = "%.4f";

	protected Xoroshiro128 rnd;
	
	
	/**
	 * 
	 */
	public Sensor(){
		this.name  = new String("sensor");
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
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
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
