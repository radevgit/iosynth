/**
 * 
 */
package net.iosynth.device;

/**
 * @author rradev
 *
 */
public class DIDString extends DID {

	/**
	 * 
	 */
	public DIDString() {
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
	 * @see net.iosynth.device.Sampling#replicate()
	 */
	@Override
	public void replicate(String x) {
		uuid = uuid + x;
	}

}
