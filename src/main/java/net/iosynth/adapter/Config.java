/**
 * Parse command line arguments and load configuration and devices settings.
 */
package net.iosynth.adapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author rradev
 *
 */
public class Config {
	/**
	 * Adapter and global configuration json
	 */
	public String cfgJson;
	/**
	 * Devices configuration json
	 */
	public String devJson;
	private String cfgFile;
	private String devFile;
	private static String usage = "\nUsage: java -cp iosynth.jar net.iosynth.Mqtt -c config.json -d devices.json\n";
	Logger logger = Logger.getLogger(Config.class.getName());
	
	/**
	 * Usage: java -cp iosynth.jar -c config.json -d devices.json
	 * @param args command line args[]
	 */
	public Config(String[] args) {
		
		if (args.length != 4) {
			logger.warning(usage);
			System.exit(1);
		}
		
		for (int i = 0; i < args.length; i = i + 2) {
			switch (args[i].substring(0, 2)) {
			case "-c":
				cfgFile = args[i + 1];
				break;
			case "-d":
				devFile = args[i + 1];
				break;
			default:
				logger.warning(usage);
				System.exit(1);
			}
		}
		
		try {
			cfgJson = new String(Files.readAllBytes(Paths.get(cfgFile)));
		} catch (IOException ie) {
			logger.log(Level.SEVERE, ie.toString(), ie);
			System.exit(1);
		}
		try {
			devJson = new String(Files.readAllBytes(Paths.get(devFile)));
		} catch (IOException ie) {
			logger.log(Level.SEVERE, ie.toString(), ie);
			System.exit(1);
		}
	}

}
