/**
 * 
 */
package net.iosynth.sensor;

import net.iosynth.gen.GeneratorIPv4;

/**
 * @author rradev
 *
 */
public class SensorIPv4 extends Sensor {
	private transient GeneratorIPv4 gen;
	private transient String value;
	String prefix;
	/**
	 * 
	 */
	public SensorIPv4() {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		value = gen.getRandomIPv4();
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
		gen = new GeneratorIPv4(rnd, prefix);
	}

}
