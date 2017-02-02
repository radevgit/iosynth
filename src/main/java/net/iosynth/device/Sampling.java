/**
 * 
 */
package net.iosynth.device;

import net.iosynth.gen.Xoroshiro128;

/**
 * @author rradev
 *
 */
public abstract class Sampling {
	protected long interval;
	protected Xoroshiro128 rnd;
	/**
	 * 
	 */
	public Sampling() {
		
	}
	
	/**
	 * 
	 */
	abstract public void checkParameters();
	/**
	 * @return sampling interval value
	 */
	abstract public long nextInterval();
	/**
	 * 
	 */
	abstract public void replicate();
	
	/**
	 * @param interval the interval to set
	 */
	public void setInterval(long interval) {
		this.interval = interval;
	}
	
	/**
	 * @return interval
	 */
	public long getInterval(){
		return interval;
	}

	/**
	 * @return the rnd
	 */
	public Xoroshiro128 getRnd() {
		return rnd;
	}

	/**
	 * @param rnd the rnd to set
	 */
	public void setRnd(Xoroshiro128 rnd) {
		this.rnd = rnd;
	}
}
