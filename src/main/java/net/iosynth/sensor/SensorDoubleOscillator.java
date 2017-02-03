/**
 * 
 */
package net.iosynth.sensor;

import java.util.logging.Logger;

import net.iosynth.adapter.AdapterMqtt;

/**
 * @author rradev
 *
 */
public class SensorDoubleOscillator extends Sensor {
	private double state;
	private transient double rad;
	private double min, max;
	private long   period;
	private double noise;
	private double   anomaly;
	private transient double minD, maxD, noiseD;
	private transient boolean isAnomaly;
	
	private transient final Logger logger = Logger.getLogger(AdapterMqtt.class.getName());
	/**
	 * 
	 */
	public SensorDoubleOscillator() {
		this.format = "%.4f";
		this.state = 0;
		this.rad   = 0;
		this.min   = 0.0;
		this.max   = 10.0;
		this.period = 1000000;
		this.noise  = 5.0;
		this.anomaly = Long.MAX_VALUE;
		this.isAnomaly = false;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#step(long)
	 */
	@Override
	public void step(long step) {
		double delta = ((double)this.getDev().getSampling().getInterval());
		if (rnd.nextDouble() + 0.000000001 < anomaly) {
			if (isAnomaly) {
				logger.info("Device: " + getDev().getUuid() + " anomaly end");
				isAnomaly = !isAnomaly;
				min = min + minD;
				max = max - maxD;
				noise = noise - noiseD;
				minD = maxD = noiseD = 0.0;
			} else {
				logger.info("Device: " + getDev().getUuid() + " anomaly");
				isAnomaly = !isAnomaly;
				minD = (max - min) * rnd.nextGaussian() * 0.1;
				maxD = (max - min) * rnd.nextGaussian() * 0.1;
				noiseD = noise * 1.0;
				min = min - minD;
				max = max + maxD;
				noise = noise + noiseD;
			}
		}
		
		double x = (delta * Math.PI * 2.0)/ (double)period;
		rad = (rad + x) % (Math.PI * 2.0);
		double noisePersent = noise/100.0;
	    double noiseAdd = rnd.nextGaussian() * (max - min) * noisePersent;
	    // here we subtract noise from min and max to avoid clipping.
	    double spike = isAnomaly && rnd.nextDouble() < 0.01 ? rnd.nextExponential(state): 0.0;
		state = 0.5 * (Math.sin(rad) + 1.0) * (max - min - 2 * noisePersent) + min + noisePersent + noiseAdd;
		if(state > max){
			state = max;
		}
		if(state < min){
			state = min;
		}
		state = state + spike;
	}

	/**
	 * @return Sensor value
	 */
	public double getValue(){
		return state;
	}

	@Override
	public String getString() {
		return String.format(format, getValue());
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if(min >= max){
			max = min + 1;
		}
		if(state > max){
			state = max;
		}
		if(state < min){
			state = min;
		}
		if(period < 100){
			period = 100;
		}
		if(noise < 0.00001){
			noise = 0.00001;
		}
		if(anomaly < 0.0){
			anomaly = 0.0;
		}
	}

	/* (non-Javadoc)
	 * @see net.iosynth.sensor.Sensor#replicate()
	 */
	@Override
	public void replicate() {
		rad = rnd.nextDouble() * 2 * Math.PI;
	}

}
