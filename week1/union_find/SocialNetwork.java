import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class SocialNetwork {
    public static void main(String[] args) {
        Stopwatch time = new Stopwatch();
        int n = 10000000;
        WeightedQuickUnionUF w = new WeightedQuickUnionUF(n);
        while (w.count() != 1) {
            w.union( (int)(Math.random() * n), (int)(Math.random() * n));
        }
        System.out.println("Finished at " + time.elapsedTime());
    }
}
