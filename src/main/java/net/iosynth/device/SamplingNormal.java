/**
 * 
 */
package net.iosynth.device;

/**
 * @author rradev
 *
 */
public class SamplingNormal extends Sampling {
	private long mean;
	private long stdev;
	/**
	 * 
	 */
	public SamplingNormal() {
		this.mean = 15000;
		this.stdev = 5000;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Sampling#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if (mean < 100) {
			mean = 100;
		}
		if (stdev < 100) {
			stdev = 100;
		}
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Sampling#getInterval()
	 */
	@Override
	public long getInterval() {
		interval = (long)(rnd.nextGaussian()*stdev + mean);
		if(interval<100){
			interval = 100;
		}
		return interval;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Sampling#replicate()
	 */
	@Override
	public void replicate() {
		getInterval();
	}

}
