/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorIntWalk extends Sensor {
	private long state;
	private double step;
	private long min, max;
	/**
	 * 
	 */
	public SensorIntWalk() {
		this.format = "%d";
		this.state = 0;
		this.step  = 1;
		this.min   = 0;
		this.max   = 10;	
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		state = state + (long)(rnd.nextGaussian()*2.0*this.step);
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
	public long getValue(){
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
		if(step < 0.001){
			step = 0.001;
		}
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		state = rnd.nextLong(max-min) + min;
	}

}
