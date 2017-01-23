/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorDoubleWalk extends Sensor {
	private double state;
	private double step;
	private double min, max;
	/**
	 * 
	 */
	public SensorDoubleWalk() {
		this.format = "%.4f";
		this.state = 0;
		this.step  = 0.1;
		this.min   = 0.0;
		this.max   = 10.0;	
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		state = state + rnd.nextGaussian() * step;
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
		if(step < 0.00000001){
			step = 0.00000001;
		}
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		state = rnd.nextDouble() * (max-min) + min;
	}

}
