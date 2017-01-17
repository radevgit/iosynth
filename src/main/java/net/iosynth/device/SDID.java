/**
 * 
 */
package net.iosynth.device;

/**
 * @author rradev
 *
 */
public abstract class SDID {
	protected String uuid;

	/**
	 * 
	 */
	public SDID() {
		uuid = new String("sdid");
	}
	
	/**
	 * 
	 */
	abstract public void checkParameters();
	/**
	 * 
	 */
	abstract public void replicate();
	
	/**
	 * @return the sdid
	 */
	public String getUUID() {
		return uuid;
	}

	/**
	 * @param sdid the sdid to set
	 */
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}

}
