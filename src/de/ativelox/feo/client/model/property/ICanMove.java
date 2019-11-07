package de.ativelox.feo.client.model.property;

import java.util.Iterator;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.callback.IMoveListener;
import de.zabuza.maglev.external.algorithms.EdgeCost;
import de.zabuza.maglev.external.graph.Edge;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface ICanMove extends ISpatial {

    void move(Iterator<EdgeCost<Tile, Edge<Tile>>> path, boolean reversed);

    void onDirectionChange(EDirection direction);

    void onMoveFinished();

    void addMoveFinishedListener(IMoveListener listener);

    void removeMoveFinishedListener(IMoveListener listener);

}
