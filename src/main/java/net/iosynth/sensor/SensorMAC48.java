/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorMAC48 extends Sensor {
	private String value;
	/**
	 * 
	 */
	public SensorMAC48() {
		this.value = new String("uuid");
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
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
	public void SetValue(String mac){
		value = mac;
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#getString()
	 */
	@Override
	public String getString() {
		return "\"" + getValue() + "\"";
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
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		// nothing to do
	}

}
