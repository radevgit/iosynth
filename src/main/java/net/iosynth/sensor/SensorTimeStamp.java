/**
 * 
 */
package net.iosynth.sensor;

import java.util.Locale;

import net.iosynth.gen.GeneratorDate;

/**
 * @author rradev
 *
 */
public class SensorTimeStamp extends Sensor {
	private transient GeneratorDate gen;
	private transient static final String s  = "s";
	private transient static final String ms = "ms";
	private transient static final String quot = "\"";
	private transient static final String defaultFormat = "yyyy-MM-dd'T'HH:mm:ssZ";
	private Locale locale;
	private String from;
	private String to;
	
	/**
	 * 
	 */
	public SensorTimeStamp() {
		this.locale = Locale.US;
		this.format = defaultFormat;
		this.from   = "2000-01-01T00:00:00+0000";
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
		if(!format.equals(s) && !format.equals(ms)){
			return quot + gen.getDate() + quot;
		} else {
			return gen.getDate();
		}
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
		gen = new GeneratorDate(rnd, from, to, locale, format);
	}

}
