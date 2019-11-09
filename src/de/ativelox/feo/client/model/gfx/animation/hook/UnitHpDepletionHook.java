package de.ativelox.feo.client.model.gfx.animation.hook;

import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class UnitHpDepletionHook implements IHook {

    private final IUnit mUnit;
    private final int mToRemove;

    private boolean mIsFinished;

    public UnitHpDepletionHook(IUnit unit, int toRemove) {
        mUnit = unit;
        mToRemove = toRemove;
        mIsFinished = false;
    }

    @Override
    public Boolean apply(TimeSnapshot t) {
        if (mIsFinished) {
            return true;
        }
        mUnit.removeHp(mToRemove);
        mIsFinished = true;

        return true;
    }
}
