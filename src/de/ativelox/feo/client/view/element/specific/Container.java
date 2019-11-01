package de.ativelox.feo.client.view.element.specific;

import java.awt.Color;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.SpatialObject;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Container<T extends ISpatial & IRenderable & IUpdateable> extends SpatialObject
        implements IRenderable, IUpdateable {

    private T mContainedValue;

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Container(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void set(T value) {
        mContainedValue = value;
        
        if(value == null) {
            return;
        }

        value.setX(getX());
        value.setY(getY());
        value.setWidth(getWidth());
        value.setHeight(getHeight());

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (mContainedValue == null) {
            g.fillRect(Color.black, getX(), getY(), getWidth(), getHeight());
            g.drawRect(Color.red, getX(), getY(), getWidth(), getHeight());

        } else {
            mContainedValue.render(g);

        }
    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mContainedValue != null) {
            mContainedValue.update(ts);
        }

    }
}
