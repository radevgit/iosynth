/**
 * 
 */
package net.iosynth.adapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import com.google.gson.Gson;

/**
 * @author rradev
 *
 */
public class MqttConfig {
	public String topic        = "iosynth/";
	public int    qos          = 2;
	public String broker       = "tcp://localhost:1883";
	public UUID uuid           = UUID.randomUUID();
	public String session      = Long.toString(uuid.getMostSignificantBits(), 36);
	public String clientId     = "iosynth-0.0.1 " + session;
	public        String configFile   ;

	/**
	 * Configurations possible:
	 *     -h hostname -p port -s session
	 *     -h hostname -p port
	 *     -h hostname
	 */
	public MqttConfig(String[] args) throws IllegalArgumentException, IOException {
		if(args.length == 0) {
			broker = "tcp://localhost:1883";
			return;
		}
		
		if (args.length != 2 && args.length != 4) {
			throw new IllegalArgumentException("Invalid arguments");
		}
		
		for (int i = 0; i < args.length; i = i + 2) {
			switch (args[i].substring(0, 2)) {
			case "-c":
				configFile = args[i + 1];
				break;
			
			default:
				throw new IllegalArgumentException("Invalid argument: " + args[i].substring(0, 2));
			}
		}
		String json = null;
		try {
			json = new String(Files.readAllBytes(Paths.get(configFile)));
		} catch (IOException e) {
			throw new IOException("Cannot read config file:" + e.getMessage());
		}
		Gson gson = new Gson();
		MqttConfigClass cfg = gson.fromJson(json, MqttConfigClass.class);
		if(cfg.topic != null){
			topic = cfg.topic;
		}
		if(cfg.broker != null){
			broker = cfg.broker;
		}
		if(cfg.session != null){
			session = cfg.session;
		}
	}
	
	/**
	 * Configurations possible:
	 *     -h hostname -p port -s session
	 *     -h hostname -p port
	 *     -h hostname
	 */
	/*
	public void MqttConfigOld(String[] args) throws IllegalArgumentException {
		if(args.length == 0) {
			broker = "tcp://" + brokerHost + ":" + brokerPort;
			return;
		}
		
		if (args.length != 2 && args.length != 4 && args.length != 6) {
			throw new IllegalArgumentException("Invalid arguments");
		}
		
		for (int i = 0; i < args.length; i = i + 2) {
			switch (args[i].substring(0, 2)) {
			case "-h":
				brokerHost = args[i + 1];
				break;
			case "-p":
				// if(args.length<4){
				// System.out.println("Usage: java -jar iosynth.jar -h hostname
				// -p port\n\n");
				// throw new IllegalArgumentException("Invalid arguments");
				// }
				brokerPort = Integer.parseInt(args[i + 1]);
				break;
			case "-s":
				session  = args[i + 1];
				break;
			default:
				throw new IllegalArgumentException("Invalid argument: " + args[i].substring(0, 2));
			}
		}
		broker = "tcp://" + brokerHost + ":" + brokerPort;
	}
   */
}
