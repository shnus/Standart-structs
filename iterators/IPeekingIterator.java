package structures_and_algorythms.iterators;

import java.util.Iterator;

/**
 * Created by Nechaev Mikhail
 * Since 18/10/16.
 */
public interface IPeekingIterator<E> extends Iterator<E> {

    E peek();
}
