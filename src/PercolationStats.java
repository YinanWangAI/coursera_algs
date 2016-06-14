/**
 * Created by yinan on 16/6/13.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n;
    private int t;
    private double[] meanVec;

    public PercolationStats(int N, int T) {

        if (N <= 0 || T <= 0) throw new java.lang.IllegalArgumentException();
        n = N;
        t = T;
        meanVec = new double[t];
        runSimulation();
    }     // perform T independent experiments on an N-by-N grid

    private void runSimulation() {
        for (int j = 0; j < t; j++) {
            Percolation grid = new Percolation(n);
            double openNum = 0;
            while (!grid.percolates()) {
                int rowIndex = StdRandom.uniform(n) + 1;
                int colIndex = StdRandom.uniform(n) + 1;
                if (!grid.isOpen(rowIndex, colIndex)) {
                    grid.open(rowIndex, colIndex);
                    openNum++;
                }
            }
            double percolationProb = openNum / (n * n);
            meanVec[j] = percolationProb;
        }
    }

    public double mean() {

        return StdStats.mean(meanVec);
    }                     // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(meanVec);
    }                  // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(t);
    }              // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(t);
    }             // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats testClass = new PercolationStats(n, t);
        System.out.println("mean = " + testClass.mean());
        System.out.println("sd = " + testClass.stddev());
        System.out.println("95% confidence interval = " + testClass.confidenceLo() +
        ", " + testClass.confidenceHi());
    }  // test client (described below)
}
