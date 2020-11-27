import java.util.Arrays;

public class AuxArray {

    public static void MergeArray(int[] a) {
        int[] aux = new int[a.length / 2];
        for (int i = 0; i < aux.length; i++) {
            aux[i] = a[i];
        }
        int i = 0;
        int j = aux.length;
        for (int k = 0; k < a.length; k++) {
            if (i == aux.length || a[j] <= aux[i]) {
                a[k] = a[j++];
            } else if (j == a.length || a[j] > aux[i]) {
                a[k] = aux[i++];
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {1,2,3, 2,3,4};
        MergeArray(a);
        System.out.println(Arrays.toString(a));
    }
}
