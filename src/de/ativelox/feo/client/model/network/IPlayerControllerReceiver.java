package de.ativelox.feo.client.model.network;

import java.util.List;

import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.util.Pair;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IPlayerControllerReceiver {

    /**
     * Signifies that this players turn has ended.
     */
    void onTurnEndReceived();

    void onServerWelcome(int playerId, long seed);

    /**
     * @param initiator
     * @param target
     * @param path
     */
    void onAttackReceived(int initiatorId, int targetId, List<Pair<Integer, Integer>> path);

    void onWaitReceived(int initiatorId, List<Pair<Integer, Integer>> path);

}
