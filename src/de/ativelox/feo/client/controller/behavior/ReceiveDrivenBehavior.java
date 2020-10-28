package de.ativelox.feo.client.controller.behavior;

import java.util.List;
import java.util.Optional;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.network.IPlayerControllerReceiver;
import de.ativelox.feo.client.model.network.NetworkRoutine;
import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.EPlayerAction;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.maglev.SimplePath;
import de.ativelox.feo.util.Pair;
import de.zabuza.maglev.external.algorithms.Path;
import de.zabuza.maglev.external.graph.Edge;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ReceiveDrivenBehavior extends BehaviorAdapter implements IPlayerControllerReceiver {

    private IUnit mTarget;

    private final NetworkRoutine mNetworkRoutine;

    private Path<Tile, Edge<Tile>> mCurrentPath;

    private EPlayerAction mNextAction;

    /**
     * @param map
     * @param affiliation
     */
    public ReceiveDrivenBehavior(Map map, EAffiliation affiliation, NetworkRoutine routine) {
	super(map, affiliation);

	System.out.println(affiliation);

	mNetworkRoutine = routine;

    }

    @Override
    public void onTurnStart() {
	super.onTurnStart();
	mController.blockNonUiInput();

    }

    @Override
    public void onTurnEnd() {
	super.onTurnEnd();
	mController.unBlockNonUiInput();
    }

    @Override
    public void onMovementFinished(IUnit unit) {
	switch (mNextAction) {
	case ATTACK:
	    mController.initiateAttack(unit, mTarget);
	    break;
	case WAIT:
	    unit.finished();
	    break;
	default:
	    break;

	}

    }

    @Override
    public void onBattleFinished() {
	mController.attackFinished();
    }

    @Override
    public void onUnitSelect(IUnit unit) {
	if (!mIsOnTurn) {
	    return;
	}
	System.out.println("onUnitSelect called");
	unit.move(mCurrentPath.iterator(), false);

    }

    @Override
    public void onTurnEndReceived() {
	this.onTurnEnd();

    }

    @Override
    public void onAttackReceived(int initiatorId, int targetId, List<Pair<Integer, Integer>> path) {
	IUnit initiator = mMap.getById(initiatorId);
	IUnit target = mMap.getById(targetId);

	mCurrentPath = SimplePath.of(path, mMap);

	mTarget = target;

	mNextAction = EPlayerAction.ATTACK;
	this.onUnitSelect(initiator);
    }

    private Optional<IUnit> getAppropriateUnit(IUnit fake) {

	List<IUnit> units = mMap.getUnitsBy(fake.getAffiliation());

	for (final IUnit unit : units) {
	    if (unit.getName().equals(fake.getName())) {
		return Optional.of(unit);
	    }

	}
	return Optional.empty();

    }

    @Override
    public void onServerWelcome(int playerId, long seed) {
	mNetworkRoutine.start(playerId, seed);

    }

    @Override
    public void onWaitReceived(int initiatorId, List<Pair<Integer, Integer>> path) {
	IUnit initator = mMap.getById(initiatorId);

	if (mNextAction != null && mNextAction.equals(EPlayerAction.ATTACK)) {
	    mNextAction = EPlayerAction.WAIT;
	    initator.finished();
	    return;
	}

	mCurrentPath = SimplePath.of(path, mMap);
	mNextAction = EPlayerAction.WAIT;
	this.onUnitSelect(initator);

    }
}
