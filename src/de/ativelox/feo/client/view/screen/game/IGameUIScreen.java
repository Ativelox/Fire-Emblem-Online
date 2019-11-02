package de.ativelox.feo.client.view.screen.game;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.property.EActionWindowOption;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.view.screen.IScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IGameUIScreen extends IScreen {

    public void setController(final GameController gc);

    public void displayUnitWindow(IUnit unit);

    public void removeUnitWindow(IUnit unit);

    public void displayUnitActionWindow(EActionWindowOption... options);

    public void displaySystemActionWindow(EActionWindowOption... options);

    public void removeActionWindow();

}
