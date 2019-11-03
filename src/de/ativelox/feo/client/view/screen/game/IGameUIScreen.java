package de.ativelox.feo.client.view.screen.game;

import java.util.Collection;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.EActionWindowOption;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.IWeapon;
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

    public void displayTileStatus(Tile tile);

    public void removeTileStatus();

    public void removeActionWindow();

    public void switchSides(ESide side);

    public void displayWeaponSelection(IUnit unit, Collection<IWeapon> eligible);

    public void removeWeaponSelection();

    public void initializeBattlePreview(Collection<IUnit> targets);

    public void removeBattlePreview();

    public void switchBattlePreview(IUnit attacker, IUnit target);

}
