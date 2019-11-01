package de.ativelox.feo.client.model.util;

import java.util.Deque;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.IUpdateable;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IMoveRoutine extends IUpdateable {

    void move(Deque<Tile> path);

}
