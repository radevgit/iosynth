/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorIntCycle extends Sensor {
	private long values[];
	private long state;
	
	/**
	 * 
	 */
	public SensorIntCycle() {
		this.format = "%d";
		this.state = 0;
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		state = rnd.nextLong(values.length);
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if (values == null) {
			values = new long[2];
			values[0] = 0;
			values[1] = 1;
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
	public long getValue(){
		return values[(int)state];
	}

	@Override
	public String getString() {
		return String.format(format, getValue());
	}


}
