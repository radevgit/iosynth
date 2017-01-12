package net.iosynth;

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
import net.iosynth.device.DevicesFromJson;

/**
 * 
 *
 */
public class Mqtt {
	protected BlockingQueue<Message> msgQueue;
	protected AdapterMqtt mqtt;
	
	protected Mqtt(Config cfg) {
		msgQueue = new LinkedBlockingQueue<Message>();
		// set configuration from Json file
		Gson gson = new Gson();
		ConfigMqtt mqttCfg = gson.fromJson(cfg.cfgJson, ConfigMqtt.class);
		try {
			mqtt = new AdapterMqtt(mqttCfg, msgQueue);
		} catch (MqttException e) {
			return;
		}
		long seed = mqttCfg.seed;
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
		Mqtt app = new Mqtt(cfg);
	}
}
