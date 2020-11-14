/*
Deque is a generalization of a stack and a queue that supports adding and removing items from either the front or the back of the data structure
Deque implementation must support each deque operation (including construction) in constant worst-case time.
A deque containing n items must use at most 48n + 192 bytes of memory.
Additionally, iterator implementation must support each operation (including construction) in constant worst-case time.
*/

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node pre;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        Node add = new Node();
        add.item = item;
        if (isEmpty()) {
            last = first = add;
        } else {
            add.next = first;
            first.pre = add;
            first = add;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {throw new IllegalArgumentException(); }
        Node add = new Node();
        add.item = item;
        if (isEmpty()) {
            last = first = add;
        } else {
            add.pre = last;
            last.next = add;
            last = add;
        }
        size++;
    }
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) { throw new java.util.NoSuchElementException(); }
        Item res = first.item;
        size--;
        if (isEmpty()) {
            first = last = null;
        } else {
            first = first.next;
            first.pre = null;
        }
        return res;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) { throw new java.util.NoSuchElementException(); }
        Item res = last.item;
        size--;
        if (isEmpty()) {
            first = last = null;
        } else {
            last = last.pre;
            last.next = null;
        }
        return res;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new ListIterator(); }
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext() {  return current != null;  }
        public void remove()     {  throw new UnsupportedOperationException(); }
        public Item next() {
            if (current == null) { throw new java.util.NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque d = new Deque();
        d.addFirst(2);
        d.addFirst(1);
        d.addLast(3);
        d.addLast(4);
        StdOut.println("Size = " + d.size() + ". Before remove:");
        for (Object i : d) {
            StdOut.print(i + " ");
        }
        d.removeFirst();
        d.removeLast();
        StdOut.println("\nAfter remove size = " + d.size());
        for (Object i : d) {
            StdOut.print(i + " ");
        }
    }
}