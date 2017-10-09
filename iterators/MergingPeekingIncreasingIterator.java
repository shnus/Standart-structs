package structures_and_algorythms.iterators;

import java.util.Iterator;

/**
 * Итератор возвращающий последовательность из N возрастающих итераторов в порядке возрастания
 * first = 1,3,4,5,7
 * second = 0,2,4,6,8
 * result = 0,1,2,3,4,4,5,6,7,8
 * <p>
 * Time = O(n + k * log n),
 * n — количество итераторов
 * k — суммарное количество элементов
 */
public class MergingPeekingIncreasingIterator implements Iterator<Integer> {

    private IncreasingIterator[] iterators;
    private IncreasingIterator temp;
    private int min;


    public MergingPeekingIncreasingIterator(IncreasingIterator... peekingIterator) {
        iterators = peekingIterator;
    }

    @Override
    public boolean hasNext() {
        for (IncreasingIterator iterator : iterators) {
            if (iterator.hasNext()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer next() {
        min = Integer.MAX_VALUE;
        for (IncreasingIterator iterator : iterators) {
            if (iterator.hasNext()) {
                if (iterator.curr < min) {
                    min = iterator.curr;
                    temp = iterator;
                }
            }
        }
        temp.curr += temp.random.nextInt(temp.maxGrowth);
        temp.step++;
        return min;
    }
}