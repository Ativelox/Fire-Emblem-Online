package de.ativelox.feo.client.view.screen.game;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.camera.ECameraApplication;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.property.EActionWindowOption;
import de.ativelox.feo.client.model.property.ICancelable;
import de.ativelox.feo.client.model.property.IConfirmable;
import de.ativelox.feo.client.model.property.callback.ICancelListener;
import de.ativelox.feo.client.model.property.callback.IConfirmListener;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.element.game.ActionWindow;
import de.ativelox.feo.client.view.element.game.UnitBurstWindow;
import de.ativelox.feo.client.view.element.generic.AButtonElement;
import de.ativelox.feo.client.view.screen.EScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class GameUIScreen implements IGameUIScreen, ICancelListener, IConfirmListener {

    private GameController mController;
    private ActionWindow mActionWindow;

    private UnitBurstWindow mTest;

    private boolean mIsSystemWindow;

    public GameUIScreen() {

    }

    @Override
    public void setController(GameController gc) {
        this.mController = gc;
    }

    @Override
    public void displayUnitWindow(IUnit unit) {
        mTest = new UnitBurstWindow(unit);

    }

    @Override
    public void removeUnitWindow(IUnit unit) {
        mTest = null;

    }

    @Override
    public void displayUnitActionWindow(EActionWindowOption... options) {
        mActionWindow = new ActionWindow(options);
        mActionWindow.registerTo(mController.getInputManager());
        mActionWindow.addCancelListener(this);
        mActionWindow.addConfirmListener(this);
        mIsSystemWindow = false;

    }

    @Override
    public void displaySystemActionWindow(EActionWindowOption... options) {
        mActionWindow = new ActionWindow(options);
        mActionWindow.registerTo(mController.getInputManager());
        mActionWindow.addCancelListener(this);
        mActionWindow.addConfirmListener(this);
        mIsSystemWindow = true;

    }

    @Override
    public void removeActionWindow() {
        mActionWindow = null;

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
        if (mActionWindow != null) {
            mActionWindow.render(g);
        }

        if (mTest != null) {
            mTest.render(g);
        }
    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mActionWindow != null) {
            mActionWindow.update(ts);
        }
        if (mTest != null) {
            mTest.update(ts);
        }

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
            default:
                break;

            }

        }
    }
}
