package net.iosynth.app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.iosynth.adapter.MqttAdapter;
import net.iosynth.device.Device;
import net.iosynth.device.Device01;
import net.iosynth.device.DeviceControl;
import net.iosynth.util.Message;

/**
 * 
 *
 */
public class AppMqtt {
	public BlockingQueue<Message> msgQueue;
	
	public AppMqtt(){
		msgQueue = new LinkedBlockingQueue<Message>();
	}
	
	public void sensorControl(){
		DeviceControl devsControl = new DeviceControl(msgQueue);
		MqttAdapter  mqtt = new MqttAdapter(msgQueue);
		mqtt.start();
		Device dev1 = new Device01();
		devsControl.add(dev1);
		devsControl.forever();
	}
	
    public static void main( String[] args ){
    	AppMqtt a = new AppMqtt();
    	a.sensorControl();
    }
}
