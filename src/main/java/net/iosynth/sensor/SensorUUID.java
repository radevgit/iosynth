/**
 * 
 */
package net.iosynth.sensor;

import java.util.UUID;

import net.iosynth.gen.GeneratorUUID;

/**
 * @author rradev
 *
 */
public class SensorUUID extends Sensor {
	private transient GeneratorUUID gen;
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
		value = gen.getUUID();
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
		gen = new GeneratorUUID(rnd);
	}

}
