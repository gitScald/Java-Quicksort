package gen;

/**
 * Provides a pathological input for the quicksort algorithm (here, an already
 * sorted array of integers).
 * @author Benjamin Vial (29590765)
 * @see QSDriver, RandomGen
 */
public final class FixedGen {
	/**
	 * Generates an already-sorted array of integers.
	 * @param size Input size
	 * @return Already-sorted array of integers
	 */
	public static int[] gen(int size) {
		int[] a = new int[size];
		a[0] = 0;
		for (int i = 1; i < size; ++i)
			a[i] = i;
		System.out.println("Sorting pathological input... ");
		return a;
	}
}
