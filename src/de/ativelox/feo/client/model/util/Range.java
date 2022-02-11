/**
 * 
 */
package de.ativelox.feo.client.model.util;

import java.util.Optional;

/**
 * @author Julian Tischner <juliantischner97@gmail.com>
 *
 */
public class Range<T extends Comparable<T>> {

    private final T mMin;

    private final T mMax;

    private Range(final T min, final T max) {
	mMin = min;
	mMax = max;
    }

    public static <T extends Comparable<T>> Range<T> of(final T min, final T max) {
	return new Range<>(min, max);
    }

    public static <T extends Comparable<T>> Range<T> ofSingular(final T value) {
	return new Range<>(value, value);
    }

    public T getMin() {
	return mMin;
    }

    public T getMax() {
	return mMax;
    }

    public boolean contains(final T value) {
	return value.compareTo(mMin) >= 0 && value.compareTo(mMax) <= 0;
    }

    public Optional<Range<T>> intersect(Range<T> other) {
	T min = null;
	T max = null;

	if (this.getMax().compareTo(other.getMin()) < 0 || this.getMin().compareTo(other.getMax()) > 0) {
	    return Optional.empty();
	}

	min = other.getMin();
	max = this.getMax();

	if (this.contains(other.getMin())) {
	    min = this.getMin();

	}
	if (this.contains(other.getMax())) {
	    max = other.getMax();
	}
	return Optional.of(Range.of(min, max));

    }

    public static <T extends Comparable<T>> Optional<Range<T>> intersection(Range<T> first, Range<T> second) {
	return first.intersect(second);

    }
}
