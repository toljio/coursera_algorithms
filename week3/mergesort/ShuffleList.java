import edu.princeton.cs.algs4.StdRandom;

public class ShuffleList {
    Node first = null;
    public class Node {
        int value;
        Node next;
    }

    public void push(int v) {
        Node old = first;
        first = new Node();
        first.value = v;
        first.next = old;
    }
    public void print() {
        Node cur = first;
        while (cur != null) {
            System.out.println(cur.value);
            cur = cur.next;
        }
    }

    private Node getMiddle(Node start) {
        Node fast = start;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            start = start.next;
        }
        return start;
    }

    public void shuffle() {
        shuffle(first);
    }

    public void shuffle(Node i) {
        if (i == null || i.next == null) return;
        Node mid = getMiddle(i);
        Node j = mid.next;
        mid.next = null;
        shuffle(i);
        shuffle(j);
        first = merge(i, j);
    }

    private Node merge(Node i, Node j) {
        Node left = i;
        Node right = j;
        if (StdRandom.uniform(2) > 0) {
            i = j;
            right = right.next;
        } else {
            left = left.next;
        }
        while (right != null || left != null) {
            if (left == null) {
                i.next = right;
                right =right.next;
            }
            else if (right == null) {
                i.next = left;
                left = left.next;
            }
            else if (StdRandom.uniform(2) > 0) {
                i.next = right;
                right = right.next;
            }
            else {
                i.next = left;
                left = left.next;
            }
            i = i.next;
        }
        if (right != null) return right;
        return left;
    }

    public static void main(String[] args) {
        ShuffleList l = new ShuffleList();
        l.push(1);
        l.push(2);
        l.push(3);
        l.push(4);
        l.shuffle();
        l.print();
    }
}
