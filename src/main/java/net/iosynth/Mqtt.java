package net.iosynth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.google.gson.Gson;

import net.iosynth.adapter.Config;
import net.iosynth.adapter.Message;
import net.iosynth.adapter.AdapterMqtt;
import net.iosynth.adapter.ConfigMqtt;
import net.iosynth.device.Device;
import net.iosynth.device.DeviceControl;
import net.iosynth.device.DeviceTemplate;
import net.iosynth.device.DevicesFromJson;

/**
 * 
 *
 */
public class Mqtt {
	protected List<BlockingQueue<Message>> msgQueues;
	protected AdapterMqtt mqtt;
	
	protected Mqtt(Config cfg) {
		// set configuration from Json file
		Gson gson = new Gson();
		ConfigMqtt cfgMqtt = gson.fromJson(cfg.cfgJson, ConfigMqtt.class);
		cfgMqtt.clients = cfgMqtt.clients < 1 ? 1: cfgMqtt.clients;
		
		// Setup clients
		msgQueues = new ArrayList<BlockingQueue<Message>>(cfgMqtt.clients);
		for(int i=0; i<cfgMqtt.clients; i++){
			msgQueues.add(new LinkedBlockingQueue<Message>());
		}
		for (BlockingQueue<Message> msgQueue : msgQueues) {
			try {
				mqtt = new AdapterMqtt(cfgMqtt, msgQueue);
			} catch (MqttException e) {
				return;
			}
		}
		// Create devices
		long seed = cfgMqtt.seed;
		DevicesFromJson fromJson = new DevicesFromJson();
		List<Device> devs = fromJson.build(cfg.devJson, seed);
		DeviceControl devControl = new DeviceControl(5);
		int k=0;
		int i=0;
		int devsPerClient = (int)Math.ceil((double)devs.size() / (double)cfgMqtt.clients); 
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
		Mqtt app = new Mqtt(cfg);
	}
}
