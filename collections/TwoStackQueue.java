package com.company.nusrat.structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.company.nusrat.structures.interfaces.IQueue;
import com.company.nusrat.structures.interfaces.IStack;

public class TwoStackQueue<Item> implements IQueue<Item> {

    private IStack<Item> stack1;
    private IStack<Item> stack2;

    public TwoStackQueue() {
        stack1 = new ArrayStack<Item>();
        stack2 = new ArrayStack<Item>();
    }

    @Override
    public void enqueue(Item item) {
        stack1.push(item);
    }

    @Override
    public Item dequeue() {
        if(!stack2.isEmpty()){
            return stack2.pop();
        } else if(!stack1.isEmpty()){
            reverse(stack1, stack2);
            return stack2.pop();
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public int size() {
        return stack1.size()+stack2.size();
    }

    @Override
    public Iterator<Item> iterator() {
        return new StackItr();
    }

    /* TODO: need to think. Current iterator is no safe */
    private class StackItr implements Iterator<Item> {

        int cursor1 = stack1.size();
        int cursor2 = stack2.size();
        boolean reversed = false;

        @Override
        public boolean hasNext() {
            return cursor1+cursor2==0;
        }

        @Override
        public Item next() {
            if(hasNext()){
                if(cursor1!=0) {
                    cursor1--;
                    return null;//peek?
                }
                if(!reversed) reverse(stack1, stack2);
                cursor2--;
                return null;//peek?
            } else
                throw new NoSuchElementException();
        }
    }

    private void reverse(IStack<Item> stack1, IStack<Item> stack2) {
        while (!stack1.isEmpty()) {
            Item nextItem = stack1.pop();
            stack2.push(nextItem);
        }
    }

}
