package de.ativelox.feo.client.controller.behavior;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.routine.PathCalculationRoutine;
import de.ativelox.feo.client.model.unit.IUnit;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DefaultPlayerBehavior implements IBehavior {

    private GameController mController;

    private PathCalculationRoutine mPathRoutine;

    public DefaultPlayerBehavior(final Map map) {
        mPathRoutine = new PathCalculationRoutine(map);
    }

    @Override
    public void onUnitSelect(IUnit unit) {
        if (mPathRoutine.isActive()) {
            return;
        }

        mController.displayUnitWindow(unit);

    }

    @Override
    public void onUnitDeselect(IUnit unit) {
        mController.removeUnitWindow(unit);

    }

    @Override
    public void onTurnStart() {
        mController.enableUserInput();

    }

    @Override
    public void onTurnEnd() {
        mController.blockUserInput();

    }

    @Override
    public void setController(GameController controller) {
        mController = controller;

    }

    @Override
    public void onUnitConfirm(IUnit unit) {
        if (unit.isWaiting() || mPathRoutine.isActive()) {
            return;
        }
        mPathRoutine.startCalculation(unit);
        mController.displayMovementRange(mPathRoutine.getMovementRange());
        mController.displayMovementIndicator(mPathRoutine.getMovementIndicator());

    }

    @Override
    public void onCursorMove(ISpatial cursor) {
        mPathRoutine.updateCursorLocation(cursor);

    }

    @Override
    public void onConfirm() {
        if (mPathRoutine.isActive()) {
            mPathRoutine.executeIfValid();
            mController.removeMovementIndicator();
            mController.removeMovementRange();

        } else {
            // confirm was pressed, not on a unit and not while a unit is moving etc.
            mController.displaySystemActionWindow();
            mController.blockNonUiInput();

        }
    }

    @Override
    public void onCancel() {
        if (mPathRoutine.isActive()) {
            mPathRoutine.stop();
            mController.removeMovementIndicator();
            mController.removeMovementRange();
        }

    }

    @Override
    public void onMovementFinished(IUnit unit) {
        mController.displayActionWindow();
        mController.blockNonUiInput();

    }

    @Override
    public void onActionWindowCanceled() {
        IUnit current = mPathRoutine.getActor();
        mPathRoutine.returnToLast();
        mPathRoutine.startCalculation(current);

        mController.displayMovementIndicator(mPathRoutine.getMovementIndicator());
        mController.displayMovementRange(mPathRoutine.getMovementRange());
        mController.removeActionWindow();

        mController.unBlockNonUiInput();

    }

    @Override
    public void onWaitAction() {
        if (!mPathRoutine.isActive()) {
            return;
        }

        IUnit actor = mPathRoutine.getActor();
        actor.finished();
        mPathRoutine.stop();

        mController.removeActionWindow();
        mController.unBlockNonUiInput();
        mController.displayUnitWindow(actor);

    }

    @Override
    public void onSystemActionWindowCanceled() {
        mController.removeActionWindow();
        mController.unBlockNonUiInput();

    }
}
