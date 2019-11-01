package de.ativelox.feo.client.model.property;

import de.ativelox.feo.client.model.property.callback.IHoverListener;
import de.ativelox.feo.client.model.property.callback.IRelativeMouseMoveListener;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IHoverable extends ISpatial, IUpdateable, IRelativeMouseMoveListener {

    void hoverStart();

    void hoverStop();

    void addHoverListener(final IHoverListener listener);
    
    void removeHoverListener(final IHoverListener listener);

}
