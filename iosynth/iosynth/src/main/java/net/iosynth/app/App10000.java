package net.iosynth.app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.iosynth.adapter.Config;
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
public class App10000 {
	public BlockingQueue<Message> msgQueue;
	MqttAdapter mqtt;
	
	public App10000(Config cfg){
		msgQueue = new LinkedBlockingQueue<Message>();
		MqttAdapter mqtt = new MqttAdapter(cfg.cfgJson, msgQueue);
		mqtt.start();
	}
	
	public void sensorControl(){
		DeviceControl devsControl = new DeviceControl(msgQueue);
		int k=1;
		for(int i=0; i<5000; i++){
			Device dev1 = new DeviceFixedRate01();
			dev1.setRate(10000);
			dev1.setId(String.format("%04d", k));
			devsControl.addFixed(dev1);
			k++;
		}
		
		for(int i=0; i<5000; i++){
			Device dev3 = new DeviceVariableRate01();
			dev3.setId(String.format("%04d", k));
			devsControl.addVariable(dev3);
			k++;
		}
		
		devsControl.forever();
	}
	
    public static void main( String[] args ){
    	Config cfg = new Config(args);
    	App10000 a = new App10000(cfg);
    	a.sensorControl();
    }
}

