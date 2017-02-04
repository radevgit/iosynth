/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorDoubleWalk extends Sensor {
	private transient double state;
	private double step;
	private double min, max;
	private double anomaly;
	private transient boolean isAnomaly;
	private transient double stepD;
	private transient double spike;
	/**
	 * 
	 */
	public SensorDoubleWalk() {
		this.format = "%.4f";
		this.state = 0;
		this.step  = 0.1;
		this.min   = 0.0;
		this.max   = 10.0;
		this.spike = 0.0;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		if (rnd.nextDouble() + 0.000000001 < anomaly) {
			if (isAnomaly) {
				isAnomaly = !isAnomaly;
				this.step = this.step - stepD;
				stepD = 0.0;
			} else {
				isAnomaly = !isAnomaly;
				stepD = this.step * 1.0;
				this.step = this.step + stepD;
			}
		}
		state = state + rnd.nextGaussian() * 2.0 * this.step;
		if (state > max) {
			state = max;
		}
		if (state < min) {
			state = min;
		}

		state = state - spike;
		if (isAnomaly && (rnd.nextDouble() < 0.01)) {
			spike = rnd.nextBoolean() ? rnd.nextExponential(state * 0.3) : -rnd.nextExponential(state * 0.3);
		} else {
			spike = 0.0;
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
