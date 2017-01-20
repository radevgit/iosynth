/**
 * 
 */
package net.iosynth.sensor;

import java.util.Locale;

import net.iosynth.gen.GeneratorCountry;

/**
 * @author rradev
 *
 */
public class SensorCountry extends Sensor {
	private transient GeneratorCountry gen;
	private Locale locale;
	/**
	 * 
	 */
	public SensorCountry() {
		this.locale = Locale.US;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#getString()
	 */
	@Override
	public String getString() {
		return "\"" + gen.getCountry() + "\"";
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
		gen = new GeneratorCountry(rnd, locale);
	}

}
