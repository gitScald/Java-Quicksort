package gen;

import java.util.Random;

/**
 * Provides an array of randomized integers for the quicksort algorithm.
 * @author Benjamin Vial (29590765)
 * @see QSDriver, FixedGen
 */
public final class RandomGen {
	private static Random rand;
	
	/**
	 * Generates an array of randomized integers. Uses the current system time
	 * as a seed value (default).
	 * @param size Input size
	 * @return Array of randomized integers (from 1 to 99)
	 */
	public static int[] gen(int size) {
		rand = new Random(System.nanoTime());
		int[] a = new int[size];
		for (int i = 0; i < size; ++i)
			a[i] = rand.nextInt(100);
		System.out.println("Sorting randomized input... ");
		return a;
	}
	
	/**
	 * Generates an array of randomized integers. Uses a passed {@code long} as
	 * a seed value.
	 * @param size Input size
	 * @param seed Seed value
	 * @return Array of randomized integers (from 1 to 99)
	 */
	public static int[] gen(int size, long seed) {
		rand = new Random();
		rand.setSeed(seed);
		int[] a = new int[size];
		for (int i = 0; i < size; ++i)
			a[i] = rand.nextInt(100);
		System.out.println("Sorting randomized input (seed: " + seed +")... ");
		return a;
	}
}
