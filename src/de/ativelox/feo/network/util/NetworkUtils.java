package de.ativelox.feo.network.util;

/**
 * Provides utility function for encoding and decoding of different data used
 * across this project.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class NetworkUtils {

    /**
     * Tries to convert <tt>toConvert</tt> to an enumeration of the given class
     * <tt>c</tt>. Assumes that <tt>toConvert</tt> has been formerly created like
     * <tt>toConvert = c.ENUM.ordinal() + ""</tt>, otherwise the behavior of this
     * method is not defined.
     * 
     * 
     * @param c         The class of the enumeration to convert the given string to.
     * @param toConvert The string to try to convert to an enumeration of the class
     *                  c.
     * @return An enumeration of <tt>c</tt> represented by <tt>toConvert</tt>.
     * @throws IllegalArgumentException If the given string could not be converted
     *                                  to an enumeration of <tt>c</tt>.
     */
    public static <C extends Enum<C>> C ensureEnumConversion(final Class<C> c, final String toConvert) {
        for (final C enumField : c.getEnumConstants()) {
            if (toConvert.equals(enumField.ordinal() + "")) {
                return enumField;

            }
        }
        throw new IllegalArgumentException("Could not convert " + toConvert + " to an enum from class " + c);

    }

    private NetworkUtils() {

    }
}
