/**
 * 
 */
package net.iosynth.gen;

/**
 * @author rradev
 *
 */
public final class GeneratorIPv4Static {
	/*
	 * RNG used by this class to create random based IPv4. In a holder class to
	 * defer initialization.
	 */
	private static class Holder {
		static final GeneratorIPv4 rnd = new GeneratorIPv4(new Xoroshiro128(System.currentTimeMillis()), null);
	}

	/**
	 * @param prefix
	 */
	public static void setPrefix(String prefix){
		Holder.rnd.setPrefix(prefix);
	}
	
	/**
	 * @param rnd
	 */
	public static void setRnd(Xoroshiro128 rnd){
		Holder.rnd.setRnd(rnd);
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
		GeneratorIPv4Static.getRandomIPv4();
		for (int i = 0; i < 300; i++) {
			System.out.println(GeneratorIPv4Static.getNextIPv4());
		}
	}

}
