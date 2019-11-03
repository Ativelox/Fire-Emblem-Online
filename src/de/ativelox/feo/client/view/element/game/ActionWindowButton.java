package de.ativelox.feo.client.view.element.game;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.EActionWindowType;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.ACancelableButton;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ActionWindowButton extends ACancelableButton implements IRequireResources {

    private Image mSelector;

    private EActionWindowType mType;

    public ActionWindowButton(int x, int y, int order, String text, EActionWindowType type) {
        super(x, y, 49 * Display.INTERNAL_RES_FACTOR, 16 * Display.INTERNAL_RES_FACTOR, false,
                EResource.ACTION_WINDOW_MIDDLE, order, text.replaceAll("_", " "));

        mType = type;
    }

    @Override
    protected void renderSelection(DepthBufferedGraphics g) {
        g.drawImage(mSelector, getX() - 10 * Display.INTERNAL_RES_FACTOR, getY(),
                mSelector.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mSelector.getHeight(null) * Display.INTERNAL_RES_FACTOR);

    }

    @Override
    public void load() {
        super.load();

        mSelector = Assets.getFor(EResource.ACTION_SELECTOR);
    }

    public EActionWindowType getType() {
        return mType;
    }
}
