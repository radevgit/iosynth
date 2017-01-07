/**
 * 
 */
package net.iosynth.sensor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ross
 *
 */
public class SensorRandomString extends Sensor {
	private String values[];
	private int state;
	
	public SensorRandomString(){
		String val[] = {new String("on"), new String("off")};
		init(val);
	}
	
	public SensorRandomString(String values[]){
		init(values);
	}
	
	/**
	 * 
	 */
	public void init(String values[]) {
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
		for(int i=0; i<step; i++){
			state = ThreadLocalRandom.current().nextInt(values.length);
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
