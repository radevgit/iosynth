/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorBoolean extends Sensor {
	private boolean state;
	private double success;
	/**
	 * 
	 */
	public SensorBoolean() {
		this.state = false;
		this.success = 0.5;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		state = rnd.nextDouble() < success ? true : false;
	}
	
	public boolean getValue(){
		return state;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#getString()
	 */
	@Override
	public String getString() {
		return String.valueOf(state);
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if(success < 0.00000001  || success > 0.99999999) {
			success = 0.5;
		}
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		state = rnd.nextDouble() < success ? true : false;
	}

}
