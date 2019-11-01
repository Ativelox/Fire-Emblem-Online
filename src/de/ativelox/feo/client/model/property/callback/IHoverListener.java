package de.ativelox.feo.client.model.property.callback;

import de.ativelox.feo.client.model.property.IHoverable;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IHoverListener {

    void onHoverStart(IHoverable hoverable);

    void onHoverLoss(IHoverable hoverable);

    void onHover(IHoverable hoverable);

}
