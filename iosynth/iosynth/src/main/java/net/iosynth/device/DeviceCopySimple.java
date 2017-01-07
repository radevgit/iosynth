/**
 * 
 */
package net.iosynth.device;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rradev
 *
 */
public class DeviceCopySimple extends DeviceCopy {
	private final static String F1 = "%1d";
	private final static String F2 = "%02d";
	private final static String F3 = "%03d";
	private final static String F4 = "%04d";
	private final static String F5 = "%05d";
	private final static String F6 = "%06d";
	private final static String F7 = "%07d";
	private final static String F8 = "%08d";

	/**
	 * 
	 */
	public DeviceCopySimple() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see net.iosynth.device.DeviceCopy#replicate(net.iosynth.device.Device)
	 */
	@Override
	public List<Device> replicate(Device dev) {
		List<Device> devList = new ArrayList();
		String format = getFormat(count);
		for(int i=0; i<count; i++){
			Device devNew = (Device) copyObject(dev);
			devNew.setId(dev.getId() + String.format(format, i));
			devList.add(devNew);
		}
		
		return devList;
	}
	
	private String getFormat(int count){
		if(count<10){
			return F1;
		}
		if(count<100){
			return F2;
		}
		if(count<1000){
			return F3;
		}
		if(count<10000){
			return F4;
		}
		if(count<100000){
			return F5;
		}
		if(count<1000000){
			return F6;
		}
		if(count<10000000){
			return F7;
		}
		if(count<100000000){
			return F8;
		}
		return null;
	}

}
