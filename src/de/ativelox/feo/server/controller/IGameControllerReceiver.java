package de.ativelox.feo.server.controller;

/**
 * Provides an interface to receive data to control the game flow.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IGameControllerReceiver {

    void onAttackReceived(final int playerId, String[] args);

}
