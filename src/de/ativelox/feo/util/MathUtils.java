package de.ativelox.feo.util;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MathUtils {

    private MathUtils() {

    }

    public static int clamp(int maxBound, int value, int minBound) {
        return Math.min(maxBound, Math.max(value, minBound));

    }
}
