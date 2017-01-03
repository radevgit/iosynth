package net.iosynth.adapter;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import net.iosynth.util.Message;

public class MqttAdapter extends Thread {
    
    MemoryPersistence persistence;
    MqttClient sampleClient;
    MqttConnectOptions connOpts;
    BlockingQueue<Message> msgQueue;
    MqttConfig config;
    
	public MqttAdapter(String[] args, BlockingQueue<Message> msgQueue) {
		try {
			config = new MqttConfig(args);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.msgQueue = msgQueue;
		persistence = new MemoryPersistence();
		try {
			sampleClient = new MqttClient(config.broker, config.clientId, persistence);
			connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			System.out.println("Connecting to broker: " + config.broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");
			
			try {
				while (true) {
					final Message msg = msgQueue.take();
					//System.out.println("Publishing message: " + msg.getId() + " " + msg.getMsg());
					MqttMessage message = new MqttMessage(msg.getMsg().getBytes());
					message.setQos(config.qos);
					sampleClient.publish(config.topic + config.session + "/device/" + msg.getId(), message);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sampleClient.disconnect();
			System.out.println("Disconnected");
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		} finally {

		}
		
	}
	
	public static void main(String[] args) {
	}

}
