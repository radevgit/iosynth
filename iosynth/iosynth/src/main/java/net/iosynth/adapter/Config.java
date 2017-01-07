/**
 * Parse command line arguments and loads configuration data in strings.
 */
package net.iosynth.adapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
	
	/**
	 * Usage: java -cp iosynth.jar -c config.json -d devices.json
	 * @param args command line args[]
	 */
	public Config(String[] args) {
		
		if (args.length != 4) {
			System.out.println("\nUsage: java -cp iosynth.jar net.iosynth.app.App -c config.json -d devices.json\n");
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
				System.out.println("\nUsage: java -cp iosynth.jar net.iosynth.app.App -c config.json -d devices.json\n");
				System.exit(1);
			}
		}
		
		try {
			cfgJson = new String(Files.readAllBytes(Paths.get(cfgFile)));
		} catch (IOException e) {
			// TODO
		}
		try {
			devJson = new String(Files.readAllBytes(Paths.get(devFile)));
		} catch (IOException e) {
			// TODO
		}
	}

}
