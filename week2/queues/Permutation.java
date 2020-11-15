/*
a client program Permutation.java that takes an integer k as a command-line argument;
reads a sequence of strings from standard input using StdIn.readString();
and prints exactly k of them, uniformly at random.
Print each item from the sequence at most once.
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        double count = 0;
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            count++;
            if (rq.size() < k) {
                rq.enqueue(s);
            } else if (StdRandom.uniform() < (double) k / count) {
                rq.dequeue();
                rq.enqueue(s);
            }
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}