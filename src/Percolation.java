/**
 * Created by yinan on 16/6/13.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private WeightedQuickUnionUF gridUnionOneVirtual;
    private WeightedQuickUnionUF gridUnionTwoVirtual;
    private int n;

    public Percolation(int N)  {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        n = N;
        grid = new boolean[n][n];
        gridUnionTwoVirtual = new WeightedQuickUnionUF(n * n + 2);  // add two virtual nodes
        gridUnionOneVirtual = new WeightedQuickUnionUF(n * n + 1);  // add one virtual nodes, deal with backwash
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                grid[j][k] = false;
            }
        }
    }             // create N-by-N grid, with all sites blocked

    public void open(int i, int j) {
        if (i > n || j > n) throw new java.lang.IndexOutOfBoundsException();

        if (isOpen(i, j)) return;

        int presentPosition = n * (i - 1) + j - 1;
        int left = presentPosition - 1;
        int right = presentPosition + 1;
        int up = presentPosition - n;
        int down = presentPosition + n;

        grid[i - 1][j - 1] = true;
        if (i == 1) {
            gridUnionTwoVirtual.union(presentPosition, n * n);
            gridUnionOneVirtual.union(presentPosition, n * n);
        }
        if (i == n) gridUnionTwoVirtual.union(presentPosition, n * n + 1);

        // connect two nearby nodes
        if (i > 1 && isOpen(i - 1, j)) {
            gridUnionOneVirtual.union(presentPosition, up);
            gridUnionTwoVirtual.union(presentPosition, up);
        }
        if (i < n && isOpen(i + 1, j)) {
            gridUnionOneVirtual.union(presentPosition, down);
            gridUnionTwoVirtual.union(presentPosition, down);
        }
        if (j > 1 && isOpen(i, j - 1)) {
            gridUnionOneVirtual.union(presentPosition, left);
            gridUnionTwoVirtual.union(presentPosition, left);
        }
        if (j < n && isOpen(i, j + 1)) {
            gridUnionOneVirtual.union(presentPosition, right);
            gridUnionTwoVirtual.union(presentPosition, right);
        }

    }          // open site (row i, column j) if it is not open already

    public boolean isOpen(int i, int j) {
        if (i > n || j > n) throw new java.lang.IndexOutOfBoundsException();
        return grid[i - 1][j - 1];
    }     // is site (row i, column j) open?

    public boolean isFull(int i, int j) {
        if (i > n || j > n) throw new java.lang.IndexOutOfBoundsException();

        if (!isOpen(i, j)) return false;

        int presentPosition = n * (i - 1) + j - 1;

        return gridUnionOneVirtual.connected(n * n, presentPosition);
    }     // is site (row i, column j) full?

    public boolean percolates() {

        return gridUnionTwoVirtual.connected(n * n, n * n + 1);
    }            // does the system percolate?

    public static void main(String[] args) {
        return;
    } // test client (optional)
}