/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author ross
 *
 */
public class SensorCycleDouble01 extends Sensor {
	private double values[];
	private long state;
	private static String FORMAT = "%.4f";
	/**
	 * 
	 */
	public SensorCycleDouble01(double[] values) {
		this.state = 0;
		this.values = new double[values.length];
		for(int i=0; i<values.length; i++){
			this.values[i] = values[i];
		}
	}
	
	// Propagate internal state and epoch
	public void step(long step){
		state = (state + step) % values.length;
		epoch = epoch + step;
	}
	
	public double getValue(){
		return values[(int)state];
	}

	@Override
	public String getString() {
		return String.format(FORMAT, getValue());
	}
}
