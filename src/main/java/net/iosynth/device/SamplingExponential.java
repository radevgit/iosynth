/**
 * 
 */
package net.iosynth.device;

/**
 * @author rradev
 *
 */
public class SamplingExponential extends Sampling {
	private long beta;
	/**
	 * 
	 */
	public SamplingExponential() {
		this.beta = 10000;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Sampling#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if(beta < 100){
			beta = 100;
		}
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Sampling#getInterval()
	 */
	@Override
	public long getInterval() {
		interval = (long)(rnd.nextExponential((double)beta));
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
		// TODO Auto-generated method stub

	}

}
