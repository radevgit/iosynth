/**
 * 
 */
package net.iosynth.device;

import java.util.UUID;

import net.iosynth.sensor.SensorDefault;

/**
 * @author rradev
 *
 */
public class DeviceSimple extends Device {

	/**
	 * 
	 */
	public DeviceSimple() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.device.Device#checkParameters()
	 */
	@Override
	public void checkParameters(){
		if(uuid.length()<1){
			uuid = UUID.randomUUID().toString();
		}
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

}
