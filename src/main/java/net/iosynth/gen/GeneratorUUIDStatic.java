/**
 * 
 */
package net.iosynth.gen;


/**
 * @author rradev
 *
 */
public final class GeneratorUUIDStatic {
	
	private static class Holder {
		static final GeneratorUUID rnd = new GeneratorUUID(new Xoroshiro128(System.currentTimeMillis()));
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
	public static String getUUID() {
		return Holder.rnd.getUUID();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 300; i++) {
			System.out.println(GeneratorUUIDStatic.getUUID());
		}
	}

}
