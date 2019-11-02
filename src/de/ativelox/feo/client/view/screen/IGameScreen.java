package de.ativelox.feo.client.view.screen;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.property.EActionWindowOption;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.view.element.game.MovementIndicator;
import de.ativelox.feo.client.view.element.game.MovementRange;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IGameScreen extends IScreen, IActionListener, IMovementListener {

    public void setController(final GameController gc);

    public void displayUnitWindow(IUnit unit);

    public void removeUnitWindow(IUnit unit);

    public void displayUnitMovementRange(MovementRange range);

    public void removeUnitMovementRange();

    public void displayMovementIndicator(MovementIndicator indicator);

    public void removeMovementIndicator();

    public void displayActionWindow(EActionWindowOption... options);

    public void removeActionWindow();

}
