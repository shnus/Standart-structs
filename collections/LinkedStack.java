package structures_and_algorythms.collections;

import java.util.Iterator;

public class LinkedStack<Item> implements IStack<Item> {

    private Node<Item> head;
    private int size;

    @Override
    public void push(Item item) {
        head = new Node<>(item, head);
        size++;
    }

    @Override
    public Item pop() {
        if (head == null) {
            return null;
        } else {
            Node<Item> popItem = head;
            head = popItem.next;
            size--;
            return popItem.item;
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
        return new LinkedStackIterator();
    }

    private class LinkedStackIterator implements Iterator<Item> {

        @Override
        public boolean hasNext() {
            return head != null;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                Node<Item> nextItem = head;
                head = head.next;
                return nextItem.item;
            }
            return null;
        }

    }

    private static class Node<Item> {
        Item item;
        Node<Item> next;

        public Node(Item item, Node<Item> next) {
            this.item = item;
            this.next = next;
        }
    }
}