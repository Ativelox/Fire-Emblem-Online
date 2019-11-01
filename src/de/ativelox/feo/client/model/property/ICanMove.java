package de.ativelox.feo.client.model.property;

import java.util.Deque;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.callback.IMoveFinishedListener;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface ICanMove extends ISpatial {

    void move(Deque<Tile> path);

    void onDirectionChange(EDirection direction);

    void onMoveFinished();

    void addMoveFinishedListener(IMoveFinishedListener listener);

    void removeMoveFinishedListener(IMoveFinishedListener listener);

}
