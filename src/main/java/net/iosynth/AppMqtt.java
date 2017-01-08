package net.iosynth;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;

import net.iosynth.adapter.Config;
import net.iosynth.adapter.MqttAdapter;
import net.iosynth.adapter.MqttConfig;
import net.iosynth.device.Device;
import net.iosynth.device.DeviceControl;
import net.iosynth.device.DevicesFromJson;
import net.iosynth.util.Message;

/**
 * 
 *
 */
public class AppMqtt {
	protected BlockingQueue<Message> msgQueue;
	protected MqttAdapter mqtt;
	
	protected AppMqtt(Config cfg) {
		msgQueue = new LinkedBlockingQueue<Message>();
		// set configuration from Json file
		Gson gson = new Gson();
		MqttConfig mqttCfg = gson.fromJson(cfg.cfgJson, MqttConfig.class);
		mqtt = new MqttAdapter(mqttCfg, msgQueue);
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
		AppMqtt app = new AppMqtt(cfg);
	}
}
