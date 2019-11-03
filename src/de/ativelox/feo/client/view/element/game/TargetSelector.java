package de.ativelox.feo.client.view.element.game;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;
import de.ativelox.feo.client.model.property.IRequireResource;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.element.generic.AScreenElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class TargetSelector extends AScreenElement implements IRequireResource<IAnimation>, IUpdateable {

    private final IAnimation mSelector;

    public TargetSelector() {
        super(0, 0, 0, 0, false);
        mSelector = request();
        mSelector.hide();
    }

    public void show() {
        mSelector.start();
        mSelector.show();
    }

    public void hide() {
        mSelector.stop();
        mSelector.reset();
        mSelector.hide();
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mSelector.render(g);
    }

    @Override
    public void update(TimeSnapshot ts) {
        mSelector.setX(getX());
        mSelector.setY(getY());

        mSelector.update(ts);

    }

    @Override
    public EResource getResourceTypes() {
        return EResource.TARGET_SELECTOR;
    }

}
