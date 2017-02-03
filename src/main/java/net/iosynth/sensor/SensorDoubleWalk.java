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
	private double anomaly;
	private transient boolean isAnomaly;
	
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
		if (rnd.nextDouble() + 0.000000001 < anomaly) {
			if (isAnomaly) {
				isAnomaly = !isAnomaly;
			} else {
				isAnomaly = !isAnomaly;
			}
		}
		double spike = isAnomaly && rnd.nextDouble() < 0.01 ? rnd.nextExponential(state*0.5): 0.0;
		state = state + rnd.nextGaussian()*2.0*this.step;
		if(state > max){
			state = max;
		}
		if(state < min){
			state = min;
		}
		state = state + spike;
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
		if(anomaly < 0.0){
			anomaly = 0.0;
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
