package de.ativelox.feo.client.view.screen;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.controller.input.EAction;
import de.ativelox.feo.client.controller.input.EAxis;
import de.ativelox.feo.client.controller.input.InputReceiver;
import de.ativelox.feo.client.model.camera.ECameraApplication;
import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.property.ICanMove;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.ISelectable;
import de.ativelox.feo.client.model.property.callback.IMoveFinishedListener;
import de.ativelox.feo.client.model.property.callback.ISelectionListener;
import de.ativelox.feo.client.model.property.routine.UnitSelectionRoutine;
import de.ativelox.feo.client.model.unit.DummyUnit;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.UnitData;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.model.util.graph.GraphUtils;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.ImageElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class GameScreen extends InputReceiver
        implements IScreen, IRequireResources, ISelectionListener, IMoveFinishedListener {

    private final Map mMap;

    private final IUnit mUnit;

    private IAnimation mSelectionCursor;

    private GameController mController;

    private final UnitSelectionRoutine mSelectionRoutine;

    private ImageElement mUnitDisplayPlaceholder;

    private boolean mBlockCursorMovement;

    public GameScreen(Map map) {
        mMap = map;
        mUnit = new DummyUnit(4, 4, UnitData.SWORDMASTER_F);
        mUnit.add(this);
        mUnit.addMoveFinishedListener(this);

        map.add(mUnit);
        load();

        mSelectionRoutine = new UnitSelectionRoutine(mSelectionCursor, map);
    }

    public void setController(final GameController gc) {
        mController = gc;
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mMap.render(g);
        mUnit.render(g);
        mSelectionCursor.render(g);

        if (mUnitDisplayPlaceholder != null) {
            mUnitDisplayPlaceholder.render(g);
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
        return ECameraApplication.WINDOW_RESIZE_ONLY;

    }

    @Override
    public void update(TimeSnapshot ts) {
        if (!mBlockCursorMovement) {
            mSelectionCursor.setX((int) (mSelectionCursor.getX() + getMovement(EAxis.X) * Tile.WIDTH));
            mSelectionCursor.setY((int) (mSelectionCursor.getY() + getMovement(EAxis.Y) * Tile.HEIGHT));
        }

        mSelectionRoutine.update(ts);

        mUnit.update(ts);
        mSelectionCursor.update(ts);

        if (isActiveInitially(EAction.CONFIRMATION)) {
            mSelectionRoutine.getSelected().ifPresent(p -> confirmationPressedOn(p));

        }

        this.cycleFinished();
    }

    private void confirmationPressedOn(IUnit unit) {
        mSelectionCursor.hide();
        mBlockCursorMovement = true;
        mSelectionRoutine.block();
        mSelectionRoutine.deSelectInstantly();

        unit.move(GraphUtils.dijsktra(mMap.getInternalMap(), mMap.getByIndex(4, 4)).get(mMap.getByIndex(10, 8)));

    }

    private void movingCanceled(IUnit unit) {
        unit.selected();
        mSelectionCursor.show();
        mBlockCursorMovement = false;

    }

    @Override
    public void load() {
        mMap.load();
        mSelectionCursor = Assets.getFor(EResource.MAP_SELECTOR);

        mSelectionCursor.start();

    }

    public void displayUnitWindow(IUnit unit) {
        mUnitDisplayPlaceholder = new ImageElement(unit.getX() + unit.getWidth(), unit.getY() - unit.getHeight(),
                50 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR, false, EResource.MENU_BUTTON);
    }

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
        mBlockCursorMovement = false;
        mSelectionCursor.show();
        mSelectionRoutine.unblock();

    }
}
