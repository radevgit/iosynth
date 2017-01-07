/**
 * 
 */
package net.iosynth.device;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author rradev
 *
 */
public abstract class Arrival implements Serializable{
	private static final long serialVersionUID = 1L;
	protected long interval;
	/**
	 * 
	 */
	public Arrival() {
		
	}
	
	abstract public void checkParameters();
	abstract public long getInterval();
	abstract public void replicate();
}
