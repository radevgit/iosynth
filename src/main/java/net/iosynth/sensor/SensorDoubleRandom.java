/**
 * 
 */
package net.iosynth.sensor;

/**
 * Generates random walk with 0.1 step
 * @author rradev
 *
 */
public class SensorDoubleRandom extends Sensor {
	private double state;
	private double min, max;
	
	/**
	 * 
	 */
	public SensorDoubleRandom() {
		init(1, 10);
	}

	/**
	 * @param min
	 * @param max
	 */
	public SensorDoubleRandom(double min, double max){
		init(min, max);
	}
	
	/**
	 * 
	 * @param min Minimum generated value
	 * @param max Maximum generated value
	 */
	public void init(double min, double max) {
		this.format = "%.4f";
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
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		for(int i=0; i<step; i++){
			state = state + rnd.nextDouble()*0.2 - 0.1;
			if(state>max) state = max;
			if(state<min) state = min;
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

}
