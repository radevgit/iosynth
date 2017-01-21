/**
 * 
 */
package net.iosynth.sensor;

import net.iosynth.gen.GeneratorString;

/**
 * @author rradev
 *
 */
public class SensorString extends Sensor {
	transient private GeneratorString gen;
	transient private long state;
	transient private String value;
	transient private char[] alp;
	private String cycle[];
	private String random[];
	private int min, max;
	private String alphabet;
	/**
	 * Just a constant string value
	 */
	public SensorString() {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {	
		if(cycle != null){ // ignore other parameters
			random = null;
			alphabet = null;
		}
		if(random != null){
			alphabet = null;
		}
		if(alphabet != null){
			if(min>=max){
				max = min + 1;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step){
		if(cycle != null){
			state = (state + step) % cycle.length;
			value = cycle[(int)state];
		} else {
			if(random != null){
				state = rnd.nextInt(random.length);
				value = random[(int)state];
			} else {
				value = gen.getString();
			}
		}
	}
	
	/**
	 * @return Sensor value
	 */
	public String getValue(){
		return value;
	}

	@Override
	public String getString() {
		return "\"" + getValue() + "\"";
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		if(cycle != null){
			state = rnd.nextInt(cycle.length);
		}
		if(alphabet != null){
			alp = alphabet.toCharArray();
			alphabet = null;
			gen = new GeneratorString(rnd, alp, min, max);
		}
	}

}
