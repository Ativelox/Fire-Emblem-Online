package de.ativelox.feo.client.model.network;

import java.util.Iterator;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.network.INetworkController;
import de.ativelox.feo.network.protocol.EC2S;
import de.ativelox.feo.network.protocol.ES2C;
import de.zabuza.maglev.external.algorithms.EdgeCost;
import de.zabuza.maglev.external.graph.Edge;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DefaultPlayerControllerSender implements IPlayerControllerSender<EC2S, ES2C> {

    private INetworkController<EC2S, ES2C> mNetworkController;

    @Override
    public void sendAttack(IUnit initiator, IUnit target, Iterator<EdgeCost<Tile, Edge<Tile>>> path) {
	mNetworkController.send(EC2S.ATTACK,
		new String[] { encodeUnit(initiator), encodeUnit(target), encodePath(path) });

    }

    @Override
    public void sendWait(IUnit initator, Iterator<EdgeCost<Tile, Edge<Tile>>> path) {
	mNetworkController.send(EC2S.WAIT, new String[] { encodeUnit(initator), encodePath(path) });

    }

    @Override
    public void sendEndTurn() {
	mNetworkController.send(EC2S.END_TURN);

    }

    private static String encodeUnit(IUnit unit) {
	return unit.getId() + "";

    }

    private static String encodePath(Iterator<EdgeCost<Tile, Edge<Tile>>> path) {

	String result = "";

	while (path.hasNext()) {
	    EdgeCost<Tile, Edge<Tile>> edge = path.next();

	    result += edge.getEdge().getSource().getX() + " " + edge.getEdge().getSource().getY() + " ";
	    result += edge.getEdge().getDestination().getX() + " " + edge.getEdge().getDestination().getY() + " ";

	    System.out.println("Encoding " + edge.getEdge().getSource().getX() + " " + edge.getEdge().getSource().getY());
	    System.out.println("Encoding " + edge.getEdge().getDestination().getX() + " " + edge.getEdge().getDestination().getY());

	}

	System.out.println("Written: " + result);

	return result;

    }

    @Override
    public void register(INetworkController<EC2S, ES2C> networkController) {
	mNetworkController = networkController;

    }
}
