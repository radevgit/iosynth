/**
 * 
 */
package net.iosynth.device;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author rradev
 *
 */
public class Arrival {
	//protected boolean fixed;
	protected long interval;
	private long jitter;
	/**
	 * 
	 */
	public Arrival() {
		//this.fixed = true;
		this.interval = 10000; // default 10s
		this.jitter = ThreadLocalRandom.current().nextInt(0, 2000); // At most 2s jitter
	}
	
	/**
	 * @return the interval
	 */
	public long getInterval() {
		return interval;
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

	public long getNextInterval() {
		return interval;
	}
	
	//public boolean isFixed(){
	//	return fixed;
	//}

}
