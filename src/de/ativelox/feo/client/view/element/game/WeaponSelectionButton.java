package de.ativelox.feo.client.view.element.game;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.property.EActionWindowType;
import de.ativelox.feo.client.model.unit.item.weapon.IWeapon;
import de.ativelox.feo.client.view.Display;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class WeaponSelectionButton extends ActionWindowButton {

    private final IWeapon mWeapon;

    public WeaponSelectionButton(int x, int y, int order, IWeapon weapon) {
        super(x, y, order, weapon.getName() + " " + weapon.getCurrentDurability(), EActionWindowType.WEAPON_SELECT);

        mWeapon = weapon;

        mXOffset = 10 * Display.INTERNAL_RES_FACTOR;
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        super.render(g);

        g.drawImage(mWeapon.getImage(), getX() + 6 * Display.INTERNAL_RES_FACTOR, getY(),
                mWeapon.getImage().getWidth(null) * Display.INTERNAL_RES_FACTOR,
                mWeapon.getImage().getHeight(null) * Display.INTERNAL_RES_FACTOR);

    }

    public IWeapon getWeapon() {
        return mWeapon;

    }
}
