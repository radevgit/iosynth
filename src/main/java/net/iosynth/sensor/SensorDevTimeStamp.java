/**
 * 
 */
package net.iosynth.sensor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author rradev
 *
 */
public class SensorDevTimeStamp extends Sensor {
	private transient SimpleDateFormat fmt;

	/**
	 * 
	 */
	public SensorDevTimeStamp() {
		this.format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		this.fmt = new SimpleDateFormat(format);
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
		Date now = new Date(System.currentTimeMillis());
		return "\"" + fmt.format(now) + "\"";
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if(format == null){
			fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		} else {
			fmt = new SimpleDateFormat(format);
		}
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		fmt = new SimpleDateFormat(format);
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
