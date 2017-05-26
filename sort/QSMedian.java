package sort;

/**
 * Quicksort implementation using median-of-three pivot selection.
 * @author Benjamin Vial (29590765)
 * @see QSDriver, QSNormal
 */
public final class QSMedian {
	/**
	 * Convenience method for invoking the quicksort algorithm.
	 * @param a Array of integers to sort
	 * @return Elapsed run time; {@code -1} if {@code StackOverflowError} is
	 * thrown
	 */
	public static long sort(int[] a) {
		long start = 0;
		long end = 0;
		long elapsed = 0;
		start = System.nanoTime();
		try {
			sort(a, 0, a.length-1);
		}
		catch (StackOverflowError e) {
			System.out.println("QSMedian: Stack overflow error! Returning -1.");
			return -1;
		}
		end = System.nanoTime();
		elapsed = end-start;
		System.out.println("QSMedian: Sorted " + a.length + " elements in " + (elapsed/1000.0) + " microseconds.");
		return elapsed;
	}
	
	/**
	 * Provides means to output the array (before or after sorting).
	 * @param a Array of integers to print
	 */
	private static void print(int[] a) {
		for (int i = 0; i < a.length; ++i)
			System.out.print(a[i] + " ");
		System.out.println();
	}
	
	/**
	 * Recursive quicksort algorithm.
	 * @param a Array of integers to sort
	 * @param left Start index
	 * @param right End index
	 */
	private static void sort(int[] a, int left, int right) {
		int k = partition(a, left, right);
		if (left < k-1)
			sort(a, left, k-1);
		if (k < right)
			sort(a, k, right);
	}
	
	/**
	 * Partitions a given array around a pivot, placing smaller elements to the
	 * left and larger elements to the right
	 * @param a Array of integers to sort
	 * @param left Start index
	 * @param right End index
	 * @return Index of the last sorted element to allow for recursion
	 */
	private static int partition(int[] a, int left, int right) {
		int pivot = a[median(a, left, right)];
		while (left <= right) {
			while (a[left] < pivot)
				++left;
			while (a[right] > pivot)
				--right;
			if (left <= right) {
				swap(a, left, right);
				++left;
				--right;
			}
		}
		return left;
	}
	
	/**
	 * Computes the median-of-three of a partition for pivot selection.
	 * @param a Array of integers to sort
	 * @param left Start index
	 * @param right End index
	 * @return Index of the median, positioned as the second-to-last element
	 */
	private static int median(int[] a, int left, int right) {
		int mid = (left+right)/2;
		if (a[left] > a[mid])
			swap(a, left, mid);
		if (a[left] > a[right])
			swap(a, left, right);
		if (a[mid] > a[right])
			swap(a, mid, right);
		swap(a, mid, right-1);
		return right-1;
	}
	
	/**
	 * Swaps two elements in an array.
	 * @param a Array of integers
	 * @param i First index to swap
	 * @param j Second index to swap
	 */
	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
}
