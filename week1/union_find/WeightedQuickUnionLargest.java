public class WeightedQuickUnionLargest {
    private int[] largest;
    private int[] parent;
    private int[] size;

    public WeightedQuickUnionLargest(int n) {
        parent = new int[n];
        size = new int[n];
        largest = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            largest[i] = i;
            size[i] = 1;
        }
    }

    public int root(int n) {
        while (n != parent[n]) {
            n = parent[n];
        }
        return parent[n];
    }

    public void union(int a, int b) {
        int rootA = root(a);
        int rootB = root(b);
        if (rootA == rootB) { return;}
        if (size[rootA] < size[rootB]) {
            parent[a] = rootB;
            size[b] += size[a];
            if (largest[rootA] > largest[rootB]) {
                largest[rootB] = largest[rootA];
            }
        } else {
            parent[b] = rootA;
            size[a] += size[b];
            if (largest[rootA] < largest[rootB]) {
                largest[rootA] = largest[rootB];
            }
        }
    }

    public int find(int n) {
        return largest[root(n)];
    }

    public static void main(String[] args) {
        WeightedQuickUnionLargest w = new WeightedQuickUnionLargest(10);
        System.out.println("Before: " + w.find(1));
        w.union(1, 2);
        w.union(6, 9);
        w.union(1, 9);
        System.out.println("After: " + w.find(1));
    }
}
