/**
 * 
 */
package net.iosynth.sensor;

/**
 * Generates random walk with 0.1 step
 * @author rradev
 *
 */
public class SensorRandomDouble extends Sensor {
	private static final long serialVersionUID = 1L;
	private double state;
	private double min, max;
	private static String FORMAT = "%.4f";
	
	/**
	 * 
	 */
	public SensorRandomDouble() {
		init(1, 10);
	}

	/**
	 * @param min
	 * @param max
	 */
	public SensorRandomDouble(double min, double max){
		init(min, max);
	}
	
	/**
	 * 
	 * @param min Minimum generated value
	 * @param max Maximum generated value
	 */
	public void init(double min, double max) {
		this.state = 0;
		this.min = min;
		this.max = max;
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		state = rnd.nextDouble()*(max-min)+min;
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
		if (state < min || state > max) {
			state = min;
		}
	}
	
	// Propagate internal state and epoch
	public void step(long step) {
		for(int i=0; i<step; i++){
			state = state + rnd.nextDouble()*0.2 - 0.1;
			if(state>max) state = max;
			if(state<min) state = min;
		}
		epoch = epoch + step;
	}
	
	/**
	 * @return Sensor value
	 */
	public double getValue(){
		return state;
	}

	@Override
	public String getString() {
		return String.format(FORMAT, getValue());
	}

}
