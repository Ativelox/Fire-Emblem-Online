package de.ativelox.feo.util;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Pair<U, V> {

    private final U mFirst;
    private final V mSecond;

    private Pair(final U first, final V second) {
        mFirst = first;
        mSecond = second;

    }

    public U getFirst() {
        return mFirst;
    }

    public V getSecond() {
        return mSecond;
    }

    public static <U, V> Pair<U, V> of(U first, V second) {
        return new Pair<>(first, second);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mFirst == null) ? 0 : mFirst.hashCode());
        result = prime * result + ((mSecond == null) ? 0 : mSecond.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Pair<U, V> other = (Pair<U, V>) obj;
        if (mFirst == null) {
            if (other.mFirst != null)
                return false;
        } else if (!mFirst.equals(other.mFirst))
            return false;
        if (mSecond == null) {
            if (other.mSecond != null)
                return false;
        } else if (!mSecond.equals(other.mSecond))
            return false;
        return true;
    }

}
