/**
 * 
 */
package net.iosynth.device;

/**
 * @author rradev
 *
 */
public abstract class DID {
	protected String uuid;

	/**
	 * 
	 */
	public DID() {
		uuid = new String("uuid");
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
	 * @return the uuid
	 */
	public String getUUID() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}

}
