package com.company.nusrat.structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.company.nusrat.structures.interfaces.IDeque;

public class LinkedDeque<Item> implements IDeque<Item> {
    int size;
    transient Node<Item> head;
    transient Node<Item> tail;

    @Override
    public void pushFront(Item item) {
        Node oldHead = head;
        Node newNode = new Node(null, item, oldHead);
        head = newNode;
        if(oldHead==null){
            tail = newNode;
        } else {
            oldHead.prev = newNode;
        }
        size++;
    }

    @Override
    public void pushBack(Item item) {
        Node oldTail = head;
        Node newNode = new Node(oldTail, item, null);
        if(oldTail==null){
            head = newNode;
        } else {
            oldTail.next = newNode;
        }

        size++;
    }

    @Override
    public Item popFront() {
        if(head == null) {
            throw new NoSuchElementException();
        } else {
            Item popElement = (Item) head.item;
            head = head.next;
            if(head!=null){
                head.prev = null;
            } else {
                tail = null;
            }
            size--;
            return popElement;
        }
    }

    @Override
    public Item popBack() {
        if(tail == null) {
            throw new NoSuchElementException();
        } else {
            Item popElement = (Item) tail.item;
            tail = tail.prev;
            if(tail!=null){
                tail.next = null;
            } else {
                head = null;
            }
            size--;
            return popElement;
        }
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new deqIterator();
    }

    private class deqIterator implements Iterator<Item> {
        Node curNode;
        int nextIndex;

        deqIterator(){
            curNode = head;
        }

        @Override
        public boolean hasNext() {
            return nextIndex<size;
        }

        @Override
        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            } else {
                Item returnItem = (Item) curNode.item;
                curNode = curNode.next;
                nextIndex++;
                return returnItem;
            }
        }
    }

    private static class Node<E> {
        E item;
        Node<E> next;

        Node<E> prev;
        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

    }
}
