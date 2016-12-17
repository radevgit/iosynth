
package net.iosynth.sensor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates random int from predefined set
 * @author ross
 *
 */
public class SensorDefault extends Sensor {
	private static int intVal[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61};
	private int state;
	private static String FORMAT = "%d";
	/**
	 * 
	 * @param min Minimum generated value
	 * @param max Maximum generated value
	 */
	public SensorDefault() {
	}
	
	// Propagate internal state and epoch
	public void step(long step) {
		for(int i=0; i<step; i++){
			state = intVal[ThreadLocalRandom.current().nextInt(0, intVal.length)];
		}
		epoch = epoch + step;
	}
	
	public int getValue(){
		return state;
	}

	@Override
	public String getString() {
		return String.format(FORMAT, getValue());
	}
}