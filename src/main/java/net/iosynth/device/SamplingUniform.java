/**
 * 
 */
package net.iosynth.device;

/**
 * @author rradev
 *
 */
public class ArrivalUniform extends Arrival {
	private static final long serialVersionUID = 1L;
	private long min;
	private long max;
	/**
	 * 
	 */
	public ArrivalUniform() {
		this.interval  = 0;
		this.min       = 1000;
		this.max       = 10000;
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.device.Arrival#checkParameters()
	 */
	@Override
	public void checkParameters() {
		if(interval<100){
			interval=100;
		}
		if(min<100){
			min=100;
		}
		if(max<min){
			max=min+1;
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
	 * @see net.iosynth.device.Arrival#getInterval()
	 */
	@Override
	public long getInterval() {
		interval  = rnd.nextLong(max-min)+min;
		return interval;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Arrival#replicate()
	 */
	@Override
	public void replicate() {
		interval = rnd.nextLong(max-min)+min;
	}
	

}
