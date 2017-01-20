/**
 * 
 */
package net.iosynth.sensor;

import net.iosynth.gen.GeneratorMAC;

/**
 * @author rradev
 *
 */
public class SensorMAC64 extends Sensor {
	private transient GeneratorMAC gen;
	private transient String value;
	String prefix;
	/**
	 * 
	 */
	public SensorMAC64() {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		value = gen.getRandom64();
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
		gen = new GeneratorMAC(rnd, prefix);
	}

}
