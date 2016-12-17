/**
 * 
 */
package net.iosynth.sensor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates random walk with 0.1 step
 * @author ross
 *
 */
public class SensorRandomDouble01 extends Sensor {
	private double state;
	private double min, max;
	private static String FORMAT = "%.4f";

	/**
	 * 
	 * @param min Minimum generated value
	 * @param max Maximum generated value
	 */
	public SensorRandomDouble01(double min, double max) {
		this.state = ThreadLocalRandom.current().nextDouble()*(max-min)+min;
		this.min = min;
		this.max = max;
	}
	
	// Propagate internal state and epoch
	public void step(long step) {
		for(int i=0; i<step; i++){
			state = state + ThreadLocalRandom.current().nextDouble()*0.2 - 0.1;
			if(state>max) state = max;
			if(state<min) state = min;
		}
		epoch = epoch + step;
	}
	
	public double getValue(){
		return state;
	}

	@Override
	public String getString() {
		return String.format(FORMAT, getValue());
	}
}
