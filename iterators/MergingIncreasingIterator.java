package structures_and_algorythms.iterators;

import java.util.Iterator;

/**
 * Итератор возвращающий последовательность из двух возрастающих итераторов в порядке возрастания
 * first = 1,3,4,5,7
 * second = 0,2,4,6,8
 * result = 0,1,2,3,4,4,5,6,7,8
 * <p>
 * Time = O(k),
 * k — суммарное количество элементов
 */
public class MergingIncreasingIterator implements Iterator<Integer> {

    private IncreasingIterator first;
    private IncreasingIterator second;

    public MergingIncreasingIterator(IncreasingIterator first, IncreasingIterator second) {
        this.first = first;
        this.second = second;
        /* TODO: implement it */
    }

    @Override
    public boolean hasNext() {
        /* TODO: implement it */
        return first.hasNext() || second.hasNext();
    }

    @Override
    public Integer next() {
        /* TODO: implement it */
        int firstNumber = first.curr;
        int secondNumber = second.curr;

        if (first.hasNext() && second.hasNext()) {
            if (firstNumber < secondNumber) {
                first.curr += first.random.nextInt(first.maxGrowth);
                first.step++;
                return firstNumber;
            } else {
                second.curr += second.random.nextInt(second.maxGrowth);
                second.step++;
                return secondNumber;
            }
        } else if (first.hasNext()) {
            first.curr += first.random.nextInt(first.maxGrowth);
            first.step++;
            return firstNumber;
        } else {
            second.curr += second.random.nextInt(second.maxGrowth);
            second.step++;
            return secondNumber;
        }
    }
}