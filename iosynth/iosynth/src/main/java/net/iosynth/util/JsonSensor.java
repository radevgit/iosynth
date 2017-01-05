/**
 * 
 */
package net.iosynth.util;

/**
 * @author rradev
 *
 */
public class JsonSensor {
	String name;
	String sensor;

	/**
	 * 
	 */
	public JsonSensor() {
		
	}

	public void set(String name, String sensor, String args[]){
		this.name = name;
		this.sensor = sensor;
		
	}
}
