/* *****************************************************************************
 *  Name:              Anatoly Kolomeets
 *  Last modified:     11/11/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95  = 1.96;
    private final double[] tries;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Args must not be positive");
        tries = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation attempt = new Percolation(n);
            while (!attempt.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                attempt.open(row, col);
            }
            tries[i] = (double) attempt.numberOfOpenSites() / (n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(tries);
    }
    //
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(tries);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - this.stddev() * CONFIDENCE_95 / Math.sqrt(this.tries.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + this.stddev() * CONFIDENCE_95 / Math.sqrt(this.tries.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        // Stopwatch time = new Stopwatch();
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
        // StdOut.println(time.elapsedTime());
    }
}
