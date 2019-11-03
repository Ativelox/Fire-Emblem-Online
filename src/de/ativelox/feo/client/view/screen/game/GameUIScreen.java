package de.ativelox.feo.client.view.screen.game;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.camera.ECameraApplication;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.EActionWindowOption;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.property.ICancelable;
import de.ativelox.feo.client.model.property.IConfirmable;
import de.ativelox.feo.client.model.property.callback.ICancelListener;
import de.ativelox.feo.client.model.property.callback.IConfirmListener;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.element.game.ActionWindow;
import de.ativelox.feo.client.view.element.game.TileStatusWindow;
import de.ativelox.feo.client.view.element.game.UnitBurstWindow;
import de.ativelox.feo.client.view.element.generic.AButtonElement;
import de.ativelox.feo.client.view.screen.EScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class GameUIScreen implements IGameUIScreen, ICancelListener, IConfirmListener {

    private GameController mController;

    private final ActionWindow mActionWindow;
    private final TileStatusWindow mTileStatusWindow;
    private final UnitBurstWindow mUnitBurstWindow;

    private boolean mIsSystemWindow;

    public GameUIScreen() {
        mTileStatusWindow = new TileStatusWindow();
        mUnitBurstWindow = new UnitBurstWindow(null);
        mActionWindow = new ActionWindow();
        mActionWindow.hide();
        mUnitBurstWindow.hide();

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

    }

    @Override
    public void displaySystemActionWindow(EActionWindowOption... options) {
        mActionWindow.setActions(options);
        mActionWindow.registerTo(mController.getInputManager());
        mActionWindow.addCancelListener(this);
        mActionWindow.addConfirmListener(this);
        mIsSystemWindow = true;

        mActionWindow.show();

    }

    @Override
    public void removeActionWindow() {
        mActionWindow.hide();

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
        mActionWindow.render(g);
        mUnitBurstWindow.render(g);
        mTileStatusWindow.render(g);
    }

    @Override
    public void update(TimeSnapshot ts) {
        mActionWindow.update(ts);
        mUnitBurstWindow.update(ts);
        mTileStatusWindow.update(ts);

    }

    @Override
    public void onCancel(ICancelable cancelable) {
        if (mIsSystemWindow) {
            mController.getActiveBehavior().onSystemActionWindowCanceled();

        } else {
            mController.getActiveBehavior().onActionWindowCanceled();

        }
    }

    @Override
    public void onConfirm(IConfirmable confirmable) {
        if (confirmable instanceof AButtonElement) {
            AButtonElement confirm = (AButtonElement) confirmable;

            switch (EActionWindowOption.valueOf(confirm.getText().toUpperCase().replaceAll("\\s", "_"))) {
            case ATTACK:
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

    }
}
