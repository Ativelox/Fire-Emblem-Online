package de.ativelox.feo.client.model.unit.item.weapon;

import de.ativelox.feo.client.model.property.EDamageType;
import de.ativelox.feo.client.model.unit.item.IItem;
import de.ativelox.feo.client.model.util.Range;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IWeapon extends IItem {
    
    int getCurrentDurability();

    int getMaximumDurability();

    Range<Integer> getRange();

    int getCrit();

    int getMight();

    int getAccuracy();

    int getWeight();

    EDamageType getDamageType();
}
