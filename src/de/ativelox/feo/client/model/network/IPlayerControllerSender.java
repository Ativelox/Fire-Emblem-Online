package de.ativelox.feo.client.model.network;

import java.util.Iterator;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.network.INetworkController;
import de.zabuza.maglev.external.algorithms.EdgeCost;
import de.zabuza.maglev.external.graph.Edge;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IPlayerControllerSender<POut, PIn> {

    /**
     * Registers an {@link INetworkController} for this instance, used to send data
     * to the receiving end.
     * 
     * @param networkController The {@link INetworkController} used to send data.
     */
    void register(final INetworkController<POut, PIn> networkController);

    public void sendAttack(final IUnit initiator, final IUnit target, Iterator<EdgeCost<Tile, Edge<Tile>>> path);

    public void sendWait(final IUnit initator, Iterator<EdgeCost<Tile, Edge<Tile>>> path);

    public void sendEndTurn();

}
