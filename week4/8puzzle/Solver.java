import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private boolean solvable;
    private SearchNode lastNode;

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final int manhattan;
        private final SearchNode prev;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.manhattan = this.board.manhattan();
        }
        public int compareTo(SearchNode that) {
            return this.moves + this.manhattan - (that.moves + that.manhattan);
        }
    }
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        SearchNode initSearchNode = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(initSearchNode);
        MinPQ<SearchNode> pq2 = new MinPQ<>();
        pq2.insert(new SearchNode(initial.twin(), 0, null));
        SearchNode minSearchNode = pq.delMin();
        SearchNode minSearchNode2 = pq2.delMin();
        while (!minSearchNode.board.isGoal() && !minSearchNode2.board.isGoal()) {
            for (Board b: minSearchNode.board.neighbors()) {
                if (minSearchNode.prev == null || !b.equals(minSearchNode.prev.board)) {
                    SearchNode cur = new SearchNode(b, minSearchNode.moves + 1, minSearchNode);
                    pq.insert(cur);
                }
            }
            minSearchNode = pq.delMin();
            for (Board b: minSearchNode2.board.neighbors()) {
                if (minSearchNode2.prev == null || !b.equals(minSearchNode2.prev.board)) {
                    SearchNode cur = new SearchNode(b, minSearchNode2.moves + 1, minSearchNode2);
                    pq2.insert(cur);
                }
            }
            minSearchNode2 = pq2.delMin();
        }
        if (minSearchNode.board.isGoal()) {
            solvable = true;
            lastNode = minSearchNode;
        } else {
            solvable = false;
            lastNode = null;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return lastNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        SearchNode cur = lastNode;
        Stack<Board> way = new Stack<>();
        while (cur != null) {
            way.push(cur.board);
            cur = cur.prev;
        }
        return way;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
