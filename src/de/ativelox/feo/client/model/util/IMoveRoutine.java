package de.ativelox.feo.client.model.util;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.zabuza.maglev.external.algorithms.Path;
import de.zabuza.maglev.external.graph.Edge;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IMoveRoutine extends IUpdateable {

    void move(Path<Tile, Edge<Tile>> path);

}
