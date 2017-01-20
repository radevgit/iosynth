/**
 * 
 */
package net.iosynth.gen;

/**
 * @author rradev
 *
 */
public final class GeneratorStaticIPv4 {
	/*
	 * RNG used by this class to create random based IPv4. In a holder class to
	 * defer initialization.
	 */
	private static class Holder {
		static final GeneratorIPv4 rnd = new GeneratorIPv4(new Xoroshiro128(System.currentTimeMillis()), null);
	}

	/**
	 * @return IPv4
	 */
	public static String getRandomIPv4() {
		return Holder.rnd.getRandomIPv4();
	}

	/**
	 * @return IPv4
	 */
	public static String getNextIPv4() {
		return Holder.rnd.getNextIPv4();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeneratorStaticIPv4.getRandomIPv4();
		for (int i = 0; i < 300; i++) {
			System.out.println(GeneratorStaticIPv4.getNextIPv4());
		}
	}

}
