package de.ativelox.feo.network.util;

/**
 * Provides an immutable pair of two (possibly) different types <tt>K</tt> and
 * <tt>V</tt>.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class ImmutablePair<K, V> {

    /**
     * The first value of this pair.
     */
    private final K mKey;

    /**
     * The second value of this pair.
     */
    private final V mValue;

    /**
     * Creates a new {@link ImmutablePair}.
     * 
     * @param key   The first value of this pair.
     * @param value The second value of this pair.
     */
    public ImmutablePair(final K key, final V value) {
	mKey = key;
	mValue = value;

    }

    /**
     * Gets the first value present in this pair.
     * 
     * @return The value mentioned.
     */
    public K getKey() {
	return mKey;

    }

    /**
     * Gets the second value present in this pair.
     * 
     * @return The value mentioned.
     */
    public V getValue() {
	return mValue;

    }
}
