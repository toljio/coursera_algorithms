/*Search in a bitonic array.
An array is bitonic if it is comprised of an increasing sequence
of integers followed immediately by a decreasing sequence of integers.
Write a program that, given a bitonic array of nn distinct integer values,
determines whether a given integer is in the array.
 */
public class Bitonic {

    private static int peakId(int[] a) {
        int begin = 0;
        int end = a.length - 1;
        while (begin < end) {
            int pivot = begin + (end - begin) / 2;
            if (a[pivot + 1] < a[pivot]) {
                end = pivot;
            } else {
                begin = pivot + 1;
            }
        }
        return begin;
    }

    private static boolean isMember(int[] a, int b) {
// left from peak
        int begin = 0;
        int end = peakId(a);
        while (begin < end) {
            int pivot = begin + (end - begin) / 2;
            if (a[pivot] == b) {
                return true;
            } else if (a[pivot] > b) {
                end = pivot - 1;
            } else {
                begin = pivot + 1;
            }
        }
// right from peak
        begin = peakId(a) + 1;
        end = a.length;
        while (begin < end) {
            int pivot = begin + (end - begin) / 2;
            if (a[pivot] == b) {
                return true;
            } else if (a[pivot] > b) {
                begin = pivot + 1;
            } else {
                end = pivot - 1;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 3, 0};
        System.out.println(isMember(a, 0));
    }
}
