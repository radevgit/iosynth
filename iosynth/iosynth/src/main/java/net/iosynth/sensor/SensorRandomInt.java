/**
 * 
 */
package net.iosynth.sensor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author rradev
 *
 */
public class SensorRandomInt extends Sensor {
	private static final long serialVersionUID = 1L;
	private int state;
	private int min, max;
	private static String FORMAT = "%d";
	
	
	/**
	 * 
	 */
	public SensorRandomInt(){
		init(1, 10);
	}
	
	/**
	 * @param min
	 * @param max
	 */
	public SensorRandomInt(int min, int max){
		init(min, max);
	}
	/**
	 * 
	 * @param min Minimum generated value
	 * @param max Maximum generated value
	 */
	public void init(int min, int max) {
		this.state = ThreadLocalRandom.current().nextInt(min, max);
		this.min = min;
		this.max = max;
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		state = ThreadLocalRandom.current().nextInt(min, max);
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
		for (int i = 0; i < step; i++) {
			state = state + ThreadLocalRandom.current().nextInt(-1, 2);
			if (state > max)
				state = max;
			if (state < min)
				state = min;
		}
		epoch = epoch + step;
	}
	
	/**
	 * @return Sensor value
	 */
	public int getValue(){
		return state;
	}

	@Override
	public String getString() {
		return String.format(FORMAT, getValue());
	}

}
