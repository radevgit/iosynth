/**
 * 
 */
package net.iosynth.device;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author rradev
 *
 */
public class ArrivalFixed extends Arrival {
	private long jitter;
	/**
	 * 
	 */
	public ArrivalFixed() {
		//this.fixed = true;
		this.interval = 10000; // default 10s
		this.jitter = ThreadLocalRandom.current().nextInt(0, 2000); // At most 2s jitter
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
	
	/**
	 * @param interval the interval to set
	 */
	public void setInterval(long interval) {
		this.interval = interval;
	}
	
	public void setJitter(long jitter) {
		this.jitter = jitter;
	}
	
	public long getJitter(){
		return jitter;
	}
}
