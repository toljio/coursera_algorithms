/*
Successor with delete. Given a set of nn integers S={0,1,...,n−1} and a sequence of requests of the following form:
    * Remove x from S
    * Find the successor of x: the smallest y in S such that y≥x.
design a data type so that all operations (except construction) take logarithmic time or better in the worst case.*/

public class Successor {
    public int maxInd;
    private boolean[] free;
    private WeightedQuickUnionLargest lst;
    public Successor(int n) {
        maxInd = n - 1;
        lst = new WeightedQuickUnionLargest(n);
        free = new boolean[n];
    }

    public void remove(int x) {
        free[x] = true;
        if (x < maxInd) lst.union(x, x+1);
        if (x > 0 && free[x-1]) lst.union(x, x-1);
    }

    public int find(int x) {
        if (!free[x]) return x;
        int s = lst.find(x);
        return s==maxInd && free[maxInd] ? -1 : s;
    }

    public static void main(String[] args) {
        int n = 10;
        Successor s = new Successor(n);
        for (int i = 1; i < n; i++) {
            System.out.print("№" + i + " before: " + s.find(i));
            s.remove(i);
            System.out.println(", after: " + s.find(i));
        }
    }
}
