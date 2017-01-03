package net.iosynth.util;

import java.util.Random;

/*
 * Original C version at http://xoroshiro.di.unimi.it/xoroshiro128plus.c
 * Original C authors: Written in 2016 by David Blackman and Sebastiano Vigna
 * Original License: http://creativecommons.org/publicdomain/zero/1.0/
 */

public class Xoroshiro128 extends Random {   
    private static final long DOUBLE_MASK = (1L << 53) - 1;
    private static final double NORM_53 = 1. / (1L << 53);
    private long state0, state1;

    Xoroshiro128(final long seed) {
        setSeed(seed);
    }
   
    public long nextLong() {
        final long s0 = state0;
        long       s1 = state1;
        final long result = s0 + s1;

        s1 ^= s0;
        state0 = Long.rotateLeft(s0, 55) ^ s1 ^ (s1 << 14); // a, b
        state1 = Long.rotateLeft(s1, 36); // c

        return result;
    }
   
    public long nextLong(final long max) {
        long threshold = (0x7fffffffffffffffL - max + 1) % max;
        while (true) {
            long bits = nextLong() & 0x7fffffffffffffffL;
            if (bits >= threshold) {
                return bits % max;
            }
        }
    }

    public int nextInt(final int max) {
        int threshold = (0x7fffffff - max + 1) % max;
        while(true) {
            int bits = (int) (nextLong() & 0x7fffffff);
            if (bits >= threshold) {
                return bits % max;
            }
        }
    }
   
    public double nextDouble() {
        return (nextLong() & DOUBLE_MASK) * NORM_53;
    }
   
    public boolean nextBoolean() {
        return nextLong() < 0L;
    }
   
   
    static final long jump0 = 0xbeac0467eba5facbL;
    static final long jump1 = 0xd86b048b86aa9922L;
   
    /**
     * This is the jump function for the generator. It is equivalent
     * to 2^64 calls to next(); it can be used to generate 2^64
     * non-overlapping subsequences for parallel computations.
    */
    public void jump() {
        long s0 = 0;
        long s1 = 0;
       
        for (int b = 0; b < 64; b++) {
            if ((jump0 & 1L << b) != 0) {
                s0 ^= state0;
                s1 ^= state1;
            }
            nextLong();
        }

        for (int b = 0; b < 64; b++) {
            if ((jump1 & 1L << b) != 0) {
                s0 ^= state0;
                s1 ^= state1;
            }
            nextLong();
        }

        state0 = s0;
        state1 = s1;
    }
   
    public void setSeed(final long seed) {
        long state = seed + 0x9E3779B97F4A7C15L, z = state;
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        state0 = z ^ (z >>> 31);
        state += 0x9E3779B97F4A7C15L;
        z = state;
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        state1 = z ^ (z >>> 31);
    }

   
     public Xoroshiro128 copy() {
             Xoroshiro128 gen = new Xoroshiro128(state0);
            gen.state0 = state0;
            gen.state1 = state1;
            return gen;
    }
   
    public static void main(String[] args) {
        Xoroshiro128 gen = new Xoroshiro128(123);
        long s0 = System.currentTimeMillis();
        for(int i=0; i<10e7; i++){
            gen.nextGaussian();
        }
        long s1 = System.currentTimeMillis();
        System.out.println(String.valueOf(s1-s0));
    }

}
