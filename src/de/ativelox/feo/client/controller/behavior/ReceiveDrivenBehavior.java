package de.ativelox.feo.client.controller.behavior;

import java.util.List;
import java.util.Optional;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.network.IPlayerControllerReceiver;
import de.ativelox.feo.client.model.network.NetworkRoutine;
import de.ativelox.feo.client.model.property.EAffiliation;
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

    /**
     * @param map
     * @param affiliation
     */
    public ReceiveDrivenBehavior(Map map, EAffiliation affiliation, NetworkRoutine routine) {
        super(map, affiliation);

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
        System.out.println("MOVEMENT FINISHED");

        mController.initiateAttack(unit, mTarget);

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
        unit.move(mCurrentPath.iterator(), false);

    }

    @Override
    public void onTurnEndReceived() {
        this.onTurnEnd();

    }

    @Override
    public void onAttackReceived(IUnit initiator, IUnit target, List<Pair<Integer, Integer>> path) {
        Optional<IUnit> possibleInitiator = getAppropriateUnit(initiator);
        Optional<IUnit> possibleTarget = getAppropriateUnit(target);

        mCurrentPath = SimplePath.of(path, mMap);

        if (possibleInitiator.isEmpty() || possibleTarget.isEmpty()) {
            throw new UnknownError("Couldnt find a suited initiator/target");
        }

        mTarget = possibleTarget.get();

        this.onUnitSelect(possibleInitiator.get());
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
    public void onServerWelcome(int playerId) {
        mNetworkRoutine.start(playerId);

    }
}
