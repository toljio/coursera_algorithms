public class StackWithMax {
    private Node first = null;
    private final class Node {
        Node next;
        int value;
        int max;
        public Node(Node next, int value, int max) {
            this.next = next;
            this.value = value;
            this.max = max;
        }
    }
    public void push(int n) {
        Node oldnode = first;
        int max = n;
        if (first!=null && first.value > n) {
            max = first.value;
        }
        first = new Node(oldnode, n, max);
    }
    public int pop() {
        Node oldFirst = first;
        first = first.next;
        return oldFirst.value;
    }
    public int max() {
        return first.max;
    }
    public static void main(String[] args) {
        StackWithMax test = new StackWithMax();
        test.push(3);
        System.out.println(test.max());
        test.push(1);
        System.out.println(test.max());
    }
}
