/**
 * 
 */
package net.iosynth.device;

/**
 * @author rradev
 *
 */
public class ArrivalFixed extends Arrival {
	private static final long serialVersionUID = 1L;
	private long jitter;
	/**
	 * 
	 */
	public ArrivalFixed() {
		//this.fixed = true;
		this.interval = 10000; // default 10s
		this.jitter   = 0;
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.device.Arrival#getInterval()
	 */
	@Override
	public long getInterval() {
		return interval;
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.Arrival#checkParameters()
	 */
	@Override
	public void checkParameters(){
		if(interval<100){
			interval=100;
		}
	}
	
	/* (non-Javadoc)
	 * @see net.iosynth.device.Arrival#replicate()
	 */
	@Override
	public void replicate() {
		jitter = rnd.nextLong(10000);
	}
		
	/**
	 * @param jitter Jitter to displace fixed inter-arrival intervals.
	 */
	public void setJitter(long jitter) {
		this.jitter = jitter;
	}
	
	/**
	 * @return Jitter to displace fixed inter-arrival intervals.
	 */
	public long getJitter(){
		return jitter;
	}
}
