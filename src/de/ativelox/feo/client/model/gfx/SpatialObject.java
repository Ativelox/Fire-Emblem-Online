package de.ativelox.feo.client.model.gfx;

import java.awt.Rectangle;

import de.ativelox.feo.client.model.property.ISpatial;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class SpatialObject implements ISpatial {

    protected int mX;

    protected int mY;

    protected int mWidth;

    protected int mHeight;

    public SpatialObject(int x, int y, int width, int height) {
        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;

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

    @Override
    public boolean contains(int x, int y) {
        return (getX() < x && getX() + getWidth() > x && getY() < y && getY() + getHeight() > y);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

}
