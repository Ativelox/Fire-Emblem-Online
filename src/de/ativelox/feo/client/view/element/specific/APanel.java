package de.ativelox.feo.client.view.element.specific;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.SpatialObject;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.view.Display;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public abstract class APanel<T extends IRenderable & ISpatial> extends SpatialObject implements IRenderable {

    protected final List<T> mObjects;

    public APanel(int percentX, int percentY, int percentWidth, int percentHeight) {
        super((int) ((percentX / 100f) * Display.WIDTH), (int) ((percentY / 100f) * Display.HEIGHT),
                (int) ((percentWidth / 100f) * Display.WIDTH), (int) ((percentHeight / 100f) * Display.HEIGHT));

        mObjects = new ArrayList<>();

    }

    public void add(T toAdd) {
        mObjects.add(toAdd);
        align();
    }

    public void remove(T toRemove) {
        mObjects.remove(toRemove);
        align();
    }

    public void addAll(Collection<T> add) {
        mObjects.addAll(add);
        align();
    }

    public void removeAll(Collection<T> remove) {
        mObjects.removeAll(remove);
        align();
    }

    public void clear() {
        mObjects.clear();
        align();
    }

    protected abstract void align();

    @Override
    public void render(DepthBufferedGraphics g) {
        mObjects.forEach(o -> o.render(g));

    }
}
