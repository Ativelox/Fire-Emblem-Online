package de.ativelox.feo.client.controller.behavior;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.unit.IUnit;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DefaultPlayerBehavior implements IBehavior {

    private GameController mController;

    public DefaultPlayerBehavior() {

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

}
