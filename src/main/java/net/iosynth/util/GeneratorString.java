/**
 * 
 */
package net.iosynth.util;

/**
 * @author rradev
 *
 */
public class GeneratorString {
	private Xoroshiro128 rnd;
	int min, max;
	char alphabet[];

	public GeneratorString(Xoroshiro128 rnd, char[] alphabet) {
		init(rnd, alphabet, 1, 10);
	}
	
	/**
	 * @param rnd 
	 * @param alphabet 
	 * @param min 
	 * @param max 
	 */
	public GeneratorString(Xoroshiro128 rnd, char[] alphabet, int min, int max) {
		init(rnd, alphabet, min, max);
	}
	
	private void init(Xoroshiro128 rnd, char[] alphabet, int min, int max){
		this.rnd = rnd;
		this.alphabet = alphabet;
		this.min = min;
		this.max = max;
	}
	
	public String getString(){
		int size = rnd.nextInt(max - min) + min;
		StringBuilder b = new StringBuilder(size);
		for(int i=0; i<size; i++){
			b.append(alphabet[rnd.nextInt(alphabet.length)]);
		}
		return b.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// performance: 10^9 getString() 10size = 124s
		Xoroshiro128 rnd = new Xoroshiro128(123);
		char alpha[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o'};
		GeneratorString gen = new GeneratorString(rnd, alpha, 10, 11);
		long start = System.currentTimeMillis();
		for(int i=0; i<1000000000; i++){
			gen.getString();
			//System.out.println(gen.getString());
		}
		long end = System.currentTimeMillis();
		System.out.println(String.valueOf(end-start));
	}

}
