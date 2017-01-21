/**
 * 
 */
package net.iosynth.gen;

import java.util.logging.Logger;


/**
 * @author rradev
 *
 */
public class GeneratorIPv4 {
	private Xoroshiro128 rnd;
	private int s;
	private final int state[] = new int[4];
	private static final String dot = ".";
	private final Logger logger = Logger.getLogger(GeneratorIPv4.class.getName());
	
	/**
	 * @param rnd 
	 * @param prefix 
	 * 
	 */
	public GeneratorIPv4(Xoroshiro128 rnd, String prefix) {
		this.rnd = rnd;
		setPrefix(prefix);
	}
	
	/**
	 * @param prefix
	 */
	public void setPrefix(String prefix) {
		if (prefix == null || prefix.length() == 0) {
			s = 0;
		} else {
			if (prefix.charAt(prefix.length() - 1) == '.') {
				// remove dot at the end
				prefix = prefix.substring(0, prefix.length() - 1);
			}
			String parts[] = prefix.split("\\.", 4);
			s = parts.length;
			for (int i = 0; i < parts.length; i++) {
				try {
					state[i] = Integer.parseInt(parts[i]);
					if (state[i] > 255) {
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					logger.severe("This is not a valid IPv4 prefix: " + parts[i]);
					throw e;
				}
			}
		}
	}
	
	/**
	 * @param rnd
	 */
	public void setRnd(Xoroshiro128 rnd){
		this.rnd = rnd;
	}
	
	
	/**
	 * @return random IPv4
	 */
	public String getRandomIPv4() {
		switch (s) {
		case 0:
			do {
				state[0] = rnd.nextInt(254);
			} while (state[0] == 0 || state[0] == 10 || state[0] == 127 || state[0] == 172 || state[0] == 192 || state[0] == 224);
		case 1:
			state[1] = rnd.nextInt(256);
		case 2:
			state[2] = rnd.nextInt(256);
		case 3:
			state[3] = rnd.nextInt(256);
			break;
		default:
			break;
		}
		return format();
	}
	
	/**
	 * @return next IPv4
	 */
	public String getNextIPv4(){
		state[3] += 1;
		if (state[3] > 254) {
			state[3] = 0;
			state[2] += 1;
			if (state[2] > 254) {
				state[2] = 0;
				state[1] += 1;
				if (state[1] > 254) {
					state[1] = 0;
				}
			}
		}
		return format();
	}
	
	protected String format(){
		StringBuilder b = new StringBuilder(15);
		b.append(state[0]).append(dot)
		.append(state[1]).append(dot)
		.append(state[2]).append(dot)
		.append(state[3]);
		return b.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// performance: 10^9 getRandomIPv4() = 136s
		Xoroshiro128 rnd = new Xoroshiro128(123);
		GeneratorIPv4 gen = new GeneratorIPv4(rnd, "");
		long start = System.currentTimeMillis();
		for(int i=0; i<1000000000; i++){
			gen.getRandomIPv4();
			//System.out.println(gen.getRandomIPv4());
		}
		long end = System.currentTimeMillis();
		System.out.println(String.valueOf(end-start));
	}

}
