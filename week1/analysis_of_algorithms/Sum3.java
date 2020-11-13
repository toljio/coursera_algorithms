/* Question 1
3-SUM in quadratic time. Design an algorithm for the 3-SUM problem that takes time proportional to n^2n
  in the worst case. You may assume that you can sort the nn integers in time proportional to n^2n
  or better.
 */

public class Sum3 {

    private static void sort(int[] a, int begin, int end) {
        if (begin < end) {
            int pivot = partition(a, begin, end);
            sort(a, begin, pivot - 1);
            sort(a, pivot + 1, end);
        }
    }

    private static int partition(int[] a, int begin, int end) {
        int pivot = a[end];
        int i = begin;
        for (int j = begin; j < end; j++) {
            if (a[j] < pivot) {
                i++;
                int tmp = a[j];
                a[j] = a[i - 1];
                a[i - 1] = tmp;
            }
        }
        a[end] = a[i];
        a[i] = pivot;
        return i;
    }

    public static int count(int[] a) {
        sort(a, 0, a.length - 1);
        int res = 0;
        for (int i = 0; i < a.length - 2; i++) {
            if (a[i] > 0) {
                break;
            }
            int j = i + 1;
            int k = a.length - 1;
            while (j < k) {
                if (a[j] + a[k] + a[i] > 0) {
                    k--;
                } else if (a[j] + a[k] + a[i] < 0) {
                    j++;
                } else {
                    res++;
                    k--;
                    j++;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] a = {5, 1, 2, 3, -4, -5, 0, 4};
        System.out.println(count(a));
    }
}
