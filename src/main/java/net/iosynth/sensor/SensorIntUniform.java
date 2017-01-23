/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorIntUniform extends Sensor {
	private long min, max;
	
	/**
	 * 
	 */
	public SensorIntUniform(){
		this.format = "%d";
		this.min = 1;
		this.max = 10;
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		// nothing to do
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if (min > max) {
			max = min + 1;
		}
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		// nothing to do
	}
	
	/**
	 * @return Sensor value
	 */
	public long getValue(){
		return rnd.nextLong(max-min) + min;
	}

	@Override
	public String getString() {
		return String.format(format, getValue());
	}

}
