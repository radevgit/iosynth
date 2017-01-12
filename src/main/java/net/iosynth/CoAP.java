package net.iosynth;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;

import net.iosynth.adapter.Config;
import net.iosynth.adapter.ConfigCoap;
import net.iosynth.adapter.Message;
import net.iosynth.adapter.AdapterCoap;
import net.iosynth.device.Device;
import net.iosynth.device.DeviceControl;
import net.iosynth.device.DevicesFromJson;

/**
 * 
 *
 */
public class CoAP {
	protected BlockingQueue<Message> msgQueue;
	protected AdapterCoap coap;
	
	protected CoAP(Config cfg) {
		msgQueue = new LinkedBlockingQueue<Message>();
		// set configuration from Json file
		Gson gson = new Gson();
		ConfigCoap coapCfg = gson.fromJson(cfg.cfgJson, ConfigCoap.class);
		
		try {
			coap = new AdapterCoap(coapCfg, msgQueue);
		} catch (URISyntaxException e) {
			return;
		}
		
		long seed = coapCfg.seed;
		DevicesFromJson fromJson = new DevicesFromJson();
		List<Device> devs = fromJson.build(cfg.devJson, seed);
		DeviceControl devControl = new DeviceControl(msgQueue);
		for (final Device dev : devs) {
			devControl.addDevice(dev);
		}

		devControl.forever();
	}
	
	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Config cfg = new Config(args);
		CoAP app = new CoAP(cfg);
	}
}
