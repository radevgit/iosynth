/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorStringRandom extends Sensor {
	private String values[];
	private int state;
	
	/**
	 * 
	 */
	public SensorStringRandom(){
		String val[] = {new String("on"), new String("off")};
		init(val);
	}
	
	/**
	 * @param values
	 */
	public SensorStringRandom(String values[]){
		init(values);
	}
	
	/**
	 * @param values 
	 * 
	 */
	public void init(String values[]) {
		this.state = 0;
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
		state = rnd.nextInt(values.length);
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
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step){
		for(int i=0; i<step; i++){
			state = rnd.nextInt(values.length);
		}
	}
	
	/**
	 * @return Sensor value
	 */
	public String getValue(){
		return values[state];
	}

	@Override
	public String getString() {
		return "\"" + getValue() + "\"";
	}

}
