package de.ativelox.feo.network.exception;

/**
 * An exception to be thrown when the read protocol wasn't supported.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class UnsupportedProtocolException extends RuntimeException {

    /**
     * The serial version UID used for serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * Creates a new {@link UnsupportedProtocolException}.
     */
    public UnsupportedProtocolException() {
        this("");

    }

    /**
     * Creates a new {@link UnsupportedProtocolException}.
     * 
     * @param message The message to be displayed alongside this exception.
     */
    public UnsupportedProtocolException(final String message) {
        super(message);

    }
}
