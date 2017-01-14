package net.iosynth;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;

import net.iosynth.adapter.Config;
import net.iosynth.adapter.Message;
import net.iosynth.adapter.AdapterRabbit;
import net.iosynth.adapter.ConfigRabbit;
import net.iosynth.device.Device;
import net.iosynth.device.DeviceControl;
import net.iosynth.device.DevicesFromJson;

/**
 * 
 *
 */
public class RabbitMQ {
	protected List<BlockingQueue<Message>> msgQueues;
	protected AdapterRabbit rabbit;
	
	protected RabbitMQ(Config cfg) {
		// set configuration from Json file
		Gson gson = new Gson();
		ConfigRabbit cfgRabbit = gson.fromJson(cfg.cfgJson, ConfigRabbit.class);
		cfgRabbit.clients = cfgRabbit.clients < 1 ? 1: cfgRabbit.clients;
		
		// Setup clients
		msgQueues = new ArrayList<BlockingQueue<Message>>(cfgRabbit.clients);
		for(int i=0; i<cfgRabbit.clients; i++){
			msgQueues.add(new LinkedBlockingQueue<Message>());
		}
		for (BlockingQueue<Message> msgQueue : msgQueues) {
			try {
				rabbit = new AdapterRabbit(cfgRabbit, msgQueue);
			} catch (URISyntaxException e) {
				return;
			}
		}
		// Create devices
		long seed = cfgRabbit.seed;
		DevicesFromJson fromJson = new DevicesFromJson();
		List<Device> devs = fromJson.build(cfg.devJson, seed);
		DeviceControl devControl = new DeviceControl(5);
		int k=0;
		int i=0;
		int devsPerClient = (int)Math.ceil((double)devs.size() / (double)cfgRabbit.clients); 
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
		RabbitMQ app = new RabbitMQ(cfg);
	}
}
