package structures_and_algorythms.collections;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayPriorityQueue<Key extends Comparable<Key>> implements IPriorityQueue<Key> {

    private Key[] elementData;
    private Comparator<Key> comparator;
    private int size;
    private int capacity;
    private Class<?> clazz;

    @SuppressWarnings("unchecked")
    public ArrayPriorityQueue(Class<?> clazz) {
        this.clazz = clazz;
        elementData = (Key[]) Array.newInstance(this.clazz, 10);
        capacity = elementData.length;
        size = 0;
        comparator = Comparator.naturalOrder();
    }

    public ArrayPriorityQueue(Key[] elementData) {
        this.clazz = elementData.getClass().getComponentType();
        this.elementData = elementData;
        capacity = elementData.length;
        size = elementData.length;
        comparator = Comparator.naturalOrder();
        for (int i = size - 1; i > -1; i--) {
            int index = 2 * i + 1;
            if (index < size) {
                siftDown(i);
            }
        }

    }

    @SuppressWarnings("unchecked")
    public ArrayPriorityQueue(Comparator<Key> comparator, Key[] elementData) {
        this(elementData);
        this.comparator = comparator;
    }

    @Override
    public void add(Key key) {
        if (size < capacity) {
            elementData[size] = key;
            siftUp(size);
            size++;
        } else {
            grow();
            add(key);
        }
    }

    @Override
    public Key peek() {
        return elementData[0];
    }

    @Override
    public Key extractMin() {
        Key min = elementData[0];
        elementData[0] = elementData[size - 1];
        elementData[size - 1] = null;
        size--;
        siftDown(0);
        shrink();
        return min;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void swap(int first, int second) {
        Key temp = elementData[first];
        elementData[first] = elementData[second];
        elementData[second] = temp;
    }

    private void siftUp(int index) {
        if (index > 0) {
            int fatherIndex = index % 2 == 0 ? (index - 2) / 2 : (index - 1) / 2;
            if (fatherIndex < size && greater(fatherIndex, index)) {
                swap(fatherIndex, index);
                siftUp(fatherIndex);
            }
        }
    }

    private void siftDown(int index) {
        if (index > -1) {
            int child1 = index * 2 + 1;
            int child2 = index * 2 + 2;
            if (child1 < size && greater(index, child1)) {
                if (child2 < size && greater(index, child2)) {
                    int min = greater(child1, child2) ? child2 : child1;
                    swap(index, min);
                    siftDown(min);
                } else {
                    swap(index, child1);
                    siftDown(child1);
                }
            } else if (child2 < size && greater(index, child2)) {
                swap(index, child2);
                siftDown(child2);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void grow() {
        if (size == capacity) {
            Key[] newArray = (Key[]) Array.newInstance(clazz, capacity / 2 * 3 + 1);
            System.arraycopy(elementData, 0, newArray, 0, size);
            elementData = newArray;
            capacity = elementData.length;
        }
    }

    @SuppressWarnings("unchecked")
    private void shrink() {
        if (4 * size <= capacity) {
            Key[] newArray = (Key[]) Array.newInstance(clazz, capacity / 2);
            System.arraycopy(elementData, 0, newArray, 0, size);
            elementData = newArray;
            capacity = elementData.length;
        }
    }

    private boolean greater(int i, int j) {
        return comparator == null
                ? elementData[i].compareTo(elementData[j]) > 0
                : comparator.compare(elementData[i], elementData[j]) > 0
                ;
    }

    @Override
    public Iterator<Key> iterator() {
        return new Iterator<Key>() {
            @Override
            public boolean hasNext() {
                return size > 0;
            }

            @Override
            public Key next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return extractMin();
            }
        };
    }
}