/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author ross
 *
 */
public class SensorCycleInt01 extends Sensor {
	private int values[];
	private long state;
	private static String FORMAT = "%d";
	/**
	 * 
	 */
	public SensorCycleInt01(int[] values) {
		this.state = 0;
		this.values = new int[values.length];
		for(int i=0; i<values.length; i++){
			this.values[i] = values[i];
		}
	}
	
	// Propagate internal state and epoch
	public void step(long step){
		state = (state + step) % values.length;
		epoch = epoch + step;
	}
	
	public int getValue(){
		return values[(int)state];
	}

	@Override
	public String getString() {
		return String.format(FORMAT, getValue());
	}
}
