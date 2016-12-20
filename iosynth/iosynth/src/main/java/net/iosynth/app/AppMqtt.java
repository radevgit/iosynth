package net.iosynth.app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.iosynth.adapter.MqttAdapter;
import net.iosynth.device.Device;
import net.iosynth.device.DeviceFixedRate01;
import net.iosynth.device.DeviceVariableRate01;
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
		Device dev1 = new DeviceFixedRate01();
		dev1.setRate(5000);
		//devsControl.addFixed(dev1);
		Device dev2 = new DeviceFixedRate01();
		dev2.setRate(10000);
		//devsControl.addFixed(dev2);

		for(int i=0; i<10; i++){
			Device dev3 = new DeviceVariableRate01();
			dev3.setId(String.format("%04d", i));
			devsControl.addVariable(dev3);
		}
		
		devsControl.forever();
	}
	
    public static void main( String[] args ){
    	AppMqtt a = new AppMqtt();
    	a.sensorControl();
    }
}
