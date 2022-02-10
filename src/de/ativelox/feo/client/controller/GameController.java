package de.ativelox.feo.client.controller;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import de.ativelox.feo.client.controller.behavior.IBehavior;
import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.model.camera.Camera;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.property.EActionWindowOption;
import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.model.sound.EMusic;
import de.ativelox.feo.client.model.sound.SoundPlayer;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.item.IItem;
import de.ativelox.feo.client.model.unit.item.Inventory;
import de.ativelox.feo.client.model.unit.item.weapon.IWeapon;
import de.ativelox.feo.client.model.util.closy.ManhattanDistance;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.game.MovementIndicator;
import de.ativelox.feo.client.view.element.game.MovementRange;
import de.ativelox.feo.client.view.screen.IScreenManager;
import de.ativelox.feo.client.view.screen.ScreenFactory;
import de.ativelox.feo.client.view.screen.game.IBattleScreen;
import de.ativelox.feo.client.view.screen.game.IGameScreen;
import de.ativelox.feo.client.view.screen.game.IGameUIScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class GameController {

    private final InputManager mInputManager;

    private final IGameScreen mScreen;
    private final IGameUIScreen mUiScreen;
    private final IBattleScreen mBattleScreen;

    private final IScreenManager mScreenManager;

    private final IBehavior mInitialBehavior;
    private final IBehavior mOtherBehavior;

    private IBehavior mCurrentActiveBehavior;

    private final Map mMap;

    private final Camera mCamera;

    private EMusic mCurrentPiece;

    public GameController(final IScreenManager sm, final InputManager im, final Map map, final Camera camera,
	    final IGameScreen screen, final IGameUIScreen uiScreen, IBehavior initialBehavior,
	    IBehavior otherBehavior) {
	mInputManager = im;
	mScreen = screen;
	mUiScreen = uiScreen;
	mBattleScreen = ScreenFactory.createBattleScreen(im);
	mInitialBehavior = initialBehavior;
	mOtherBehavior = otherBehavior;
	mScreenManager = sm;

	mCurrentActiveBehavior = initialBehavior;

	System.out.println("Current active behavior: " + initialBehavior);

	mCamera = camera;
	mMap = map;

	initialBehavior.setController(this);
	otherBehavior.setController(this);
	mBattleScreen.setController(this);

	uiScreen.setController(this);
	screen.setController(this);

	im.register((IActionListener) screen);
	im.register((IMovementListener) screen);

	initialBehavior.onTurnStart();

    }

    public void focusOn(ISpatial object) {
	mCamera.ensureInViewport(object);
    }

    public void removeFocus() {
	mCamera.removeFollow();
    }

    public void blockNonUiInput() {
	mScreen.blockInput();
    }

    public void unBlockNonUiInput() {
	mScreen.unblockInput();
    }

    public void displayUnitWindow(IUnit unit) {
	mUiScreen.displayUnitWindow(unit);

    }

    public void removeUnitWindow(IUnit unit) {
	mUiScreen.removeUnitWindow(unit);

    }

    public void turnEnd(IBehavior behavior) {
	if (behavior.equals(mInitialBehavior)) {
	    mOtherBehavior.onTurnStart();

	} else {
	    mInitialBehavior.onTurnStart();

	}
    }

    public void turnStart(IBehavior behavior) {
	mCurrentActiveBehavior = behavior;

	if (behavior.getAffiliation() == EAffiliation.ALLIED) {
	    mCurrentPiece = EMusic.BEYOND_THE_SKY;
	    SoundPlayer.get().play(EMusic.BEYOND_THE_SKY);

	} else {
	    mCurrentPiece = EMusic.AT_THE_FINAL_DRAGON;
	    SoundPlayer.get().play(EMusic.AT_THE_FINAL_DRAGON);

	}
	mMap.getAlliedUnits().forEach(u -> u.ready());
	mMap.getOpposedUnits().forEach(u -> u.ready());

    }

    public IBehavior getActiveBehavior() {
	return mCurrentActiveBehavior;
    }

    public void displayMovementRange(MovementRange range) {
	mScreen.displayUnitMovementRange(range);
    }

    public void removeMovementRange() {
	mScreen.removeUnitMovementRange();
    }

    public void displayMovementIndicator(MovementIndicator indicator) {
	mScreen.displayMovementIndicator(indicator);
    }

    public void removeMovementIndicator() {
	mScreen.removeMovementIndicator();
    }

    public void displayActionWindow(IUnit unit) {
	List<EActionWindowOption> temp = new ArrayList<>();

	if (unit.getInventory().getWeapons().length > 0 && mMap.opponentInRange(unit, unit.getMaxRange())) {
	    temp.add(EActionWindowOption.ATTACK);
	}
	if (mMap.allyInRange(unit, 1)) {
	    temp.add(EActionWindowOption.TRADE);
	}
	temp.add(EActionWindowOption.ITEM);
	temp.add(EActionWindowOption.WAIT);

	EActionWindowOption[] result = new EActionWindowOption[temp.size()];

	for (int i = 0; i < temp.size(); i++) {
	    result[i] = temp.get(i);

	}
	mUiScreen.displayUnitActionWindow(result);

    }

    public void removeActionWindow() {
	mUiScreen.removeActionWindow();
    }

    public void displaySystemActionWindow() {
	mUiScreen.displaySystemActionWindow(EActionWindowOption.END_TURN);
    }

    public InputManager getInputManager() {
	return mInputManager;
    }

    public void displayTileStatus(int x, int y) {
	mUiScreen.displayTileStatus(mMap.getByPos(x, y));
    }

    public void cursorMoved(ISpatial cursor) {
	Point2D point = mCamera.getTransform(mScreen.cameraApplied()).transform(new Point(cursor.getX(), cursor.getY()),
		null);

	if (point.getX() > Display.WIDTH / 2) {
	    mUiScreen.switchSides(ESide.LEFT);

	} else {
	    mUiScreen.switchSides(ESide.RIGHT);

	}
    }

    public void showWeaponSelection(IUnit unit) {
	Inventory inv = unit.getInventory();
	Collection<IWeapon> sorted = inv.getSorted(Comparator.comparing(w -> w.getRange().getMax()));
	Collection<IWeapon> eligible = new ArrayList<>();

	for (final IWeapon weapon : sorted) {
	    if (mMap.opponentInRange(unit, weapon.getRange().getMax())) {
		eligible.addAll(inv.getWeapons(w -> w.getRange().getMax() >= weapon.getRange().getMax()));
		break;

	    }
	}
	mUiScreen.displayWeaponSelection(unit, eligible);
    }

    public void removeWeaponSelect() {
	mUiScreen.removeWeaponSelection();

    }

    public void showTargetUnitSelect(IUnit unit, IWeapon weapon) {
	unit.equip(weapon);

	Collection<IUnit> targets = mMap.getOpponentsInRange(unit, weapon.getRange().getMax());
	mUiScreen.initializeBattlePreview(targets);

    }

    public void removeTargetSelect() {
	mScreen.removeTargetSelection();
	mUiScreen.removeBattlePreview();
    }

    public void switchTarget(IUnit attacker, IUnit target) {
	mScreen.moveTargetSelection(target);
	mUiScreen.switchBattlePreview(attacker, target);
    }

    public void initiateAttack(IUnit attacker, IUnit target) {
	mBattleScreen.setParticipants(attacker, target, mMap.getByPos(attacker.getX(), attacker.getY()).getType(),
		(int) new ManhattanDistance<>().distance(attacker, target));
	mScreenManager.addScreen(mBattleScreen);

    }

    public void attackFinished() {
	mScreenManager.removeScreen();
	mMap.getAlliedUnits().removeIf(u -> u.getCurrentHP() <= 0);
	mMap.getOpposedUnits().removeIf(u -> u.getCurrentHP() <= 0);

	SoundPlayer.get().play(mCurrentPiece);
    }

    public void moveCursor(Tile tile) {
	mScreen.moveCursor(tile);
    }

    public void showInventory(IUnit unit) {
	mUiScreen.displayInventory(unit);
    }

    public void removeInventory() {
	mUiScreen.removeInventory();

    }

    public void showItemUsageSelection(IItem item) {
	mUiScreen.displayItemUsageSelection(item);

    }

    public void removeItemUsageSelection() {
	mUiScreen.removeItemUsageSelection();

    }

    public void useItem(IItem item) {
	item.use();

    }

    public void discardItem(IItem item) {
	item.getOwner().getInventory().remove(item);
	mUiScreen.displayInventory(item.getOwner());

    }

    public void equipWeaon(IWeapon weapon) {
	weapon.getOwner().equip(weapon);

    }

    public void setSeed(long seed) {
	mBattleScreen.getManager().setSeed(seed);
    }
}
