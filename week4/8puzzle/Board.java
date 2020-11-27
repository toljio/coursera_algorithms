import java.util.Arrays;
import java.util.Iterator;

public final class Board {
    private final int[][] board;
    private int n;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        int n1 = tiles.length;
        if (n1 < 2 || n1 >= 128) throw new IllegalArgumentException("2 ≤ n < 128");
        int n2 = tiles[0].length;
        if (n2 != n1) throw new IllegalArgumentException("input matrix should be squire");
//        int check = 0;
//        for (int i =0; i < n1; i++) {
//            for (int j=0; j < n2; j++) {
//                check |= 1<<tiles[i][j];
//            }
//        }
//        System.out.println(Math.pow(2, 127*127));
//        if (check != Math.pow(2, n1*n1) - 1) throw new IllegalArgumentException();
        board = tiles.clone();
        n = n1;
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(n);
        builder.append("\n");
        for (int i = 0; i < n; i++) {
            StringBuilder builder2 = new StringBuilder();
            for (int v : board[i]) {
                builder2.append(Integer.toString(v));
            }
            builder.append(builder2);
            if (i != n - 1) builder.append("\n");
        }
        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) continue;
                if (board[i][j] != n * i + j + 1) {
                    k++;
                }
            }
        }
        return k;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) continue;
                int row_diff = Math.abs(i - (board[i][j] - 1) / n);
                int col_diff = Math.abs(j - (board[i][j] - 1) % n);
                k += row_diff + col_diff;
            }
        }
        return k;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return toString().equals(y.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {

            @Override
            public Iterator<Board> iterator() { return new BoardIterator(); }
            class BoardIterator implements Iterator<Board> {
                private int row0;
                private int col0;
                private boolean leftSeen = false;
                private boolean rightSeen = false;
                private boolean topSeen = false;
                private boolean botSeen = false;
                public BoardIterator() {
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            if (board[i][j] == 0) {
                                row0 = i;
                                col0 = j;
                                break;
                            }
                        }
                    }
                    if (row0 == 0) {
                        topSeen = true;
                    }
                    if (row0 == n-1) {
                        botSeen = true;
                    }
                    if (col0 == 0) {
                        leftSeen = true;
                    }
                    if (col0 == n-1) {
                        rightSeen = true;
                    }
                }

                @Override

                public boolean hasNext() {
                    return !(topSeen && botSeen && leftSeen && rightSeen);
                }

                @Override
                public Board next() {
                    int[][] bro = new int[n][];
                    for (int i = 0; i < n; i++) {
                        bro[i] = board[i].clone();
                    }
                    int row0New = row0;
                    int col0New = col0;
                    if (!topSeen) {
                        row0New -= 1;
                        topSeen = true;
                    } else if (!botSeen) {
                        row0New += 1;
                        botSeen = true;
                    } else if (!leftSeen) {
                        col0New -= 1;
                        leftSeen = true;
                    } else if (!rightSeen) {
                        col0New += 1;
                        rightSeen = true;
                    }
                    bro[row0][col0] = bro[row0New][col0New];
                    bro[row0New][col0New] = 0;
                    return new Board(bro);
                }
            }
        };
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return neighbors().iterator().next();
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] a = {{8,1,3},{4,0,2},{7,6,5}};
        int[][] b = {{8,1,3},{4,0,2},{7,5,6}};
        Board test = new Board(a);
        Board test2 = new Board(b);
        System.out.println(test.toString());
        System.out.println(test.hamming());
        System.out.println(test.manhattan());
        System.out.println(test.isGoal());
        System.out.println(test.dimension());
        System.out.println(test.equals(test2));
//        test2 = test.twin();
//        System.out.println(test2.toString());
        System.out.println(test.toString());

        for (Board brd : test.neighbors()) {
            System.out.println(brd.toString());
        }
        System.out.println(test.toString());

    }

}