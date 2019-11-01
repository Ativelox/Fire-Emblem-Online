package de.ativelox.feo.client.model.property.routine;

import java.util.Optional;

import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class UnitSelectionRoutine implements IUpdateable {

    private final ISpatial mSelector;
    private final Map mMap;

    private IUnit mCurrentlySelected;

    private boolean mIsBlocked;

    public UnitSelectionRoutine(ISpatial selector, Map map) {
        mSelector = selector;
        mMap = map;
    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mIsBlocked) {
            return;
        }

        Optional<IUnit> unit = mMap.isOccupied(mSelector.getX(), mSelector.getY());

        if (unit.isPresent()) {
            if (mCurrentlySelected == null) {
                mCurrentlySelected = unit.get();
                unit.get().selected();
            }

            if (mCurrentlySelected != null && mCurrentlySelected != unit.get()) {
                mCurrentlySelected.deSelected();
                mCurrentlySelected = unit.get();
                unit.get().selected();
            }

        } else {
            if (mCurrentlySelected != null) {
                mCurrentlySelected.deSelected();
                mCurrentlySelected = null;

            }
        }
    }

    public void deSelectInstantly() {
        mCurrentlySelected.deSelected();
        mCurrentlySelected = null;
    }

    public void block() {
        mIsBlocked = true;
    }

    public void unblock() {
        mIsBlocked = false;
    }

    public Optional<IUnit> getSelected() {
        if (mCurrentlySelected == null) {
            return Optional.empty();
        }
        return Optional.of(mCurrentlySelected);
    }
}
