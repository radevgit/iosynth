/**
 * 
 */
package net.iosynth.sensor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ross
 *
 */
public class SensorCycleString01 extends Sensor {
	private String values[];
	private long state;
	
	/**
	 * 
	 */
	public SensorCycleString01(String[] values) {
		this.state = ThreadLocalRandom.current().nextInt(values.length);
		this.values = new String[values.length];
		for(int i=0; i<values.length; i++){
			this.values[i] = values[i];
		}
	}
	
	// Propagate internal state and epoch
	public void step(long step){
		state = (state + step) % values.length;
		epoch = epoch + step;
	}
	
	public String getValue(){
		return values[(int)state];
	}

	@Override
	public String getString() {
		return "\"" + getValue() + "\"";
	}
}
