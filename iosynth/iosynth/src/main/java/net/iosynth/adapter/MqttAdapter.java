package net.iosynth.adapter;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.google.gson.Gson;

import net.iosynth.util.Message;

public class MqttAdapter extends Thread {
	// Adapter default configuration
	public String topic        = "iosynth/";
	public int    qos          = 0;
	public String broker       = "tcp://localhost:1883";
	public UUID uuid           = UUID.randomUUID();
	public String session      = Long.toString(uuid.getMostSignificantBits(), 36);
	public String clientId     = "iosynth-0.0.1 " + session;
	
	
    public MemoryPersistence persistence;
    MqttClient sampleClient;
    MqttConnectOptions connOpts;
    BlockingQueue<Message> msgQueue;
    
	public MqttAdapter(String cfgJson, BlockingQueue<Message> msgQueue) {
		// set configuration from Json file
		Gson gson = new Gson();
		MqttConfigClass cfg = gson.fromJson(cfgJson, MqttConfigClass.class);
		if(cfg.topic != null){
			topic = cfg.topic;
		}
		qos = cfg.qos;
		if(cfg.broker != null){
			broker = cfg.broker;
		}
		if(cfg.session != null){
			session = cfg.session;
		}
		
		this.msgQueue = msgQueue;
		persistence = new MemoryPersistence();
		try {
			sampleClient = new MqttClient(broker, clientId, persistence);
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
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");
			
			try {
				while (true) {
					final Message msg = msgQueue.take();
					//System.out.println("Publishing message: " + msg.getId() + " " + msg.getMsg());
					MqttMessage message = new MqttMessage(msg.getMsg().getBytes());
					message.setQos(qos);
					sampleClient.publish(topic + session + "/device/" + msg.getId(), message);
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
	
}
