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
        mController.displayUnitWindow(unit);

    }

    @Override
    public void onUnitDeselect(IUnit unit) {
        mController.removeUnitWindow(unit);

    }

    @Override
    public void turnStart() {
        mController.enableUserInput();

    }

    @Override
    public void turnEnd() {
        mController.blockUserInput();

    }

    @Override
    public void setController(GameController controller) {
        mController = controller;

    }

    @Override
    public void onUnitConfirm(IUnit unit) {
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
//        mPathRoutine.stop();
//        mController.removeMovementRange();

        mController.displayActionWindow();

    }

    @Override
    public void onActionWindowCanceled() {
        IUnit current = mPathRoutine.getActor();
        mPathRoutine.returnToLast();
        mPathRoutine.startCalculation(current);

        mController.displayMovementIndicator(mPathRoutine.getMovementIndicator());
        mController.displayMovementRange(mPathRoutine.getMovementRange());
        mController.removeActionWindow();
    }
}
