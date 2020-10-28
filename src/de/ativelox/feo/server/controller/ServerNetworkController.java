package de.ativelox.feo.server.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringJoiner;

import de.ativelox.feo.network.ANetworkController;
import de.ativelox.feo.network.INetworkController;
import de.ativelox.feo.network.exception.UnsupportedProtocolException;
import de.ativelox.feo.network.protocol.EC2S;
import de.ativelox.feo.network.protocol.ES2C;

/**
 * Provides a {@link INetworkController} for the server, which sends the
 * protocol specified by {@link ES2C} to its given {@link OutputStream} and
 * reads the protocol specified by {@link EC2S} from its given
 * {@link InputStream}. This implementation does <b>not</b> care about
 * encryption.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see INetworkController
 * @see ANetworkController
 *
 */
public final class ServerNetworkController extends ANetworkController<ES2C, EC2S> {

    /**
     * The {@link IGameControllerReceiver} for this {@link INetworkController}
     * designed to receive messages read from this instances underlying
     * {@link InputStream}.
     */
    private final IGameControllerReceiver mGc;

    /**
     * The id of the player this instance is managing.
     */
    private final int mPlayerId;

    /**
     * Creates a new {@link ServerNetworkController}.
     * 
     * @param gc       The controller used to manage the game flow.
     * @param playerId The id of the player this instance manages.
     * @param is       The input stream this instance reads from.
     * @param os       The output stream this instance writes to.
     */
    public ServerNetworkController(final IGameControllerReceiver gc, final int playerId, final InputStream is,
	    final OutputStream os) {
	super(is, os, EC2S.class);

	mPlayerId = playerId;
	mGc = gc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.ANetworkController#send(java.lang.Enum,
     * java.lang.String[])
     */
    @Override
    public void send(final ES2C protocol, final String[] additional) {
	super.send(protocol, additional);

	System.out.println("Sending " + protocol.toString() + " to player " + mPlayerId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.INetworkController#serve(java.lang.String)
     */
    @Override
    public void serve(final EC2S protocol, final String[] op) {
	System.out.println("Received " + protocol.toString() + " from player " + mPlayerId + "with arguments: "
		+ prettyPrintedArray(op));

	switch (protocol) {
	case ATTACK:
	    mGc.onAttackReceived(mPlayerId, op);
	    break;
	case END_TURN:
	    mGc.onEndTurnReceived(mPlayerId);
	    break;
	case WAIT:
	    mGc.onWaitReceived(mPlayerId, op);
	    break;

	default:
	    throw new UnsupportedProtocolException(
		    "The protocol named " + EC2S.class.getName() + "." + protocol.toString() + " isn't supported.");

	}
    }

    private static String prettyPrintedArray(String[] array) {
	if (array == null) {
	    return "";
	}

	StringJoiner sj = new StringJoiner(", ", "[", "]");

	int i = 0;
	for (final String s : array) {
	    sj.add(i + ": \"" + s + "\"");

	    i++;
	}
	return sj.toString();
    }

}
