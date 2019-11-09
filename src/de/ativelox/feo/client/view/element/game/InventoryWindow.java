package de.ativelox.feo.client.view.element.game;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.manager.ConfirmCancelWhenSelectedManager;
import de.ativelox.feo.client.model.manager.VerticalSelectionManager;
import de.ativelox.feo.client.model.property.EActionWindowType;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.AActionWindow;
import de.ativelox.feo.client.view.element.generic.AButtonElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class InventoryWindow extends AActionWindow {

    private Image mTop;
    private Image mBot;
    private Image mFill;

    private int mInventoryLimit;

    public InventoryWindow() {
        super(EActionWindowType.INVENTORY_WINDOW, 0, 0, 0, 0, true);

        setUnit(null);

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        super.render(g);
        
        if(mIsHidden || mButtons.length <= 0) {
            return;
        }

        g.drawImage(mTop, getX(), getY(), getWidth(), mTop.getHeight(null) * Display.INTERNAL_RES_FACTOR);
        g.drawImage(mBot, getX(), getY() + getHeight() - 5 * Display.INTERNAL_RES_FACTOR, getWidth(),
                mBot.getHeight(null) * Display.INTERNAL_RES_FACTOR, 5);

        for (final AButtonElement button : mButtons) {
            button.render(g);
        }

        for (int i = mButtons.length; i < mInventoryLimit; i++) {
            g.drawImage(mFill, mButtons[0].getX(), mButtons[0].getY() + i * mButtons[0].getHeight(),
                    mButtons[0].getWidth(), mButtons[0].getHeight());

        }
    }

    @Override
    public void update(TimeSnapshot ts) {
        super.update(ts);

        setY(10 * Display.INTERNAL_RES_FACTOR);

        int i = 0;
        for (final AButtonElement button : mButtons) {
            button.setX(getX());
            button.setY(4 * Display.INTERNAL_RES_FACTOR + getY() + (i * 16 * Display.INTERNAL_RES_FACTOR));
            i++;
        }
    }

    public boolean setUnit(final IUnit unit) {
        setX(10 * Display.INTERNAL_RES_FACTOR);
        setY(12 * Display.INTERNAL_RES_FACTOR);
        setWidth((int) (Display.WIDTH / 2.5f));

        int limit = 0;
        int invSize = 0;

        if (unit != null) {
            limit = unit.getInventory().getLimit();
            invSize = unit.getInventory().getItems().size();
        }

        mInventoryLimit = limit;

        mButtons = new ItemSelectionButton[invSize];

        for (int i = 0; i < invSize; i++) {

            mButtons[i] = new ItemSelectionButton(getX(),
                    4 * Display.INTERNAL_RES_FACTOR + getY() + (i * 16 * Display.INTERNAL_RES_FACTOR), i,
                    unit.getInventory().getItems().get(i), EActionWindowType.INVENTORY_WINDOW);

            mButtons[i].setWidth(getWidth());

        }
        setHeight((16 * limit + 5 + 4) * Display.INTERNAL_RES_FACTOR);

        mSelectionManager = new VerticalSelectionManager<>(true, mButtons);
        mButtonConfirmManager = new ConfirmCancelWhenSelectedManager<>(mButtons);

        if (mButtons.length <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public void switchTo(ESide side) {
        return;

    }

    @Override
    public void load() {
        mTop = Assets.getFor(EResource.ACTION_WINDOW_TOP);
        mBot = Assets.getFor(EResource.ACTION_WINDOW_BOTTOM);
        mFill = Assets.getFor(EResource.ACTION_WINDOW_MIDDLE);
    }

}
