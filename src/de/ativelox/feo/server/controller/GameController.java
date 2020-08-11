package de.ativelox.feo.server.controller;

import java.util.HashMap;
import java.util.Map;

import de.ativelox.feo.network.INetworkController;
import de.ativelox.feo.network.protocol.EC2S;
import de.ativelox.feo.network.protocol.ES2C;

/**
 * Provides a basic implementation of an {@link IGameController}. This
 * implementation doesn't care about <i>packet loss</i>, resulting in a
 * completely out of synch experience on the server and client sides if
 * happening, also doesn't care about encryption, so all network traffic is
 * <b>plain text</b> and doesn't care about checking if clients are doing
 * inappropriate stuff, e.g. a client could easily send {@link EC2S#VICTORY} and
 * would instantly win, without checking if it was actually <i>valid</i>. On the
 * other hand this provides an easy to understand and simple
 * {@link IGameController}, which could easily be extended to adhere to such
 * standards.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class GameController implements IGameController<ES2C, EC2S> {

    /**
     * Provides a mapping from each player id to its associated
     * {@link INetworkController}.
     */
    private final Map<Integer, INetworkController<ES2C, EC2S>> mPIdToNetwork;

    /**
     * The amount of players this instance manages and are currently participating
     * in the game.
     */
    private final int mPlayerAmount;

    /**
     * The amount of players ready to play.
     */
    private int mReadyPlayers;

    /**
     * Whether the game is running or not.
     */
    private boolean mGameOngoing;

    /**
     * The id of the player that is currently on his turn.
     */
    private int mCurrentTurnPlayerId;

    /**
     * Creates a new {@link GameController}.
     * 
     * @param playerAmount The amount of players this instance manages.
     */
    public GameController(final int playerAmount) {
        mPIdToNetwork = new HashMap<>();

        mPlayerAmount = playerAmount;
        mReadyPlayers = 0;
        mGameOngoing = false;
        mCurrentTurnPlayerId = 0;

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.server.controller.IGameControllerSender#register(int,
     * de.ativelox.rummyz.model.INetworkController)
     */
    @Override
    public void register(final int playerId, final INetworkController<ES2C, EC2S> networkController) {
        this.mPIdToNetwork.put(playerId, networkController);

    }

    /**
     * Sends the given message to all players except the one with
     * <tt>excludePlayerId</tt>;
     * 
     * @param protocol        The protocol to send.
     * @param excludePlayerId The player id <b>not</b> to send this to.
     * 
     * @see GameController#sendExcluding(ES2C, String[], int)
     */
    private void sendExcluding(final ES2C protocol, final int excludePlayerId) {
        this.sendExcluding(protocol, null, excludePlayerId);
    }

    /**
     * Sends the given message to all players, except the one with
     * <tt>excludePlayerId</tt>.
     * 
     * @param protocol        The protocol to send.
     * @param args            The additional arguments.
     * @param excludePlayerId The player id <b>not</b> to send this to.
     */
    private void sendExcluding(final ES2C protocol, final String[] args, final int excludePlayerId) {

        for (int i = 0; i < mPlayerAmount - 1; i++) {
            int playerIdToSend = ((excludePlayerId + i) % mPlayerAmount) + 1;
            this.mPIdToNetwork.get(playerIdToSend).send(protocol, args);

        }
    }

    /**
     * Sends the given message to all players.
     * 
     * @param protocol The protocol to send.
     * @param args     The additional arguments.
     */
    private void sendToAll(final ES2C protocol, final String[] args) {
        for (int i = 1; i <= mPlayerAmount; i++) {
            mPIdToNetwork.get(i).send(protocol, args);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.feo.server.controller.IGameControllerSender#sendWelcome(int)
     */
    @Override
    public void sendWelcome(final int playerId) {
        this.mPIdToNetwork.get(playerId).send(ES2C.WELCOME, new String[] { playerId + "" });

    }

    @Override
    public void onAttackReceived(int playerId, String[] args) {
        this.sendExcluding(ES2C.ATTACK, args, playerId);

    }
}
