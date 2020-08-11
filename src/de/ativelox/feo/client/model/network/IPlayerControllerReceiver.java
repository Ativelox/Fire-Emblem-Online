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

    void onServerWelcome(int playerId);

    /**
     * @param initiator
     * @param target
     * @param path
     */
    void onAttackReceived(IUnit initiator, IUnit target, List<Pair<Integer, Integer>> path);

}
