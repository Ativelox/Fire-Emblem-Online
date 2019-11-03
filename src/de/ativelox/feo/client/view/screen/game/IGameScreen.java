package de.ativelox.feo.client.view.screen.game;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.view.element.game.MovementIndicator;
import de.ativelox.feo.client.view.element.game.MovementRange;
import de.ativelox.feo.client.view.screen.IScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IGameScreen extends IScreen, IActionListener, IMovementListener {

    public void setController(final GameController gc);

    public void displayUnitMovementRange(MovementRange range);

    public void removeUnitMovementRange();

    public void displayMovementIndicator(MovementIndicator indicator);

    public void removeMovementIndicator();

    public void blockInput();

    public void unblockInput();

    public void moveTargetSelection(ISpatial spatial);

    public void removeTargetSelection();

}
