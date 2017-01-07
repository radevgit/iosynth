/**
 * 
 */
package net.iosynth.device;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author rradev
 *
 */
public abstract class Arrival {
	protected long interval;
	/**
	 * 
	 */
	public Arrival() {
		
	}
	
	abstract public void checkParameters();
	abstract public long getInterval();
	
}
