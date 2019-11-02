package de.ativelox.feo.client.model.property.callback;

import de.ativelox.feo.client.model.property.ICanMove;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IMoveListener {

    void onMoveFinished(ICanMove mover);

    void onMoveStarted(ICanMove mover);

}
