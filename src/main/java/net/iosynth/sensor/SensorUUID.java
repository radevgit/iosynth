/**
 * 
 */
package net.iosynth.sensor;

import java.util.UUID;

/**
 * @author rradev
 *
 */
public class SensorUUID extends Sensor {
	private transient String value;
	/**
	 * 
	 */
	public SensorUUID() {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		value = UUID.randomUUID().toString();
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
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		value = UUID.randomUUID().toString();
	}

}
