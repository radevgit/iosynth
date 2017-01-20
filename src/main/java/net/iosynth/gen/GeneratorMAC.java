/**
 * 
 */
package net.iosynth.gen;

import java.util.logging.Logger;

/**
 * @author rradev
 *
 */
public final class GeneratorMAC {
	private Xoroshiro128 rnd;
	private int s;
	private final int state[] = new int[6];
	private static final String fmt = "%02X";
	private static final String sem = ":";
	private static final String sem64 = ":FF:FE:";
	private final Logger logger = Logger.getLogger(GeneratorMAC.class.getName());
	
	/**
	 * @param rnd
	 */
	public GeneratorMAC(Xoroshiro128 rnd, String prefix) {
		this.rnd = rnd;
		if (prefix == null || prefix.length() == 0) {
			s = 0;
		} else {
			if (prefix.charAt(prefix.length() - 1) == ':') {
				prefix = prefix.substring(0, prefix.length() - 1); // remove colon at the end
			}
			String parts[] = prefix.split(":", 3);
			s = parts.length;
			for (int i = 0; i < parts.length; i++) {
				try {
					state[i] = Integer.parseInt(parts[i], 16);
					if (state[i] > 0xFF) {
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					logger.severe("This is not a valid MAC prefix: " + parts[i]);
					throw e;
				}
			}
		}
	}

	
	/**
	 * @return SDIDMac48
	 */
	public String getRandom48() {
		return getRandom(0);
	}

	/**
	 * @return MAC64
	 */
	public String getRandom64() {
		return getRandom(1);
	}

	/**
	 * @return SDIDMac48
	 */
	public String getNext48() {
		return getNext(0);
	}

	/**
	 * @return MAC64
	 */
	public String getNext64() {
		return getNext(1);
	}

	protected String getRandom(int f) {
		switch (s) {
		case 0:
			state[0] = rnd.nextInt(128) * 2;
		case 1:
			state[1] = rnd.nextInt(256);
		case 2:
			state[2] = rnd.nextInt(256);
		default:
			break;
		}
		state[3] = rnd.nextInt(256);
		state[4] = rnd.nextInt(256);
		state[5] = rnd.nextInt(256);
		switch (f) {
		case 0:
			return format48();
		case 1:
			return format64();
		default:
			return null;
		}
	}

	private String getNext(int f) {
		state[5] += 1;
		if (state[5] > 255) {
			state[5] = 0;
			state[4] += 1;
			if (state[4] > 255) {
				state[4] = 0;
				state[3] += 1;
				if (state[3] > 255) {
					state[3] = 0;
				}
			}
		}
		switch (f) {
		case 0:
			return format48();
		case 1:
			return format64();
		default:
			return null;
		}
	}

	private String format48(){
		StringBuilder b = new StringBuilder(15);
		b.append(String.format(fmt, state[0])).append(sem)
		 .append(String.format(fmt, state[1])).append(sem)
		 .append(String.format(fmt, state[2])).append(sem)
		 .append(String.format(fmt, state[3])).append(sem)
		 .append(String.format(fmt, state[4])).append(sem)
		 .append(String.format(fmt, state[5]));
		return b.toString();
	}
	
	private String format64(){
		StringBuilder b = new StringBuilder(21);
		b.append(String.format(fmt, state[0])).append(sem)
		 .append(String.format(fmt, state[1])).append(sem)
		 .append(String.format(fmt, state[2])).append(sem64)
		 .append(String.format(fmt, state[3])).append(sem)
		 .append(String.format(fmt, state[4])).append(sem)
		 .append(String.format(fmt, state[5]));
		return b.toString();
	}	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// performance: 10^7 getRandom48() = 61s 
		Xoroshiro128 rnd = new Xoroshiro128(123);
		GeneratorMAC gen = new GeneratorMAC(rnd, "");
		long start = System.currentTimeMillis();
		for(int i=0; i<10000000; i++){
			gen.getRandom48();
			//System.out.println(gen.getRandom48());
		}
		long end = System.currentTimeMillis();
		System.out.println(String.valueOf(end-start));
	}

}
