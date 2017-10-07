package structures_and_algorythms.collections;

import java.util.Iterator;

public class LinkedQueue<Item> implements IQueue<Item> {

    // -> [tail -> .. -> .. -> head] ->
    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    @Override
    public void enqueue(Item item) {
        Node<Item> newItem = new Node<>(item, null);
        if (tail == null) {
            head = newItem;
            tail = newItem;
        } else {
            head.next = newItem;
            head = newItem;
        }
        size++;
    }

    @Override
    public Item dequeue() {
        if (tail == null) {
            return null;
        } else {
            size--;
            Node<Item> deleteItem = tail;
            tail = tail.next;
            return deleteItem.item;
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedQueueIterator();
    }

    private class LinkedQueueIterator implements Iterator<Item> {

        @Override
        public boolean hasNext() {
            return tail != null;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                Node<Item> nextItem = tail;
                tail = tail.next;
                return nextItem.item;
            } else {
                return null;
            }
        }

    }

    private static class Node<Item> {
        Item item;
        Node<Item> next;

        public Node(Item item) {
            this.item = item;
        }

        public Node(Item item, Node<Item> next) {
            this.item = item;
            this.next = next;
        }
    }

}