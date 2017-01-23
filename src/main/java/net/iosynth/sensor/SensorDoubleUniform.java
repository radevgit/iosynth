/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorDoubleUniform extends Sensor {
	private double min, max;
	
	/**
	 * 
	 */
	public SensorDoubleUniform() {
		this.format = "%.4f";
		this.min = 0.0;
		this.max = 1.0;
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
		if (min >= max) {
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
	public double getValue(){
		return rnd.nextDouble()*(max - min) + min;
	}

	@Override
	public String getString() {
		return String.format(format, getValue());
	}

}
