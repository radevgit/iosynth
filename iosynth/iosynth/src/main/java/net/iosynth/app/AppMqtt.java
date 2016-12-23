package net.iosynth.app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.iosynth.adapter.MqttAdapter;
import net.iosynth.device.Device;
import net.iosynth.device.DeviceControl;
import net.iosynth.device.DeviceFixedRate01;
import net.iosynth.device.DeviceVariableRate01;
import net.iosynth.util.Message;

/**
 * 
 *
 */
public class AppMqtt {
	public BlockingQueue<Message> msgQueue;
	MqttAdapter mqtt;
	
	public AppMqtt(String[] args){
		msgQueue = new LinkedBlockingQueue<Message>();
		MqttAdapter mqtt = null;
		try {
			mqtt = new MqttAdapter(args, msgQueue);
		} catch (IllegalArgumentException e) {
			System.out.println("\nUsage: java -jar iosynth.jar -h hostname -p port\n");
			System.exit(1);;
		}
		mqtt.start();
	}
	
	public void sensorControl(){
		DeviceControl devsControl = new DeviceControl(msgQueue);
		Device dev1 = new DeviceFixedRate01();
		dev1.setRate(5000);
		devsControl.addFixed(dev1);
		Device dev2 = new DeviceFixedRate01();
		dev2.setRate(10000);
		devsControl.addFixed(dev2);

		for(int i=0; i<4; i++){
			Device dev3 = new DeviceVariableRate01();
			dev3.setId(String.format("%04d", i));
			devsControl.addVariable(dev3);
		}
		
		devsControl.forever();
	}
	
    public static void main( String[] args ){
    	AppMqtt a = new AppMqtt(args);
    	a.sensorControl();
    }
}
