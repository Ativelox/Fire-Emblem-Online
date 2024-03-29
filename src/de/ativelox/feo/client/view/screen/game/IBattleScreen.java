package de.ativelox.feo.client.view.screen.game;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.gfx.tile.ETileType;
import de.ativelox.feo.client.model.manager.IBattleManager;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.Range;
import de.ativelox.feo.client.view.screen.IScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IBattleScreen extends IScreen {

    void setController(final GameController gc);

    void setParticipants(IUnit attacker, IUnit target, ETileType type, int range);

    void onBattleFinished();
    
    IBattleManager getManager();

}
