/**
 * 
 */
package net.iosynth.device;

import java.util.UUID;

/**
 * @author rradev
 *
 */
public class DIDUuid extends DID {

	/**
	 * 
	 */
	public DIDUuid() {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.DID#checkParameters()
	 */
	@Override
	public void checkParameters() {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.DID#replicate(java.lang.String)
	 */
	@Override
	public void replicate(String x) {
		uuid = UUID.randomUUID().toString();
	}

}
