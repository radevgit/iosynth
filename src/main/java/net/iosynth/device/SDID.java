/**
 * 
 */
package net.iosynth.device;

/**
 * @author rradev
 *
 */
public abstract class SDID {
	protected String sdid;

	/**
	 * 
	 */
	public SDID() {
		sdid = new String("sdid");
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
		return sdid;
	}

	/**
	 * @param sdid 
	 */
	public void setUUID(String sdid) {
		this.sdid = sdid;
	}

}
