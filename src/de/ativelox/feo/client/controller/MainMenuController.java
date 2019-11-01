package de.ativelox.feo.client.controller;

import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.view.screen.EScreen;
import de.ativelox.feo.client.view.screen.IScreenManager;
import de.ativelox.feo.client.view.screen.menu.MainMenuScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MainMenuController {

    private final IScreenManager mManager;

    public MainMenuController(IScreenManager manager, InputManager im, MainMenuScreen screen) {
        mManager = manager;
        
        screen.setController(this);

        im.register(screen.getMovementListener());
        im.register(screen.getActionListener());

    }

    public void onNewGameButtonPressed() {
        mManager.switchTo(EScreen.GAME_SCREEN);

    }
    
    public void onMapEditorButtonPressed() {
        mManager.switchTo(EScreen.MAP_EDITOR_SCREEN);

    }
}
