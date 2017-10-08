package com.company.nusrat.structures;

import java.util.Arrays;
import java.util.Iterator;

import com.company.nusrat.structures.interfaces.IStack;

public class ArrayStack<Item> implements IStack<Item> {

    private static final int DEFAULT_CAPACITY = 10;

    private Item[] elementData;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.elementData = (Item[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void push(Item item) {
        if(size==elementData.length) grow();
        elementData[size] = item;
        size++;
    }

    @Override
    public Item pop() {
        if(size>0) {
            Item returnItem = elementData[size-1];
            size--;
            if(size>DEFAULT_CAPACITY && size < elementData.length/4)
                shrink();
            return returnItem;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void grow() {
        int newCapacity = elementData.length*3/2;
        elementData = Arrays.copyOf(elementData, size);
    }

    private void shrink() {
        int newCapacity = elementData.length/4;
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private void changeCapacity(int newCapacity) {
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayStackIterator();
    }

    private class ArrayStackIterator implements Iterator<Item> {

        private int currentPosition = size;

        @Override
        public boolean hasNext() {
            return currentPosition != 0;
        }

        @Override
        public Item next() {
            return elementData[--currentPosition];
        }

    }

}
