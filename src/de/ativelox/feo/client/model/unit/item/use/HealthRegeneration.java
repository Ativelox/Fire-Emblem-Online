package de.ativelox.feo.client.model.unit.item.use;

import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.item.IItem;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class HealthRegeneration implements IItemUsageContract {

    private final int mAmount;

    public HealthRegeneration(int amount) {
        mAmount = amount;
    }

    @Override
    public void accept(IUnit t, IItem u) {
        t.addHp(mAmount);

    }
}
