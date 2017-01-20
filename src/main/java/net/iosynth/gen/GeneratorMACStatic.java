/**
 * 
 */
package net.iosynth.gen;

/**
 * @author rradev
 *
 */
public final class GeneratorMACStatic {
	/*
	 * RNG used by this class to create random based MACs. In a holder class to
	 * defer initialization.
	 */
	private static class Holder {
		static final GeneratorMAC rnd = new GeneratorMAC(new Xoroshiro128(System.currentTimeMillis()), null);
	}

	/**
	 * @param prefix
	 */
	public static void setPrefix(String prefix){
		Holder.rnd.setPrefix(prefix);
	}
	
	/**
	 * @return Mac48
	 */
	public static String getRandom48() {
		return Holder.rnd.getRandom48();
	}

	/**
	 * @return MAC64
	 */
	public static String getRandom64() {
		return Holder.rnd.getRandom64();
	}

	/**
	 * @return MAC48
	 */
	public static String getNext48() {
		return Holder.rnd.getNext48();
	}

	/**
	 * @return MAC64
	 */
	public static String getNext64() {
		return Holder.rnd.getNext64();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeneratorMACStatic.getRandom48();
		for (int i = 0; i < 300; i++) {
			System.out.println(GeneratorMACStatic.getNext48());
		}
	}

}
