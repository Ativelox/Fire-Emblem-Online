package de.ativelox.feo.client.view.element.game;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.manager.ConfirmCancelWhenSelectedManager;
import de.ativelox.feo.client.model.manager.VerticalSelectionManager;
import de.ativelox.feo.client.model.property.EActionWindowType;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.unit.item.IItem;
import de.ativelox.feo.client.model.unit.item.weapon.IWeapon;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.AActionWindow;
import de.ativelox.feo.client.view.element.generic.AButtonElement;
import de.ativelox.feo.client.view.element.generic.ACancelableButton;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ItemUsageWindow extends AActionWindow {

    private Image mTop;
    private Image mBottom;

    public ItemUsageWindow() {
        super(EActionWindowType.ITEM_USE, 110 * Display.INTERNAL_RES_FACTOR, 0, 49 * Display.INTERNAL_RES_FACTOR, 0,
                false);

        this.setItem(null, null);
    }

    public void setItem(IItem item, ISpatial anchor) {
//        setY((int) ((Display.HEIGHT / 2f) - (this.getHeight() / 2f)) - 20 * Display.INTERNAL_RES_FACTOR);

        EActionWindowType[] type = new EActionWindowType[2];

        mButtons = new ACancelableButton[0];

        if (item != null) {
            mButtons = new ACancelableButton[2];
            if (item instanceof IWeapon) {
                type[0] = EActionWindowType.WEAPON_EQUIP;
            } else {
                type[0] = EActionWindowType.ITEM_USE;
            }
        }
        type[1] = EActionWindowType.ITEM_DISCARD;

        for (int i = 0; i < mButtons.length; i++) {
            mButtons[i] = new ActionWindowButton(getX(),
                    4 * Display.INTERNAL_RES_FACTOR + getY() + (i * 16 * Display.INTERNAL_RES_FACTOR), i,
                    type[i].toString().split("_")[1], type[i]);

        }
        setHeight((16 * mButtons.length + 5 + 4) * Display.INTERNAL_RES_FACTOR);

        mSelectionManager = new VerticalSelectionManager<>(true, mButtons);
        mButtonConfirmManager = new ConfirmCancelWhenSelectedManager<>(mButtons);
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        super.render(g);

        if (mIsHidden || mButtons.length <= 0) {
            return;
        }
        g.drawImage(mTop, getX(), getY(), mTop.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mTop.getHeight(null) * Display.INTERNAL_RES_FACTOR);
        g.drawImage(mBottom, getX(), getY() + getHeight() - 5 * Display.INTERNAL_RES_FACTOR,
                mBottom.getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mBottom.getHeight(null) * Display.INTERNAL_RES_FACTOR, 5);

        for (final AButtonElement button : mButtons) {
            button.render(g);
        }

    }

    @Override
    public void update(TimeSnapshot ts) {
        super.update(ts);

        setY(20 * Display.INTERNAL_RES_FACTOR);
        int i = 0;
        for (final AButtonElement button : mButtons) {
            button.setX(getX());
            button.setY(4 * Display.INTERNAL_RES_FACTOR + getY() + (i * 16 * Display.INTERNAL_RES_FACTOR));
            i++;

            button.adjustTextWidth((int) (getWidth() / 1.5f));
            button.adjustTextHeight((int) (button.getHeight() / 1.5f));
        }
    }

    @Override
    public void switchTo(ESide side) {
        return;

    }

    @Override
    public void load() {
        mTop = Assets.getFor(EResource.ACTION_WINDOW_TOP);
        mBottom = Assets.getFor(EResource.ACTION_WINDOW_BOTTOM);

    }
}
