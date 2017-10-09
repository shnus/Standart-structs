package structures_and_algorythms.collections;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CyclicArrayQueue<Item> implements IQueue<Item> {

    private final Class<?> clazz;
    private int size;
    private int capacity;
    private int front;
    private int back;
    private Item[] elementData;

    public CyclicArrayQueue(Item[] elementData) {
        this.clazz = elementData.getClass().getComponentType();
        this.elementData = elementData;
        size = elementData.length;
        capacity = elementData.length;
        front = 0;
        back = elementData.length - 1;
    }

    @SuppressWarnings("unchecked")
    public CyclicArrayQueue(Class<?> clazz) {
        this.clazz = clazz;
        elementData = (Item[]) Array.newInstance(this.clazz, 10);
        size = 0;
        capacity = elementData.length;
        front = 0;
        back = 0;
    }

    @Override
    public void enqueue(Item item) {
        if (size < capacity) {
            if (back == capacity - 1 && elementData[back] != null) {
                back = 0;
            }
            if (front <= back) {
                while (front <= back) {
                    if (elementData[back] == null) {
                        break;
                    }
                    ++back;
                }
                elementData[back] = item;
            } else {
                while (front > back) {
                    if (elementData[back] == null) {
                        break;
                    }
                    ++back;
                }
                elementData[back] = item;
            }
            size++;
        } else {
            grow();
            enqueue(item);
        }
    }

    @Override
    public Item dequeue() {
        if (size > 0) {
            Item item = elementData[front];
            elementData[front] = null;
            size--;
            front = front == capacity - 1 ? 0 : ++front;
            shrink();
            return item;
        } else {
            return null;
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

    private void grow() {
        if (size == capacity) {
            Item[] newArray = (Item[]) Array.newInstance(clazz, capacity / 2 * 3 + 1);
            if (front == 0) {
                System.arraycopy(elementData, front, newArray, front, size);
            } else {
                System.arraycopy(elementData, front, newArray, 0, capacity - front);
                System.arraycopy(elementData, 0, newArray, capacity - front - 1, front);
            }
            elementData = newArray;
            capacity = elementData.length;
            front = 0;
            back = size - 1;
        }
    }

    private void shrink() {
        if (4 * size <= capacity && size > 1) {
            Item[] newArray = (Item[]) Array.newInstance(clazz, capacity / 2);
            if (front <= back) {
                System.arraycopy(elementData, front, newArray, 0, back + 1 - front);
            } else {
                System.arraycopy(elementData, front, newArray, 0, capacity - front);
                System.arraycopy(elementData, 0, newArray, capacity - front - 1, front);
            }
            elementData = newArray;
            capacity = elementData.length;
            front = 0;
            back = size - 1;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            @Override
            public boolean hasNext() {
                return size > 0;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return dequeue();
            }
        };
    }
}