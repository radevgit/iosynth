/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorEpoch extends Sensor {
	protected transient long epoch;
	
	/**
	 * 
	 */
	public SensorEpoch() {
		this.format = "%d";
		this.epoch = -1L;
	}

	/**
	 * @return Sensor value
	 */
	public long getValue(){
		return epoch;
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		epoch = epoch + step;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#getString()
	 */
	@Override
	public String getString() {
		return String.format(format, getValue());
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
		epoch = 0L;
	}

	/**
	 * @return internal epoch
	 */
	public long getEpoch(){
		return epoch;
	}
}
