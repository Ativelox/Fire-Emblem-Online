package de.ativelox.feo.client.model.property;

import java.util.Deque;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.callback.IMoveListener;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface ICanMove extends ISpatial {

    void move(Deque<Tile> path);

    void onDirectionChange(EDirection direction);

    void onMoveFinished();

    void addMoveFinishedListener(IMoveListener listener);

    void removeMoveFinishedListener(IMoveListener listener);

}
