package de.ativelox.feo.client.view.element.generic;

import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.view.Display;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public abstract class AScreenElement implements ISpatial, IRenderable {

    private int mX;

    private int mY;

    private int mWidth;

    private int mHeight;

    public AScreenElement(int x, int y, int width, int height, boolean isPercent) {
        if (isPercent) {
            setX((int) ((x / 100f) * Display.WIDTH));
            setY((int) ((y / 100f) * Display.HEIGHT));
            setWidth((int) ((width / 100f) * Display.WIDTH));
            setHeight((int) ((height / 100f) * Display.HEIGHT));

        } else {
            setX(x);
            setY(y);
            setWidth(width);
            setHeight(height);
        }
    }

    @Override
    public int getX() {
        return mX;
    }

    @Override
    public int getY() {
        return mY;
    }

    @Override
    public void setX(int x) {
        mX = x;

    }

    @Override
    public void setY(int y) {
        mY = y;

    }

    @Override
    public int getWidth() {
        return mWidth;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    @Override
    public void setWidth(int width) {
        mWidth = width;

    }

    @Override
    public void setHeight(int height) {
        mHeight = height;

    }

}
