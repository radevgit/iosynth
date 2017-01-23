/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorDoubleNormal extends Sensor {
	private double mean, stdev;
	
	/**
	 * 
	 */
	public SensorDoubleNormal() {
		this.format = "%.4f";
		this.mean = 1.0;
		this.stdev = 1.0;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if(mean < 0.00000001){
			mean = 0.00000001;
		}
		if(stdev < 0.00000001){
			stdev = 0.00000001;
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
		return rnd.nextGaussian()*stdev + mean;
	}

	@Override
	public String getString() {
		return String.format(format, getValue());
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		// nothing to do
	}

}
