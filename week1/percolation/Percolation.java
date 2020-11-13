/* *****************************************************************************
 *  Name:              Anatoly Kolomeets
 *  Last modified:     11/11/2020
 **************************************************************************** */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private final WeightedQuickUnionUF model;
    private final WeightedQuickUnionUF modelAlt;

    private final int loId, hiId;
    private final int n;
    private int openSitesCount;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int t) {
        if (t <= 0) {
            throw new IllegalArgumentException("Incorrect N");
        }
        openSitesCount = 0;
        n = t - 1;
        loId = t*t;
        hiId = loId + 1;
        grid = new boolean[t][t];
        model = new WeightedQuickUnionUF(hiId+1);
        modelAlt = new WeightedQuickUnionUF(hiId);
    }
    private int flatN(int row, int col) {
        return (n+1)*row+col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        grid[--row][--col] = true;
        openSitesCount++;
        if (row == 0) {
            model.union(flatN(row, col), loId);
            modelAlt.union(flatN(row, col), loId);
        } else if (grid[row-1][col]) {
            model.union(flatN(row, col), flatN(row-1, col));
            modelAlt.union(flatN(row, col), flatN(row-1, col));
        }

        if (row == n) {
            model.union(flatN(row, col), hiId);
        } else if (grid[row+1][col]) {
            model.union(flatN(row, col), flatN(row+1, col));
            modelAlt.union(flatN(row, col), flatN(row+1, col));
        }

        if (col > 0 && grid[row][col-1]) {
            model.union(flatN(row, col), flatN(row, col-1));
            modelAlt.union(flatN(row, col), flatN(row, col-1));
        }
        if (col < n && grid[row][col+1]) {
            model.union(flatN(row, col), flatN(row, col+1));
            modelAlt.union(flatN(row, col), flatN(row, col+1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        inputValidator(--row, --col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        inputValidator(--row, --col);
        return modelAlt.find(flatN(row, col)) == modelAlt.find(loId);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return model.find(loId) == model.find(hiId);
    }

    private void inputValidator(int row, int col) {
        if (!isOnGrid(row, col)) {
            throw new IllegalArgumentException("Input indexes are out of bounds.");
        }
    }

    private boolean isOnGrid(int row, int col) {
        return (row >= 0 && col >= 0 && row <= n && col <= n);
    }
    // test client (optional)
    public static void main(String[] args) {
        // tst
    }
}
