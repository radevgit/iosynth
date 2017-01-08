/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorConstantString extends Sensor {
	private String value;
	/**
	 * Just a constant string value
	 */
	public SensorConstantString() {
		this.value = new String("constant");
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
	
	/**
	 * @param value
	 */
	public SensorConstantString(String value) {
		this.value = value;
	}
	
	// Propagate internal state and epoch
	public void step(long step){
		epoch = epoch + step;
	}
	
	/**
	 * @return Sensor value
	 */
	public String getValue(){
		return value;
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
		// nothing to do
	}

}
