package de.ativelox.feo.network;

import java.io.InputStream;
import java.io.OutputStream;

import de.ativelox.feo.network.property.EIO;

/**
 * Provides an interface to ease {@link InputStream Input-} and
 * {@link OutputStream} communication.
 * 
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface INetworkController<POut, PIn> extends Runnable {

    /**
     * Starts to ignore the given {@link EIO} action.
     * 
     * @param ioType The type of action to ignore.
     */
    void ignore(final EIO ioType);

    /**
     * Writes the protocol to its {@link OutputStream}.
     * 
     * @param protocol The protocol to send.
     * 
     * @see INetworkController#send(Object, String[])
     */
    void send(final POut protocol);

    /**
     * Writes the given message defined by the protocol and its additional arguments
     * to its {@link OutputStream}.
     * 
     * @param protocol   The protocol to send.
     * @param additional Possible additional arguments.
     */
    void send(final POut protocol, final String[] additional);

    /**
     * Gets called when this instance has read messages from its
     * {@link InputStream}.
     * 
     * @param protocol   The protocol that got sent to this instance.
     * @param additional Additional arguments that might be relevant for the given
     *                   protocol.
     */
    void serve(final PIn protocol, final String[] additional);

    /**
     * Stops this instance from reading and writing its streams.
     */
    void stop();

    /**
     * Starts to unignore the given {@link EIO} action.
     * 
     * @param ioType The type of action to unignore.
     */
    void unignore(final EIO ioType);

}
