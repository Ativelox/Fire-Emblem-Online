package de.ativelox.feo.client.model.property;

import java.awt.Rectangle;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface ISpatial {

    int getX();

    int getY();

    void setX(int x);

    void setY(int y);

    int getWidth();

    int getHeight();

    void setWidth(int width);

    void setHeight(int height);

    default boolean contains(int x, int y) {
        return getBounds().contains(x, y);
    }

    default Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

}
