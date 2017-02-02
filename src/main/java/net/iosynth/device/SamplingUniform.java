/**
 * 
 */
package net.iosynth.device;

/**
 * @author rradev
 *
 */
public class SamplingUniform extends Sampling {
	private long min;
	private long max;
	/**
	 * 
	 */
	public SamplingUniform() {
		this.min       = 1000;
		this.max       = 10000;
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.device.Sampling#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if (interval < 50) {
			interval = 50;
		}
		if (min < 50) {
			min = 50;
		}
		if (max < min) {
			max = min + 1;
		}
	}
		
	/**
	 * @return the max
	 */
	public long getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(long max) {
		this.max = max;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Sampling#getInterval()
	 */
	@Override
	public long nextInterval() {
		interval  = rnd.nextLong(max-min)+min;
		return interval;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Sampling#replicate()
	 */
	@Override
	public void replicate() {
		// nothing to do
	}
	

}
