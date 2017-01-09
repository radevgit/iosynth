package net.iosynth;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;

import net.iosynth.adapter.Config;
import net.iosynth.adapter.RabbitAdapter;
import net.iosynth.adapter.RabbitConfig;
import net.iosynth.device.Device;
import net.iosynth.device.DeviceControl;
import net.iosynth.device.DevicesFromJson;
import net.iosynth.util.Message;

/**
 * 
 *
 */
public class RabbitMQ {
	protected BlockingQueue<Message> msgQueue;
	protected RabbitAdapter rabbit;
	
	protected RabbitMQ(Config cfg) {
		msgQueue = new LinkedBlockingQueue<Message>();
		// set configuration from Json file
		Gson gson = new Gson();
		RabbitConfig rabbitCfg = gson.fromJson(cfg.cfgJson, RabbitConfig.class);
		rabbit = new RabbitAdapter(rabbitCfg, msgQueue);
		long seed = rabbitCfg.seed;
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
		RabbitMQ app = new RabbitMQ(cfg);
	}
}
