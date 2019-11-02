package de.ativelox.feo.client.view.screen;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.controller.input.EAction;
import de.ativelox.feo.client.controller.input.EAxis;
import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.controller.input.InputReceiver;
import de.ativelox.feo.client.model.camera.Camera;
import de.ativelox.feo.client.model.camera.ECameraApplication;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.property.EActionWindowOption;
import de.ativelox.feo.client.model.property.ICanMove;
import de.ativelox.feo.client.model.property.ICancelable;
import de.ativelox.feo.client.model.property.ISelectable;
import de.ativelox.feo.client.model.property.callback.ICancelListener;
import de.ativelox.feo.client.model.property.callback.IMoveListener;
import de.ativelox.feo.client.model.property.callback.ISelectionListener;
import de.ativelox.feo.client.model.property.routine.UnitSelectionRoutine;
import de.ativelox.feo.client.model.unit.DummyUnit;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.UnitData;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.game.ActionWindow;
import de.ativelox.feo.client.view.element.game.ActionWindowButton;
import de.ativelox.feo.client.view.element.game.MapSelector;
import de.ativelox.feo.client.view.element.game.MovementIndicator;
import de.ativelox.feo.client.view.element.game.MovementRange;
import de.ativelox.feo.client.view.element.generic.ImageElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class GameScreen extends InputReceiver
        implements IGameScreen, ISelectionListener, IMoveListener, ICancelListener {

    private final Map mMap;

    private final IUnit mUnit;

    private MapSelector mSelectionCursor;

    private GameController mController;

    private final UnitSelectionRoutine mSelectionRoutine;

    private ImageElement mUnitDisplayPlaceholder;

    private MovementRange mMovementRange;

    private MovementIndicator mMovementIndicator;

    private ActionWindow mCurrentActionWindow;

    private InputManager mInputManager;

    private boolean mBlockInput;

    public GameScreen(Map map, Camera camera, InputManager im) {
        mMap = map;
        mUnit = new DummyUnit(0, 0, 5, UnitData.SWORDMASTER_F);
        mUnit.add(this);
        mUnit.addMoveFinishedListener(this);

        mInputManager = im;

        map.add(mUnit);
        map.load();

        mSelectionCursor = new MapSelector(0, 0, map);

        mSelectionRoutine = new UnitSelectionRoutine(mSelectionCursor, map);

        camera.ensureInViewport(mSelectionCursor);
    }

    @Override
    public void setController(final GameController gc) {
        mController = gc;
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mMap.render(g);

        if (mMovementRange != null) {
            mMovementRange.render(g);

        }

        if (mMovementIndicator != null) {
            mMovementIndicator.render(g);
        }

        mUnit.render(g);
        mSelectionCursor.render(g);

        if (mUnitDisplayPlaceholder != null) {
            mUnitDisplayPlaceholder.render(g);
        }

        if (mCurrentActionWindow != null) {
            mCurrentActionWindow.render(g);
        }
    }

    @Override
    public EScreen getIdentifier() {
        return EScreen.GAME_SCREEN;

    }

    @Override
    public boolean renderLower() {
        return false;
    }

    @Override
    public boolean updateLower() {
        return false;
    }

    @Override
    public ECameraApplication cameraApplied() {
        return ECameraApplication.DYNAMIC;

    }

    @Override
    public void update(TimeSnapshot ts) {
        int tempX = mSelectionCursor.getX();
        int tempY = mSelectionCursor.getY();

        mSelectionCursor.setX((int) (mSelectionCursor.getX() + getMovement(EAxis.X) * Tile.WIDTH));
        mSelectionCursor.setY((int) (mSelectionCursor.getY() + getMovement(EAxis.Y) * Tile.HEIGHT));

        mSelectionCursor.update(ts);

        if (tempX != mSelectionCursor.getX() || tempY != mSelectionCursor.getY()) {
            this.cursorMoved();

        }
        mSelectionRoutine.update(ts);

        mUnit.update(ts);

        if (isActiveInitially(EAction.CONFIRMATION)) {
            confirm();

        }
        if (isActiveInitially(EAction.CANCEL)) {
            cancel();
        }

        if (mCurrentActionWindow != null) {
            mCurrentActionWindow.update(ts);
        }

        this.cycleFinished();
    }

    private void cancel() {
        mController.getActiveBehavior().onCancel();
    }

    private void cursorMoved() {
        mController.getActiveBehavior().onCursorMove(mSelectionCursor);

    }

    private void confirm() {
        if (mSelectionRoutine.getSelected().isPresent()) {
            mController.getActiveBehavior().onUnitConfirm(mSelectionRoutine.getSelected().get());

        } else {
            mController.getActiveBehavior().onConfirm();
        }
    }

    @Override
    public void displayUnitWindow(IUnit unit) {
        mUnitDisplayPlaceholder = new ImageElement(unit.getX() + unit.getWidth(), unit.getY() - unit.getHeight(),
                50 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR, false, EResource.MENU_BUTTON);
    }

    @Override
    public void removeUnitWindow(IUnit unit) {
        mUnitDisplayPlaceholder = null;
    }

    @Override
    public void onSelect(ISelectable selectable) {
        if (selectable instanceof IUnit) {
            mSelectionCursor.stop();
            mController.getActiveBehavior().onUnitSelect((IUnit) selectable);

        }
    }

    @Override
    public void onDeSelect(ISelectable selectable) {
        if (selectable instanceof IUnit) {
            mSelectionCursor.start();
            mController.getActiveBehavior().onUnitDeselect((IUnit) selectable);
        }

    }

    @Override
    public void onMoveFinished(ICanMove mover) {
        if (mover instanceof IUnit) {
            mController.getActiveBehavior().onMovementFinished((IUnit) mover);
        }

    }

    @Override
    public void displayUnitMovementRange(MovementRange range) {
        mMovementRange = range;
    }

    @Override
    public void removeUnitMovementRange() {
        mMovementRange = null;

    }

    @Override
    public void onMoveStarted(ICanMove mover) {

    }

    @Override
    public void displayMovementIndicator(MovementIndicator indicator) {
        mMovementIndicator = indicator;

    }

    @Override
    public void removeMovementIndicator() {
        mMovementIndicator = null;

    }

    @Override
    public void displayActionWindow(EActionWindowOption... options) {
        mCurrentActionWindow = new ActionWindow(options);
        mCurrentActionWindow.registerTo(mInputManager);
        mCurrentActionWindow.addCancelListener(this);
        this.block();

    }

    @Override
    public void onCancel(ICancelable cancelable) {
        if (cancelable instanceof ActionWindowButton) {
            mController.getActiveBehavior().onActionWindowCanceled();
        }
    }

    @Override
    public void removeActionWindow() {
        this.mCurrentActionWindow = null;
        this.unblock();

    }
}
