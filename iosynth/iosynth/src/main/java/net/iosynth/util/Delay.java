/**
 * 
 */
package net.iosynth.util;

/**
 * @author rradev
 *
 */
public class Delay {
	private int id;
	private long delay;
	/**
	 * @param id
	 * @param delay
	 */
	public Delay(int id, long delay) {
		super();
		this.id = id;
		this.delay = delay;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the delay
	 */
	public long getDelay() {
		return delay;
	}
	/**
	 * @param delay the delay to set
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}
}
