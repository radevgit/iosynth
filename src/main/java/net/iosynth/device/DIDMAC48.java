/**
 * 
 */
package net.iosynth.device;

import net.iosynth.util.GeneratorMAC;

/**
 * @author rradev
 *
 */
public class DIDMAC48 extends DID {

	/**
	 * 
	 */
	public DIDMAC48() {
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
	public void replicate() {
		uuid = GeneratorMAC.getNext48();
	}

}
