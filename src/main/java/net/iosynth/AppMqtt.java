package net.iosynth;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.iosynth.adapter.Config;
import net.iosynth.adapter.MqttAdapter;
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
	
	protected AppMqtt(Config cfg){
		msgQueue = new LinkedBlockingQueue<Message>();
		MqttAdapter mqtt = new MqttAdapter(cfg.cfgJson, msgQueue);
		mqtt.start();
		DevicesFromJson fromJson = new DevicesFromJson();
		List<Device> devs = fromJson.build(cfg.devJson);
		DeviceControl devControl = new DeviceControl(msgQueue);
		for(final Device dev: devs){
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
