package net.iosynth;

import java.net.URISyntaxException;
import java.util.ArrayList;
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
	protected List<BlockingQueue<Message>> msgQueues;
	protected AdapterCoap coap;
	
	protected CoAP(Config cfg) {
		// set configuration from Json file
		Gson gson = new Gson();
		ConfigCoap cfgCoap = gson.fromJson(cfg.cfgJson, ConfigCoap.class);
		cfgCoap.clients = cfgCoap.clients < 1 ? 1: cfgCoap.clients;
		
		// Setup clients
		msgQueues = new ArrayList<BlockingQueue<Message>>(cfgCoap.clients);
		for(int i=0; i<cfgCoap.clients; i++){
			msgQueues.add(new LinkedBlockingQueue<Message>());
		}
		for (BlockingQueue<Message> msgQueue : msgQueues) {
			try {
				coap = new AdapterCoap(cfgCoap, msgQueue);
			} catch (URISyntaxException e) {
				return;
			}
		}
		// Create devices
		long seed = cfgCoap.seed;
		DevicesFromJson fromJson = new DevicesFromJson();
		List<Device> devs = fromJson.build(cfg.devJson, seed);
		DeviceControl devControl = new DeviceControl(5);
		int k=0;
		int i=0;
		int devsPerClient = (int)Math.ceil((double)devs.size() / (double)cfgCoap.clients); 
		for (final Device dev : devs) {
			if(k>devsPerClient){
				k=0;
				i++;
			}
			devControl.addDevice(dev, msgQueues.get(i));
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
