package structures_and_algorythms.iterators;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import structures_and_algorythms.collections.ArrayPriorityQueue;

/**
 * Итератор возвращающий последовательность из N возрастающих итераторов в порядке возрастания
 * first = 1,3,4,5,7
 * second = 0,2,4,6,8
 * result = 0,1,2,3,4,4,5,6,7,8
 * <p>
 * Time = O(n + k * log n),
 * n — количество итераторов
 * k — суммарное количество элементов
 */
public class MergingPeekingIncreasingIterator implements Iterator<Integer> {

    private Comparator<PeekingIncreasingIterator> comparator = Comparator.comparing(PeekingIncreasingIterator::peek);
    private ArrayPriorityQueue<PeekingIncreasingIterator> queue;

    public MergingPeekingIncreasingIterator(IPeekingIterator... peekingIterator) {
        queue = new ArrayPriorityQueue<>();
        for (IPeekingIterator aPeekingIterator : peekingIterator) {
            queue.add((PeekingIncreasingIterator) aPeekingIterator);
        }
    }

    @Override
    public boolean hasNext() {
        return queue.size() > 0;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        PeekingIncreasingIterator iterator = queue.extractMin();
        Integer nextInteger = iterator.next();
        if (iterator.hasNext()) {
            iterator.peek();
            queue.add(iterator);
        }
        return nextInteger;
    }

    public static void main(String[] args) {
        IPeekingIterator iterator1 = new PeekingIncreasingIterator(1, 10, 10);
        IPeekingIterator iterator2 = new PeekingIncreasingIterator(10, 10, 10);
        IPeekingIterator iterator3 = new PeekingIncreasingIterator(100, 10, 10);
        MergingPeekingIncreasingIterator iterator = new MergingPeekingIncreasingIterator(iterator1, iterator2, iterator3);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
