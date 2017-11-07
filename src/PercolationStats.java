// Brian Jordan (bjj17)
import java.util.*;

/**
 * Compute statistics on Percolation after performing T independent experiments on an N-by-N grid.
 * Compute 95% confidence interval for the percolation threshold, and  mean and std. deviation
 * Compute and print timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 * @author Josh Hug
 */

public class PercolationStats {
	// Instance Variables
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);
	double[] thresholds;
	double timesRun;
	// Constructor of PercolationStats Objects
	public PercolationStats(int N, int T){
		if (N <= 0 || T <= 0){
			throw new IllegalArgumentException("Grid size or number of tests is invalid");
		}
		else{
			thresholds = new double[T];
			timesRun = T;
			for (int i = 0; i < T; i++){
				PercolationUF tester = new PercolationUF(N, new QuickFind());
				ArrayList<int[]> values = new ArrayList<>();
				for (int j = 0; j < N; j++){
					for (int k = 0; k < N; k++){
						int[] pair = new int[2];
						pair[0] = j;
						pair[1] = k;
						values.add(pair);
						}
					}
				Collections.shuffle(values,ourRandom);
				for (int[] pair : values){
					if (! tester.percolates()){
						tester.open(pair[0], pair[1]);
						}
					else break;
					}
				double numOpen = tester.numberOfOpenSites();
				thresholds[i] = numOpen / (N * N);
				}
			}
		}
	// mean Method -- calls the mean Method in the StdStats class
	public double mean(){
		return StdStats.mean(thresholds);
	}
	// stddev Method -- calls the stddev Method in the StdStats class
	public double stddev(){
		return StdStats.stddev(thresholds);
	}
	// confidenceLow Method -- returns the low confidence value for the data
	public double confidenceLow(){
		double cL = mean() - ((1.96 * stddev()) / Math.sqrt(timesRun));
		return cL;
	}
	//confidenceHigh Method -- returns the high confidence value for the data
	public double confidenceHigh(){
		double cH = mean() + ((1.96 * stddev()) / Math.sqrt(timesRun));
		return cH;
	}
	// main Method -- runs and displays the result of tests
	public static void main(String[] args) {
		double start = System.nanoTime();
		PercolationStats ps = new PercolationStats(5,100);
		double end = System.nanoTime();
		double time = (end - start) / 1e9;
		System.out.printf("mean: %1.4f, time: %1.4f\n", ps.mean(),time);
	}
}
