/**
 * 
 */
package net.iosynth.device;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author rradev
 *
 */
public class ArrivalUniform extends Arrival {
	private long min;
	private long max;
	/**
	 * 
	 */
	public ArrivalUniform() {
		this.interval  = ThreadLocalRandom.current().nextLong(9*1000)+1000; // Default delay 1-10 s
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
	 * @see net.iosynth.device.ArrivalFixed#getNextInterval()
	 */
	@Override
	public long getNextInterval(){
		return ThreadLocalRandom.current().nextLong((max-min))+min;
	}

}
