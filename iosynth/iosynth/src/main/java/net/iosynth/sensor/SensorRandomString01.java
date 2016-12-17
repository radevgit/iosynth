/**
 * 
 */
package net.iosynth.sensor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ross
 *
 */
public class SensorRandomString01 extends Sensor {
	private String values[];
	private int state;
	
	/**
	 * 
	 */
	public SensorRandomString01(String[] values) {
		this.state = 0;
		this.values = new String[values.length];
		for(int i=0; i<values.length; i++){
			this.values[i] = values[i];
		}
	}
	
	// Propagate internal state and epoch
	public void step(long step){
		for(int i=0; i<step; i++){
			state = ThreadLocalRandom.current().nextInt(0, values.length);
		}
		epoch = epoch + step;
	}
	
	public String getValue(){
		return values[state];
	}

	@Override
	public String getString() {
		return "\"" + getValue() + "\"";
	}
}
