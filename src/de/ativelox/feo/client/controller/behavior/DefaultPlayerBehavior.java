package de.ativelox.feo.client.controller.behavior;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.routine.PathCalculationRoutine;
import de.ativelox.feo.client.model.sound.ESoundEffect;
import de.ativelox.feo.client.model.sound.SoundPlayer;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.IWeapon;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DefaultPlayerBehavior implements IBehavior {

    private GameController mController;

    private PathCalculationRoutine mPathRoutine;

    private final EAffiliation mAffiliation;

    private boolean mIsOnTurn;

    public DefaultPlayerBehavior(final Map map, EAffiliation affiliation) {
        mPathRoutine = new PathCalculationRoutine(map);

        mAffiliation = affiliation;
    }

    @Override
    public void onUnitSelect(IUnit unit) {
        if (!mIsOnTurn) {
            return;
        }

        if (mPathRoutine.isActive()) {
            return;
        }

        mController.displayUnitWindow(unit);

    }

    @Override
    public void onUnitDeselect(IUnit unit) {
        if (!mIsOnTurn) {
            return;
        }
        mController.removeUnitWindow(unit);

    }

    @Override
    public void onTurnStart() {
        Logger.get().logInfo("Turn start: " + getAffiliation());

        mIsOnTurn = true;
        mController.turnStart(this);

    }

    @Override
    public void onTurnEnd() {
        Logger.get().logInfo("Turn end: " + getAffiliation());

        mController.removeActionWindow();
        mController.unBlockNonUiInput();

        mIsOnTurn = false;
        mController.turnEnd(this);

    }

    @Override
    public void setController(GameController controller) {
        mController = controller;

    }

    @Override
    public void onUnitConfirm(IUnit unit) {
        if (!mIsOnTurn) {
            return;
        }
        if (unit.getAffiliation() != mAffiliation) {
            SoundPlayer.get().play(ESoundEffect.WINDOW_POPUP);
            mController.displaySystemActionWindow();
            mController.blockNonUiInput();
            return;

        }

        if (unit.isWaiting() || mPathRoutine.isActive()) {
            return;
        }
        SoundPlayer.get().play(ESoundEffect.MOVEMENT_RANGE);
        mPathRoutine.startCalculation(unit);
        mController.displayMovementRange(mPathRoutine.getMovementRange());
        mController.displayMovementIndicator(mPathRoutine.getMovementIndicator());

    }

    @Override
    public void onCursorMove(ISpatial cursor) {
        if (!mIsOnTurn) {
            return;
        }
        SoundPlayer.get().play(ESoundEffect.CURSOR_MOVE);
        mController.cursorMoved(cursor);
        mPathRoutine.updateCursorLocation(cursor);
        mController.displayTileStatus(cursor.getX(), cursor.getY());

    }

    @Override
    public void onConfirm() {
        if (!mIsOnTurn) {
            return;
        }
        if (mPathRoutine.isActive()) {
            mPathRoutine.executeIfValid();
            mController.removeMovementIndicator();
            mController.removeMovementRange();

        } else {
            SoundPlayer.get().play(ESoundEffect.WINDOW_POPUP);
            // confirm was pressed, not on a unit and not while a unit is moving etc.
            mController.displaySystemActionWindow();
            mController.blockNonUiInput();

        }
    }

    @Override
    public void onCancel() {
        if (!mIsOnTurn) {
            return;
        }
        SoundPlayer.get().play(ESoundEffect.WINDOW_CANCELED);
        if (mPathRoutine.isActive()) {
            mPathRoutine.stop();
            mController.removeMovementIndicator();
            mController.removeMovementRange();
        }

    }

    @Override
    public void onMovementFinished(IUnit unit) {
        if (!mIsOnTurn) {
            return;
        }
        if (unit.getAffiliation() != mAffiliation) {
            return;
        }
        SoundPlayer.get().play(ESoundEffect.WINDOW_POPUP);
        mController.displayActionWindow(unit);
        mController.blockNonUiInput();

    }

    @Override
    public void onActionWindowCanceled() {
        if (!mIsOnTurn) {
            return;
        }
        SoundPlayer.get().play(ESoundEffect.WINDOW_CANCELED);

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
        if (!mIsOnTurn) {
            return;
        }
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
        if (!mIsOnTurn) {
            return;
        }
        SoundPlayer.get().play(ESoundEffect.WINDOW_CANCELED);

        mController.removeActionWindow();
        mController.unBlockNonUiInput();

    }

    @Override
    public EAffiliation getAffiliation() {
        return mAffiliation;
    }

    @Override
    public void onAttackAction() {
        if (!mIsOnTurn) {
            return;
        }
        SoundPlayer.get().play(ESoundEffect.WINDOW_ACCEPT);

        mController.showWeaponSelection(mPathRoutine.getActor());

    }

    @Override
    public void onWeaponSelectCancel() {
        if (!mIsOnTurn) {
            return;
        }

        SoundPlayer.get().play(ESoundEffect.WINDOW_CANCELED);

        mController.removeWeaponSelect();

    }

    @Override
    public void onWeaponSelection(IWeapon weapon) {
        if (!mIsOnTurn) {
            return;
        }
        SoundPlayer.get().play(ESoundEffect.WINDOW_ACCEPT);

        mController.showTargetUnitSelect(mPathRoutine.getActor(), weapon);

    }

    @Override
    public void onTargetSelectCancel() {
        if (!mIsOnTurn) {
            return;
        }
        SoundPlayer.get().play(ESoundEffect.WINDOW_CANCELED);

        mController.removeTargetSelect();

    }

    @Override
    public void onTargetSelection(IUnit target) {
        if (!mIsOnTurn) {
            return;
        }
        SoundPlayer.get().play(ESoundEffect.WINDOW_ACCEPT);

        mController.removeTargetSelect();
        mController.removeWeaponSelect();

        mController.initiateAttack(mPathRoutine.getActor(), target);

    }

    @Override
    public void onTargetSwitch(IUnit target) {
        if (!mIsOnTurn) {
            return;
        }
        mController.switchTarget(mPathRoutine.getActor(), target);

    }

    @Override
    public void onBattleFinished() {
        if (!mIsOnTurn) {
            return;
        }
        mController.attackFinished();
        this.onWaitAction();

    }
}
