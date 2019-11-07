package de.ativelox.feo.client.view.screen.menu;

import de.ativelox.feo.client.controller.MainMenuController;
import de.ativelox.feo.client.model.camera.ECameraApplication;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.manager.ConfirmWhenSelectedManager;
import de.ativelox.feo.client.model.manager.VerticalSelectionManager;
import de.ativelox.feo.client.model.property.IConfirmable;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.IConfirmListener;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.model.sound.ESoundEffect;
import de.ativelox.feo.client.model.sound.SoundPlayer;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.element.generic.AButtonElement;
import de.ativelox.feo.client.view.element.generic.ImageElement;
import de.ativelox.feo.client.view.element.menu.MainMenuButton;
import de.ativelox.feo.client.view.screen.EScreen;
import de.ativelox.feo.client.view.screen.IScreen;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MainMenuScreen implements IScreen, IConfirmListener {

    private final ImageElement mBackground;
    private final AButtonElement[] mButtons;

    private final VerticalSelectionManager<AButtonElement> mSelectionManager;
    private final ConfirmWhenSelectedManager<AButtonElement> mButtonConfirmManager;

    private static final String NEW_GAME = "New Game";
    private static final String MAP_EDITOR = "Map Editor";
    private static final String ONLINE = "Online";
    private static final String SETTING = "Settings";

    private MainMenuController mController;

    public MainMenuScreen() {
        mButtons = new MainMenuButton[4];
        mButtons[0] = new MainMenuButton(25, 10, 50, 20, true, EResource.MENU_BUTTON, 0, NEW_GAME);
        mButtons[1] = new MainMenuButton(25, 30, 50, 20, true, EResource.MENU_BUTTON, 1, MAP_EDITOR);
        mButtons[2] = new MainMenuButton(25, 50, 50, 20, true, EResource.MENU_BUTTON, 2, ONLINE);
        mButtons[3] = new MainMenuButton(25, 70, 50, 20, true, EResource.MENU_BUTTON, 3, SETTING);

        mBackground = new ImageElement(0, 0, 100, 100, true, EResource.MENU_BACKGROUND);

        mSelectionManager = new VerticalSelectionManager<>(false, mButtons);
        mButtonConfirmManager = new ConfirmWhenSelectedManager<>(mButtons);

        mButtons[0].add(this);
        mButtons[1].add(this);
        mButtons[2].add(this);
        mButtons[3].add(this);
    }

    public void setController(final MainMenuController controller) {
        mController = controller;
    }

    public IMovementListener getMovementListener() {
        return mSelectionManager;
    }

    public IActionListener getActionListener() {
        return mButtonConfirmManager;
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mBackground.render(g);
        for (AButtonElement e : mButtons) {
            e.render(g);
        }

    }

    @Override
    public void update(TimeSnapshot ts) {
        mSelectionManager.update(ts);
        mButtonConfirmManager.update(ts);
    }

    @Override
    public EScreen getIdentifier() {
        return EScreen.MAIN_MENU_SCREEN;
    }

    @Override
    public boolean renderLower() {
        return true;
    }

    @Override
    public boolean updateLower() {
        return true;
    }

    @Override
    public ECameraApplication cameraApplied() {
        return ECameraApplication.WINDOW_RESIZE_ONLY;
    }

    @Override
    public void onConfirm(IConfirmable confirmable) {
        if (confirmable instanceof AButtonElement) {
            SoundPlayer.get().play(ESoundEffect.WINDOW_ACCEPT);

            switch (((AButtonElement) confirmable).getText()) {
            case NEW_GAME:
                mController.onNewGameButtonPressed();
                break;

            case MAP_EDITOR:
                mController.onMapEditorButtonPressed();
                break;

            case ONLINE:
                break;
            case SETTING:
                break;
            default:
                Logger.get().log(ELogType.ERROR,
                        "Couldn't parse the following button: " + ((AButtonElement) confirmable).getText());
            }
        }
    }
}
