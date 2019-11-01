package de.ativelox.feo.client.view.element.menu;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.AButtonElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MainMenuButton extends AButtonElement {

    private Image mLeftSelector;

    private Image mRightSelector;

    public MainMenuButton(int x, int y, int width, int height, boolean isPercent, EResource background, int order,
            String text) {
        super(x, y, width, height, isPercent, background, order, text);
    }

    public MainMenuButton(int x, int y, int width, int height, boolean isPercent, EResource background, int order) {
        super(x, y, width, height, isPercent, background, order);
    }

    @Override
    protected void renderSelection(DepthBufferedGraphics g) {
        g.drawImage(mLeftSelector, getX() - (mLeftSelector.getWidth(null) * Display.INTERNAL_RES_FACTOR) / 2, getY(),
                mLeftSelector.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mLeftSelector.getHeight(null) * Display.INTERNAL_RES_FACTOR);
        g.drawImage(mRightSelector,
                getX() + getWidth() - (mRightSelector.getWidth(null) * Display.INTERNAL_RES_FACTOR) / 2, getY(),
                mRightSelector.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mRightSelector.getHeight(null) * Display.INTERNAL_RES_FACTOR);
    }

    @Override
    public void load() {
        super.load();

        mLeftSelector = Assets.getFor(EResource.SYSTEM_SELECTOR_LEFT);
        mRightSelector = Assets.getFor(EResource.SYSTEM_SELECTOR_RIGHT);

    }

}
