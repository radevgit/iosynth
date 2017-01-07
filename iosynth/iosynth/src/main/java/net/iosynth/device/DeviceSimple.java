/**
 * 
 */
package net.iosynth.device;

import java.util.List;
import java.util.UUID;

import net.iosynth.sensor.Sensor;
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
		arrival.checkParameters();
		for(final Sensor sen: sensors){
			sen.checkParameters();
		}
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.device.Device#replicate()
	 */
	@Override
	public List<Device> replicate() {
		return copy.replicate(this);
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
