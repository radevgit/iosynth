/**
 * 
 */
package net.iosynth.util;

/**
 * @author rradev
 *
 */
public class Exp1 {
	static double pdf[] = {2,3};
	static double cdf[];
	public Xoroshiro128 rng;
	
	/**
	 * 
	 */
	public Exp1() {
		rng = new Xoroshiro128(123);
	}

	public void toString(double a[]){
		for(int i=0; i<a.length; i++){
			System.out.print(String.valueOf(a[i]) + ", ");
		}
		System.out.println("\n");
	}
	public void calc(){
		cdf = new double[pdf.length];
		for(int i=0; i<pdf.length; i++) cdf[i] = 0;
	    cdf[0] = pdf[0];
		for(int i=1; i<pdf.length; i++){
			cdf[i] = cdf[i-1] + pdf[i]; 
		}
		toString(pdf);
		toString(cdf);
		double sum = cdf[cdf.length-1];
		for(int i=1; i<pdf.length; i++) cdf[i] = cdf[i]/sum;
		toString(cdf);
		
		for(int i=0; i<10; i++){
			double u = rng.nextDouble();
			int idx = (int)Math.floor(u);
			
			double ret = (cdf[idx] + (cdf.length-1) * u)*(cdf[idx+1]-cdf[idx]);
			System.out.println(ret);
		}
		
	}

	public static void main(String[] args) {
		Exp1 e = new Exp1();
		e.calc();
		

	}

}
