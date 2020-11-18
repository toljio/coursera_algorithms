public class QueueTwoStacks {
    private Stack stack1;
    private Stack stack2;
    private static class Stack {
        Node first = null;
        public int size = 0;
        private class Node {
            int value;
            Node next;
            public Node(int value, Node next) {
                this.value = value;
                this.next = next;
            }
        }
        public void push(int v) {
            first = new Node(v, first);
            size++;
        }
        public int pop() {
            Node res = first;
            first = first.next;
            size--;
            return res.value;
        }
    }

    public QueueTwoStacks() {
        this.stack1 = new Stack();
        this.stack2 = new Stack();
    }

    public void enqueue(int v) {
        stack1.push(v);
        if (stack1.size > stack2.size) {
            rebalance();
        }
    }

    public int dequeue() {
        if (stack1.size > stack2.size) {
            rebalance();
        }
        return stack2.pop();
    }
    private void rebalance() {
        int[] tmp = new int[stack2.size];
        for (int i = 0; stack2.size > 0; i++) {
            tmp[i] = stack2.pop();
        }
        while (stack1.size > 0) {
            stack2.push(stack1.pop());
        }
        for (int i : tmp) {
            stack2.push(i);
        }
    }

    public static void main(String[] args) {
        QueueTwoStacks q = new QueueTwoStacks();
        q.enqueue(254);
        q.enqueue(391);
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        q.enqueue(765);
        System.out.println(q.dequeue());
    }
}
