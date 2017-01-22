/**
 * 
 */
package net.iosynth.sensor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author rradev
 *
 */
public class SensorDevTimeStamp extends Sensor {
	private transient SimpleDateFormat fmt;
	private transient Calendar cal;
	private transient static final String s  = "s";
	private transient static final String ms = "ms";
	private transient static final String quot = "\"";
	private transient static final String defaultFormat = "yyyy-MM-dd'T'HH:mm:ssZ";
	private Locale locale;
	
	/**
	 * 
	 */
	public SensorDevTimeStamp() {
		this.locale = Locale.US;
		this.format = defaultFormat;
		this.fmt = new SimpleDateFormat(format, this.locale);
		setName("time");
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		// do nothing

	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#getString()
	 */
	@Override
	public String getString() {
		long tt = System.currentTimeMillis();
		if(format.length() == 0){
			return quot + String.valueOf(tt) + quot;
		}
		if(format.equals(ms)){
			return String.valueOf(tt);
		}
		if(format.equals(s)){
			return String.valueOf(tt/1000L);
		}
		cal.setTimeInMillis(tt);
		fmt.setCalendar(cal);
		return quot + fmt.format(cal.getTime()) + quot;
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
		fmt = new SimpleDateFormat(format, locale);
		if(!format.equals(s) && !format.equals(ms)){
			cal = GregorianCalendar.getInstance();
		}
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

}
