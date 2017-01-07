/**
 * 
 */
package net.iosynth.sensor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author rradev
 *
 */
public class SensorCycleString extends Sensor {
	private static final long serialVersionUID = 1L;
	private String values[];
	private long state;
	
	/**
	 * 
	 */
	public SensorCycleString(){
		String val[] = {new String("on"), new String("off")};
		init(val);
	}
	
	/**
	 * @param values
	 */
	public SensorCycleString(String[] values){
		init(values);
	}
	
	/**
	 * @param values 
	 */
	public void init(String[] values) {
		this.state = ThreadLocalRandom.current().nextInt(values.length);
		this.values = new String[values.length];
		for(int i=0; i<values.length; i++){
			this.values[i] = values[i];
		}
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		state = ThreadLocalRandom.current().nextInt(values.length);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if (values == null) {
			values = new String[1];
			values[0] = new String("");
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
	public String getValue(){
		return values[(int)state];
	}

	@Override
	public String getString() {
		return "\"" + getValue() + "\"";
	}


}
