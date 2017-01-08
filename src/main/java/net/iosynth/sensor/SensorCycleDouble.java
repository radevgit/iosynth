/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorCycleDouble extends Sensor {
	private double values[];
	private long state;
	private static String FORMAT = "%.4f";
	
	/**
	 * 
	 */
	public SensorCycleDouble() {
		double val[] = {1.1, 2.2, 3.3};
		init(val);
	}
	
	/**
	 * @param values
	 */
	public SensorCycleDouble(double values[]){
		init(values);
	}
	/**
	 * @param values 
	 * 
	 */
	public void init(double[] values) {
		this.state = 0;
		this.values = new double[values.length];
		for(int i=0; i<values.length; i++){
			this.values[i] = values[i];
		}
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		state = rnd.nextInt(values.length);
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if (values == null) {
			values = new double[1];
			values[0] = 0.0;
		}
	}
	
	// Propagate internal state and epoch
	public void step(long step){
		state = (state + step) % values.length;
		epoch = epoch + step;
	}
	
	/**
	 * @return Sensor value
	 */
	public double getValue(){
		return values[(int)state];
	}

	@Override
	public String getString() {
		return String.format(FORMAT, getValue());
	}


}
