/*
 * Author:		Benjamin VIAL
 * ID:			29590765
 * Course:		COMP 352
 * Instructor:	Stuart THIEL
 * Due date:	Monday, May 29th, 2017
 * 
 * Please see the "quicksort_time.png" for a chart of execution time (data
 * graphed from the "qs_test.csv" file, in nanoseconds).
 * 
 * Textual responses to assignment questions:
 * 
 * 2.	The two sorts exhibit a similar growth rate for randomized input. The
 * 		only visible difference (on a log-log scale) is for an input size of
 * 		100, where the median of three approach has a higher execution time,
 * 		which may be caused by the extra computation involving finding the
 * 		median.
 * 		However, when fed a pathological input (described below), the execution
 * 		time of trivial pivot selection far exceeds that of the median of three
 * 		variant. This is because proper pivot selection is a primary factor in
 * 		optimizing the behavior of quicksort.
 * 
 * 3.	The pathological input for quicksort implementing trivial pivot
 * 		selection is an already-sorted list of integers. In this case, since
 * 		the leftmost element of the partition is chosen to perform the sort, a
 * 		list of increasing values will always cause the pivot to be the
 * 		smallest value, resulting in partitions of size 1 and (n-1), then 1 and
 * 		(n-2), and so on. In other words, the array will be partitioned
 * 		unevenly and the algorithm will run with its worst case time complexity
 * 		of O(n^2), even causing stack overflow errors for large input sizes.
 * 		This will also be the case for a list of values that are all equal.
 * 
 * 4.	Given the same pathological input as described above, the median of
 * 		of three approach will always select the middle element, and will
 * 		partition the array evenly, even in the case of a list of equal values.
 * 		The algorithm will run with a time complexity of O(n*log(n)), which
 * 		empirical analysis confirms (see included CSV file and chart). This
 * 		implementation also prevents recursive calls from piling up, and avoids
 * 		stack overflow issues encountered with trivial pivot selection.
 * 
 */

import gen.FixedGen;
import gen.RandomGen;
import sort.QSMedian;
import sort.QSNormal;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Implementation of the quicksort algorithm.
 * @author Benjamin Vial (29590765)
 * @see QSNormal, QSMedian, FixedGen, RandomGen
 */
public class QSDriver {
	private static Random rand = new Random();
	private static long seed;
	private static SORT_TYPE stype;
	private static GEN_TYPE gtype;
	private static int size = 0;
	private static final String help =
			("Usage:"
			+ "\n\tjava QSDriver <sort> <gen> <length> <seed>"
			+ "\nwhere:"
			+ "\n\t<sort>\t\tQSNormal  -or- QSMedian"
			+ "\n\t<gen>\t\tRandomGen -or- FixedGen"
			+ "\n\t<length>\t(Positive) Input size"
			+ "\n\t<seed>\t\t(Optional) Seed value for RandomGen");
	
	private enum SORT_TYPE {
		MEDIAN,
		NORMAL
	}
	
	private enum GEN_TYPE {
		FIXED,
		RANDOM
	}
	
	/**
	 * Parses command line inputs and feeds them to the algorithm.
	 * @param args Command line arguments
	 */
	private static void parse(String[] args) {
		if (args.length < 3 || args.length > 4) {
			System.out.println(help);
			System.exit(0);
		}
		
		switch (args[0]) {
		case "QSNormal":
			stype = SORT_TYPE.NORMAL;
			break;
		case "QSMedian":
			stype = SORT_TYPE.MEDIAN;
			break;
		default:
			System.out.println(help);
			System.exit(0);
			break;
		}
		
		switch (args[1]) {
		case "RandomGen":
			gtype = GEN_TYPE.RANDOM;
			break;
		case "FixedGen":
			gtype = GEN_TYPE.FIXED;
			break;
		default:
			System.out.println(help);
			System.exit(0);
			break;
		}
		
		size = Integer.parseInt(args[2]);
		if (size <= 0) {
			System.out.println(help);
			System.exit(0);
		}
		
		if (gtype == GEN_TYPE.FIXED) {
			if (stype == SORT_TYPE.NORMAL)
				QSNormal.sort(FixedGen.gen(size));
			if (stype == SORT_TYPE.MEDIAN)
				QSMedian.sort(FixedGen.gen(size));
		}
		if (gtype == GEN_TYPE.RANDOM) {
			if (args.length == 3) {
				if (stype == SORT_TYPE.NORMAL)
					QSNormal.sort(RandomGen.gen(size));
				if (stype == SORT_TYPE.MEDIAN)
					QSMedian.sort(RandomGen.gen(size));
			}
			if (args.length == 4) {
				long seed = Long.parseLong(args[3]);
				if (stype == SORT_TYPE.NORMAL)
					QSNormal.sort(RandomGen.gen(size, seed));
				if (stype == SORT_TYPE.MEDIAN)
					QSMedian.sort(RandomGen.gen(size, seed));
			}
		}
	}
	
	/**
	 * Testing method used to produce a CSV file with algorithm run times.
	 * @param n Number of tests to run (to provide a meaningful average)
	 */
	private static void testRun(int n) {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream("qs_test.csv"));
			pw.println("Size,"
					+ "QSNormal_Random,"
					+ "QSMedian_Random,"
					+ "QSNormal_Fixed,"
					+ "QSMedian_Fixed");
			size = 10;
			for (int i = 0; i < n; ++i) {
				size = 10;
				pw.println(testSort());
				size = 100;
				pw.println(testSort());
				size = 10000;
				pw.println(testSort());
				size = 1000000;
				pw.println(testSort());
			}
			pw.close();
		}
		catch(FileNotFoundException e) {
			System.err.println("Error: Could not create or write to log file \"qs_test.csv\".");
			System.exit(0);
		}
	}
	
	/**
	 * Provides output for the {@code testRun()} method.
	 * @return {@code String} containing the test results
	 */
	private static String testSort() {
		StringBuilder sb = new StringBuilder();
		rand.setSeed(System.nanoTime());
		seed = rand.nextLong();
		sb.append(size);
		sb.append(",");
		sb.append(QSNormal.sort(RandomGen.gen(size, seed)));
		sb.append(",");
		sb.append(QSMedian.sort(RandomGen.gen(size, seed)));
		sb.append(",");
		sb.append(QSNormal.sort(FixedGen.gen(size)));
		sb.append(",");
		sb.append(QSMedian.sort(FixedGen.gen(size)));
		return sb.toString();
	}
	
	/**
	 * Invokes the {@code parse()} method to begin processing input.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		parse(args);
	}
}
