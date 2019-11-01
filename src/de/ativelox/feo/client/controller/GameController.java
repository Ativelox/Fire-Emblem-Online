package de.ativelox.feo.client.controller;

import de.ativelox.feo.client.controller.behavior.IBehavior;
import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.view.screen.GameScreen;
import de.ativelox.feo.client.view.screen.IScreenManager;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class GameController {

    private final InputManager mInputManager;

    private final GameScreen mScreen;

    private final IBehavior mAlliedBehavior;

    public GameController(final IScreenManager sm, final InputManager im, final GameScreen screen,
            IBehavior alliedBehavior) {
        screen.setController(this);
        alliedBehavior.setController(this);

        mInputManager = im;
        mScreen = screen;
        mAlliedBehavior = alliedBehavior;

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

}
