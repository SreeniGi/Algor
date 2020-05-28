import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] matrix;
    private WeightedQuickUnionUF uf;
    private int virtualTop = 0;
    private int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        matrix = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n);
        virtualBottom = n * n + 2;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        matrix[row - 1][col - 1] = true;
        if (row == 1) {
            uf.union(virtualTop, (row - 1) * matrix.length + col);
        }
        if (row == matrix.length) {
            uf.union(virtualBottom, (row - 1) * matrix.length + col);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union((row + 1) * matrix.length + col, row - 1);
        }
        if (row < matrix.length && isOpen(row + 1, col)) {
            uf.union((row + 1) * matrix.length + col, row + 1);
        }
        if (col > 1 && isOpen(col - 1, row)) {
            uf.union((row + 1) * matrix.length + col, col - 1);
        }
        if (col < matrix.length && isOpen(col + 1, row)) {
            uf.union((row + 1) * matrix.length + col, col + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return matrix[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col > matrix.length) {
            throw new IllegalArgumentException();
        }
        return col <= matrix.length && uf
                .connected((row - 1) * matrix.length + col, virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j]) {
                    ++count;
                }
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}