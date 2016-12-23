/**
 * 
 */
package net.iosynth.adapter;

import java.util.UUID;

/**
 * @author rradev
 *
 */
public class MqttConfig {
	public static String topic        = "iosynth/";
	public static int    qos          = 2;
	public static String brokerHost   = "localhost";
	public static int    brokerPort   = 1883;
	public static String broker;
	public static   UUID uuid         = UUID.randomUUID();
	public static String session      = Long.toString(uuid.getMostSignificantBits(), 36);
	public static String clientId     = "iosynth-0.0.1 " + session;

	/**
	 * Configurations possible:
	 *     -h hostname -p port -s session
	 *     -h hostname -p port
	 *     -h hostname
	 */
	public MqttConfig(String[] args) throws IllegalArgumentException {
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

}
