/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorDoubleOscillator extends Sensor {
	private double state;
	private transient double rad;
	private double min, max;
	private long   period;
	private double noise;
	/**
	 * 
	 */
	public SensorDoubleOscillator() {
		this.format = "%.4f";
		this.state = 0;
		this.rad   = 0;
		this.min   = 0.0;
		this.max   = 10.0;
		this.period = 1000000;
		this.noise  = 5.0;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		double delta = ((double)this.getDev().getSampling().getInterval());
		double x = (delta * Math.PI * 2.0)/ (double)period;
		rad = (rad + x) % (Math.PI * 2.0);
		double noisePersent = noise/100.0;
	    double noiseAdd = rnd.nextGaussian() * (max - min) * noisePersent;
	    // here we subtract noise from min and max to avoid clipping.
		state = 0.5 * (Math.sin(rad) + 1.0) * (max - min - 2 * noisePersent) + min + noisePersent + noiseAdd;
		if(state > max){
			state = max;
		}
		if(state < min){
			state = min;
		}
	}

	/**
	 * @return Sensor value
	 */
	public double getValue(){
		return state;
	}

	@Override
	public String getString() {
		return String.format(format, getValue());
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if(min >= max){
			max = min + 1;
		}
		if(state > max){
			state = max;
		}
		if(state < min){
			state = min;
		}
		if(period < 100){
			period = 100;
		}
		if(noise < 0.00001){
			noise = 0.00001;
		}
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		rad = rnd.nextDouble() * 2 * Math.PI;
	}

}
