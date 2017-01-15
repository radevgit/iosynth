/**
 * 
 */
package net.iosynth.sensor;

/**
 * @author rradev
 *
 */
public class SensorIntRandom extends Sensor {
	private int state;
	private int min, max;
	
	
	/**
	 * 
	 */
	public SensorIntRandom(){
		init(1, 10);
	}
	
	/**
	 * @param min
	 * @param max
	 */
	public SensorIntRandom(int min, int max){
		init(min, max);
	}
	/**
	 * 
	 * @param min Minimum generated value
	 * @param max Maximum generated value
	 */
	public void init(int min, int max) {
		this.format = "%d";
		this.state = 0;
		this.min = min;
		this.max = max;
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		state = rnd.nextInt(max-min)+min;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if (min > max) {
			max = min + 1;
		}
		if (state < min || state > max) {
			state = min;
		}
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		for (int i = 0; i < step; i++) {
			state = state + rnd.nextInt(2)-1;
			if (state > max)
				state = max;
			if (state < min)
				state = min;
		}
	}
	
	/**
	 * @return Sensor value
	 */
	public int getValue(){
		return state;
	}

	@Override
	public String getString() {
		return String.format(format, getValue());
	}

}
