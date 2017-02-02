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
		this.mean = 10000;
		this.stdev = 5000;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Sampling#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if (mean < 50) {
			mean = 50;
		}
		if (stdev < 50) {
			stdev = 50;
		}
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Sampling#getInterval()
	 */
	@Override
	public long nextInterval() {
		interval = (long) (rnd.nextGaussian() * stdev + mean);
		if (interval < 50) {
			interval = 50;
		}
		return interval;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Sampling#replicate()
	 */
	@Override
	public void replicate() {
		/// nothing to do
	}

}
