/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author ross
 *
 */
public class SensorConstantString extends Sensor {
	private String value;
	/**
	 * Just a constant string value
	 */
	public SensorConstantString(String value) {
		this.value = value;
	}
	
	// Propagate internal state and epoch
	public void step(long step){
		epoch = epoch + step;
	}
	
	public String getValue(){
		return value;
	}

	@Override
	public String getString() {
		return getValue();
	}
}
