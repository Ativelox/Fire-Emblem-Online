package de.ativelox.feo.client.view.screen.game;

import java.util.Collection;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.camera.ECameraApplication;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.EActionWindowOption;
import de.ativelox.feo.client.model.property.EActionWindowType;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.property.ICancelable;
import de.ativelox.feo.client.model.property.IConfirmable;
import de.ativelox.feo.client.model.property.ISelectable;
import de.ativelox.feo.client.model.property.callback.ICancelListener;
import de.ativelox.feo.client.model.property.callback.IConfirmListener;
import de.ativelox.feo.client.model.property.callback.ISelectionListener;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.item.weapon.IWeapon;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.element.game.ActionWindow;
import de.ativelox.feo.client.view.element.game.ActionWindowButton;
import de.ativelox.feo.client.view.element.game.BattlePreviewWindow;
import de.ativelox.feo.client.view.element.game.TargetSelectionWindow;
import de.ativelox.feo.client.view.element.game.TileStatusWindow;
import de.ativelox.feo.client.view.element.game.UnitBurstWindow;
import de.ativelox.feo.client.view.element.game.WeaponSelectionButton;
import de.ativelox.feo.client.view.element.game.WeaponSelectionWindow;
import de.ativelox.feo.client.view.screen.EScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class GameUIScreen implements IGameUIScreen, ICancelListener, IConfirmListener, ISelectionListener {

    private GameController mController;

    private final ActionWindow mActionWindow;
    private final TileStatusWindow mTileStatusWindow;
    private final UnitBurstWindow mUnitBurstWindow;
    private final WeaponSelectionWindow mWeaponSelectionWindow;
    private TargetSelectionWindow mTargetSelector;
    private final BattlePreviewWindow mBattlePreviewWindow;

    private boolean mIsSystemWindow;

    public GameUIScreen() {
        mTileStatusWindow = new TileStatusWindow();
        mUnitBurstWindow = new UnitBurstWindow(null);
        mActionWindow = new ActionWindow();
        mWeaponSelectionWindow = new WeaponSelectionWindow(null);
        mBattlePreviewWindow = new BattlePreviewWindow();
        mActionWindow.hide();
        mUnitBurstWindow.hide();
        mWeaponSelectionWindow.hide();
        mBattlePreviewWindow.hide();

    }

    @Override
    public void setController(GameController gc) {
        this.mController = gc;
    }

    @Override
    public void displayUnitWindow(IUnit unit) {
        mUnitBurstWindow.setUnit(unit);
        mUnitBurstWindow.show();

    }

    @Override
    public void removeUnitWindow(IUnit unit) {
        mUnitBurstWindow.hide();

    }

    @Override
    public void displayUnitActionWindow(EActionWindowOption... options) {
        mActionWindow.setActions(options);
        mActionWindow.registerTo(mController.getInputManager());
        mActionWindow.addCancelListener(this);
        mActionWindow.addConfirmListener(this);
        mIsSystemWindow = false;

        mActionWindow.show();
        mTileStatusWindow.hide();

    }

    @Override
    public void displaySystemActionWindow(EActionWindowOption... options) {
        mActionWindow.setActions(options);
        mActionWindow.registerTo(mController.getInputManager());
        mActionWindow.addCancelListener(this);
        mActionWindow.addConfirmListener(this);
        mIsSystemWindow = true;

        mActionWindow.show();
        mTileStatusWindow.hide();

    }

    @Override
    public void removeActionWindow() {
        mActionWindow.hide();
        mTileStatusWindow.show();

    }

    @Override
    public EScreen getIdentifier() {
        return EScreen.GAME_SCREEN;
    }

    @Override
    public boolean renderLower() {
        return true;
    }

    @Override
    public boolean updateLower() {
        return true;
    }

    @Override
    public ECameraApplication cameraApplied() {
        return ECameraApplication.WINDOW_RESIZE_ONLY;
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mWeaponSelectionWindow.render(g);
        mActionWindow.render(g);
        mUnitBurstWindow.render(g);
        mTileStatusWindow.render(g);
        mBattlePreviewWindow.render(g);
    }

    @Override
    public void update(TimeSnapshot ts) {
        mWeaponSelectionWindow.update(ts);
        mActionWindow.update(ts);
        mUnitBurstWindow.update(ts);
        mTileStatusWindow.update(ts);

        if (mTargetSelector != null) {
            mTargetSelector.update(ts);

        }

    }

    @Override
    public void onCancel(ICancelable cancelable) {
        if (cancelable instanceof ActionWindowButton) {
            ActionWindowButton cancel = (ActionWindowButton) cancelable;

            if (mIsSystemWindow && cancel.getType() == EActionWindowType.DEFAULT) {
                mController.getActiveBehavior().onSystemActionWindowCanceled();

            } else if (cancel.getType() == EActionWindowType.DEFAULT) {
                mController.getActiveBehavior().onActionWindowCanceled();

            } else {
                mController.getActiveBehavior().onWeaponSelectCancel();

            }

        } else if (cancelable instanceof TargetSelectionWindow) {
            mController.getActiveBehavior().onTargetSelectCancel();
        }
    }

    @Override
    public void onConfirm(IConfirmable confirmable) {
        if (confirmable instanceof ActionWindowButton
                && ((ActionWindowButton) confirmable).getType() == EActionWindowType.DEFAULT) {
            ActionWindowButton confirm = (ActionWindowButton) confirmable;

            switch (EActionWindowOption.valueOf(confirm.getText().toUpperCase().replaceAll("\\s", "_"))) {
            case ATTACK:
                mController.getActiveBehavior().onAttackAction();
                break;
            case TALK:
                break;
            case TRADE:
                break;
            case VISIT:
                break;
            case WAIT:
                mController.getActiveBehavior().onWaitAction();
                break;
            case END_TURN:
                mController.getActiveBehavior().onTurnEnd();
            default:
                break;

            }

        } else if (confirmable instanceof WeaponSelectionButton) {
            WeaponSelectionButton button = (WeaponSelectionButton) confirmable;

            mController.getActiveBehavior().onWeaponSelection(button.getWeapon());

        } else if (confirmable instanceof TargetSelectionWindow) {
            mController.getActiveBehavior().onTargetSelection(((TargetSelectionWindow) confirmable).getSelectedUnit());

        }
    }

    @Override
    public void displayTileStatus(Tile tile) {
        mTileStatusWindow.setTile(tile);

    }

    @Override
    public void removeTileStatus() {
        mTileStatusWindow.setTile(null);

    }

    @Override
    public void switchSides(ESide side) {
        mTileStatusWindow.switchTo(side);
        mActionWindow.switchTo(side);
        mUnitBurstWindow.switchTo(side);
        mBattlePreviewWindow.switchTo(side);

    }

    @Override
    public void displayWeaponSelection(IUnit unit, Collection<IWeapon> eligible) {
        mActionWindow.hide();

        mWeaponSelectionWindow.setUnit(unit, eligible);
        mWeaponSelectionWindow.registerTo(mController.getInputManager());
        mWeaponSelectionWindow.addCancelListener(this);
        mWeaponSelectionWindow.addConfirmListener(this);

        mWeaponSelectionWindow.show();

    }

    @Override
    public void removeWeaponSelection() {
        mWeaponSelectionWindow.hide();
        mActionWindow.show();
    }

    @Override
    public void initializeBattlePreview(Collection<IUnit> targets) {
        mWeaponSelectionWindow.hide();

        mTargetSelector = new TargetSelectionWindow(targets, this);
        mTargetSelector.add(this);
        mTargetSelector.addCancelListener(this);
        mTargetSelector.registerTo(mController.getInputManager());

    }

    @Override
    public void removeBattlePreview() {
        mTargetSelector.reset();
        mTargetSelector = null;
        mBattlePreviewWindow.hide();
        mWeaponSelectionWindow.show();

    }

    @Override
    public void onSelect(ISelectable selectable) {
        if (selectable instanceof IUnit) {
            mController.getActiveBehavior().onTargetSwitch((IUnit) selectable);
        }
    }

    @Override
    public void onDeSelect(ISelectable selectable) {
        // TODO Auto-generated method stub

    }

    @Override
    public void switchBattlePreview(IUnit attacker, IUnit target) {
        mBattlePreviewWindow.switchParticipants(attacker, target);
        mBattlePreviewWindow.show();

    }
}
