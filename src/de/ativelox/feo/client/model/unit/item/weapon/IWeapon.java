package de.ativelox.feo.client.model.unit.item.weapon;

import de.ativelox.feo.client.model.property.EDamageType;
import de.ativelox.feo.client.model.unit.item.IItem;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IWeapon extends IItem {
    
    int getCurrentDurability();

    int getMaximumDurability();

    int getRange();

    int getCrit();

    int getMight();

    int getAccuracy();

    int getWeight();

    EDamageType getDamageType();
}
