package de.ativelox.feo.client.controller.behavior;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.unit.IUnit;
import de.zabuza.maglev.external.algorithms.Path;
import de.zabuza.maglev.external.graph.Edge;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class SimpleAIBehavior extends BehaviorAdapter {

    private IUnit mCurrent;

    private java.util.Map<IUnit, Boolean> mSelected;

    public SimpleAIBehavior(Map map, EAffiliation affiliation) {
        super(map, affiliation);
        mSelected = new HashMap<>();

    }

    @Override
    public void onTurnStart() {
        super.onTurnStart();
        mController.blockNonUiInput();

        mUnits.forEach(u -> mSelected.put(u, false));

        selectNextUnit();

    }

    @Override
    public void onTurnEnd() {
        super.onTurnEnd();
        mController.unBlockNonUiInput();
    }

    @Override
    public void onMovementFinished(IUnit unit) {
        Iterator<IUnit> units = mMap.getOpponentsInRange(unit, unit.getMaxRange()).iterator();

        if (units.hasNext()) {
            mController.initiateAttack(unit, units.next());

        } else {
            unit.finished();
            selectNextUnit();

        }
    }

    @Override
    public void onBattleFinished() {
        mCurrent.finished();
        mController.attackFinished();

        this.selectNextUnit();
    }

    @Override
    public void onUnitSelect(IUnit unit) {
        if (!mIsOnTurn || mSelected.get(unit)) {
            return;
        }
        mSelected.put(unit, true);

        Optional<Path<Tile, Edge<Tile>>> path = mMap.getNearestPathToAttackable(unit);

        if (path.isEmpty()) {
            unit.finished();
            this.selectNextUnit();
            return;

        }
        unit.move(path.get().reverseIterator(), true);

    }

    private void selectNextUnit() {
        if (getNext().isEmpty()) {
            onTurnEnd();
            return;
        }
        IUnit unit = getNext().get();
        mCurrent = unit;

        unit.selected();

    }

    private Optional<IUnit> getNext() {
        for (final IUnit unit : mUnits) {
            if (!unit.isWaiting()) {

                return Optional.of(unit);
            }
        }
        return Optional.empty();

    }
}
