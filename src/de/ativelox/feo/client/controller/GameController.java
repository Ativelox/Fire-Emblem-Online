package de.ativelox.feo.client.controller;

import de.ativelox.feo.client.controller.behavior.IBehavior;
import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.property.EActionWindowOption;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.view.element.game.MovementIndicator;
import de.ativelox.feo.client.view.element.game.MovementRange;
import de.ativelox.feo.client.view.screen.IGameScreen;
import de.ativelox.feo.client.view.screen.IScreenManager;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class GameController {

    private final InputManager mInputManager;

    private final IGameScreen mScreen;

    private final IBehavior mAlliedBehavior;

    private final Map mMap;

    public GameController(final IScreenManager sm, final InputManager im, final Map map, final IGameScreen screen,
            IBehavior alliedBehavior) {
        screen.setController(this);
        alliedBehavior.setController(this);

        mInputManager = im;
        mScreen = screen;
        mAlliedBehavior = alliedBehavior;

        mMap = map;

        alliedBehavior.turnStart();

    }

    public void displayUnitWindow(IUnit unit) {
        mScreen.displayUnitWindow(unit);

    }

    public void removeUnitWindow(IUnit unit) {
        mScreen.removeUnitWindow(unit);

    }

    public void blockUserInput() {
        mInputManager.remove((IActionListener) mScreen);
        mInputManager.remove((IMovementListener) mScreen);

    }

    public void enableUserInput() {
        mInputManager.register((IActionListener) mScreen);
        mInputManager.register((IMovementListener) mScreen);
    }

    public IBehavior getActiveBehavior() {
        return mAlliedBehavior;
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

    public void displayActionWindow() {
        mScreen.displayActionWindow(EActionWindowOption.ATTACK, EActionWindowOption.TRADE, EActionWindowOption.WAIT);

    }

    public void removeActionWindow() {
        mScreen.removeActionWindow();
    }
}
