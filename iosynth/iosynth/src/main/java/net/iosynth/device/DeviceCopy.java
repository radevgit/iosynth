/**
 * 
 */
package net.iosynth.device;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * @author rradev
 *
 */
public abstract class DeviceCopy implements Serializable{
	private static final long serialVersionUID = 1L;
	protected int count;
	/**
	 * 
	 */
	public DeviceCopy() {
		this.count = 1;
	}
	
	/**
	 * @param count
	 */
	public DeviceCopy(int count){
		this.count = count;
	}
	
	/**
	 * @param dev
	 * @return List of replicated devices
	 */
	abstract public List<Device> replicate(Device dev);
	
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * Returns a copy of the object, or null if the object cannot be serialized.
	 * @param orig source
	 * @return new object
	 */
	public static Object copyObject(Object orig) {
		Object obj = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(orig);
			out.flush();
			out.close();

			// Make an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			obj = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
