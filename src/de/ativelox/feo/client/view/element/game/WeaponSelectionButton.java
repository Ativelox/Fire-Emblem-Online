package de.ativelox.feo.client.view.element.game;

import de.ativelox.feo.client.model.property.EActionWindowType;
import de.ativelox.feo.client.model.unit.IWeapon;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class WeaponSelectionButton extends ActionWindowButton {

    private final IWeapon mWeapon;

    public WeaponSelectionButton(int x, int y, int order, IWeapon weapon) {
        super(x, y, order, weapon.getName() + " " + weapon.getCurrentDurability(), EActionWindowType.WEAPON_SELECT);

        mWeapon = weapon;
    }

    public IWeapon getWeapon() {
        return mWeapon;

    }
}
