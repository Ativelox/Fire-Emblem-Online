package de.ativelox.feo.client.view.element.generic;

import de.ativelox.feo.client.model.property.IUpdateable;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public abstract class AUpdateableScreenElement extends AScreenElement implements IUpdateable {

    public AUpdateableScreenElement(int x, int y, int width, int height, boolean isPercent) {
        super(x, y, width, height, isPercent);
    }

}
