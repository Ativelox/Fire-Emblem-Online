package de.ativelox.feo.server.controller;

import de.ativelox.feo.network.INetworkController;

/**
 * Provides an interface to send data, to properly control the game flow.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IGameControllerSender<POut, PIn> {

    /**
     * Registers a {@link INetworkController} to this instance, which eases stream
     * communication.
     * 
     * @param playerId          The id of the player.
     * @param networkController The network controller, controlling communication
     *                          with this player.
     */
    void register(final int playerId, final INetworkController<POut, PIn> networkController);

    /**
     * Sends data to the player with <tt>playerId</tt> that signalizes that the
     * server has registered this player.
     * 
     * @param playerId
     */
    void sendWelcome(final int playerId);

}
