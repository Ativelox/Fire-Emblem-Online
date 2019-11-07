package de.ativelox.feo.client.model.util;

import java.util.Iterator;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.zabuza.maglev.external.algorithms.EdgeCost;
import de.zabuza.maglev.external.graph.Edge;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IMoveRoutine extends IUpdateable {

    void move(Iterator<EdgeCost<Tile, Edge<Tile>>> path, boolean reversed, int limit);

}
