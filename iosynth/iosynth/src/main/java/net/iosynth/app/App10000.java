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
public class App10000 {
	public BlockingQueue<Message> msgQueue;
	MqttAdapter mqtt;
	
	public App10000(String[] args){
		msgQueue = new LinkedBlockingQueue<Message>();
		MqttAdapter mqtt = null;
		try {
			mqtt = new MqttAdapter(args, msgQueue);
		} catch (IllegalArgumentException e) {
			System.out.println("\nUsage: java -cp iosynth.jar App1000 -h hostname -p port -s session\n");
			System.exit(1);;
		}
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
    	App10000 a = new App10000(args);
    	a.sensorControl();
    }
}

