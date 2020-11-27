import java.util.Arrays;

public class Inversions {
    public static int InvCount(int[] a, int lo, int hi) {
        if (lo == hi) return 0;
        int mid = (hi + lo) / 2;
        int sum = 0;
        sum += InvCount(a, lo, mid);
        sum += InvCount(a, mid + 1, hi);
        int[] b = new int[a.length];
        for (int k = lo; k <= hi; k++) {
            b[k] = a[k];
        }
        int j = mid + 1;
        int i = lo;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = b[j++];
            } else if (j > hi) {
                a[k] = b[i++];
            } else if (b[i] > b[j]) {
                sum += j - k;
                a[k] = b[j++];
            } else {
                a[k] = b[i++];
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int[] a = {0,1,2,3,4,5,2};
        System.out.println(InvCount(a, 0, a.length - 1));
        System.out.println(Arrays.toString(a));

    }
}
