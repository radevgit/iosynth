/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorDoubleCycle extends Sensor {
	private double values[];
	private long state;
	
	/**
	 * 
	 */
	public SensorDoubleCycle() {
		final double val[] = {1.234567};
		init(val);
	}
	
	/**
	 * @param values
	 */
	public SensorDoubleCycle(double values[]){
		init(values);
	}
	/**
	 * @param values 
	 * 
	 */
	public void init(double[] values) {
		this.format = "%.4f";
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
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step){
		state = (state + step) % values.length;
	}
	
	/**
	 * @return Sensor value
	 */
	public double getValue(){
		return values[(int)state];
	}

	@Override
	public String getString() {
		return String.format(format, getValue());
	}


}
