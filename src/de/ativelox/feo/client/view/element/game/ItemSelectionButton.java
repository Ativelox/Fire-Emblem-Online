package de.ativelox.feo.client.view.element.game;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.EActionWindowType;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.unit.item.IItem;
import de.ativelox.feo.client.model.unit.item.weapon.IWeapon;
import de.ativelox.feo.client.view.Display;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ItemSelectionButton extends ActionWindowButton implements IRequireResources {

    private final IItem mItem;

    private Image mCurrentDurability;

    public ItemSelectionButton(int x, int y, int order, IItem item, EActionWindowType type) {
        super(x, y, order, item.getName(), type);

        mItem = item;

        mXOffset = -3 * Display.INTERNAL_RES_FACTOR;

        this.load();
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        super.render(g);

        g.drawImage(mItem.getImage(), getX() + 6 * Display.INTERNAL_RES_FACTOR, getY(),
                mItem.getImage().getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mItem.getImage().getHeight(null) * Display.INTERNAL_RES_FACTOR);

        if (mCurrentDurability != null) {
            g.drawImage(mCurrentDurability, getX() + 72 * Display.INTERNAL_RES_FACTOR,
                    getY() + 1 * Display.INTERNAL_RES_FACTOR);

        }

    }

    public IItem getItem() {
        return mItem;

    }

    @Override
    public void load() {
        super.load();

        if (mItem != null && mItem instanceof IWeapon) {
            mCurrentDurability = Assets.getFor(EResource.REGULAR_FONT, ((IWeapon) mItem).getCurrentDurability() + "");

        }

    }
}
