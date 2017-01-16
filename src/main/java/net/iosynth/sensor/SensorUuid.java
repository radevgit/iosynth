/**
 * 
 */
package net.iosynth.sensor;

import net.iosynth.device.Device;

/**
 * @author rradev
 *
 */
public class SensorUuid extends Sensor {
	private String value;
	/**
	 * Just a constant string value
	 */
	public SensorUuid() {
		this.value = new String("uuid");
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {	
		if(value==null){
			value = new String("");
		}
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step){
		// nothing to do
	}
	
	/**
	 * @return Sensor value
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * @param uuid
	 */
	public void SetValue(String uuid){
		value = uuid;
	}

	@Override
	public String getString() {
		return "\"" + getValue() + "\"";
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		value = getDev().getUUID().getUUID();
	}

}
