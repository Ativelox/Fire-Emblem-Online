package de.ativelox.feo.client.model.util;

import java.util.Iterator;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ArrayIterator<T> implements Iterator<T> {

    private int mIndex;
    private T[] mArray;

    public ArrayIterator(T[] array) {
        mIndex = 0;
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public T next() {
        T result = mArray[mIndex];
        mIndex++;
        return result;
    }

}
