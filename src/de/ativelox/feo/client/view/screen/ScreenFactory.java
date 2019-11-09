package de.ativelox.feo.client.view.screen;

import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.view.screen.game.BattleScreen;
import de.ativelox.feo.client.view.screen.game.IBattleScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ScreenFactory {

    private ScreenFactory() {

    }

    public static IBattleScreen createBattleScreen(InputManager im) {
        return new BattleScreen(im);

    }
}
