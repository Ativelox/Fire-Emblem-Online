package de.ativelox.feo.client.view.element.game;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.IRequireResource;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.element.generic.AScreenElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MapSelector extends AScreenElement implements IUpdateable, IRequireResource<IAnimation> {

    private final IAnimation mSelector;

    private final ISpatial mBounds;

    public MapSelector(int x, int y, ISpatial bounds) {
        super(x, y, Tile.WIDTH, Tile.HEIGHT, false);

        mBounds = bounds;

        mSelector = request();
        mSelector.start();
    }

    public void stop() {
        mSelector.stop();
    }

    public void start() {
        mSelector.start();
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mSelector.render(g);

    }

    @Override
    public void update(TimeSnapshot ts) {
        if (getX() < mBounds.getX()) {
            setX(mBounds.getX());

        }
        if (getX() + getWidth() > mBounds.getX() + mBounds.getWidth()) {
            setX(mBounds.getWidth() + mBounds.getX() - getWidth());

        }
        if (getY() < mBounds.getY()) {
            setY(mBounds.getY());
        }
        if (getY() + getHeight() > mBounds.getY() + mBounds.getHeight()) {
            setY(mBounds.getHeight() + mBounds.getY() - getHeight());
        }

        mSelector.setX(this.getX());
        mSelector.setY(this.getY());

        mSelector.update(ts);

    }

    @Override
    public EResource getResourceTypes() {
        return EResource.MAP_SELECTOR;

    }
}
