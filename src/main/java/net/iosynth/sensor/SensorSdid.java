/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorSdid extends Sensor {
	private String value;
	/**
	 * Just a constant string value
	 */
	public SensorSdid() {
		this.value = new String("sdid");
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
	 * @param sdid
	 */
	public void SetValue(String sdid){
		value = sdid;
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
		value = getDev().getSDID().getUUID();
	}

}
