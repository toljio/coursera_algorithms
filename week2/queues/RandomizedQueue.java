/*
A randomized queue is similar to a stack or queue,
except that the item removed is chosen uniformly at random among items in the data structure.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int len;
    // construct an empty randomized queue
    public RandomizedQueue() {
        len = 0;
        items = (Item[]) new Object[1]; // the ugly cast
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return len;
    }

    private void resize(int length) {
        Item[] copy = (Item[]) new Object[length];
        for (int i = 0; i < len; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        if (len == items.length) {
            resize(items.length * 2);
        }
        items[len++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) { throw new java.util.NoSuchElementException(); }
        int i = (int) (Math.random() * (len--));
        Item res = items[i];
        items[i] = items[len];
        items[len] = null;
        if (len > 0 && len == items.length / 4) resize(items.length / 2);

        return res;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) { throw new java.util.NoSuchElementException(); }
        return items[(int) (Math.random() * len)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new ArrayIterator(); }
    private class ArrayIterator implements Iterator<Item> {
        private int current = 0;
        private Item[] randomItems = (Item[]) new Object[len];
        public ArrayIterator() {
            for (int i = 0; i < len; i++) {
                randomItems[i] = items[i];
            }
            StdRandom.shuffle(randomItems);
        }
        public boolean hasNext() { return current != len; }
        public void remove()     { throw new UnsupportedOperationException(); }
        public Item next() {
            if (current == len) { throw new java.util.NoSuchElementException(); }
            return randomItems[current++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(2);
        rq.enqueue(3);
        StdOut.println("Queue has " + rq.size() + ". Random value: " + rq.sample() + "\nAll values:");
        for (Object i : rq) {
            StdOut.println(i);
        }
        while (!rq.isEmpty()) {
            StdOut.print(" remove: " + rq.dequeue());
        }
    }

}