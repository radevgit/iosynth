/**
 * 
 */
package net.iosynth.device;

import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

/**
 * @author rradev
 *
 */
public class Zzz {

	/**
	 * 
	 */
	public Zzz() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		RuntimeTypeAdapterFactory<Device> shapeAdapter = RuntimeTypeAdapterFactory.of(Device.class, "type");
	}
	
}
