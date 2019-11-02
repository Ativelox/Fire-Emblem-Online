package de.ativelox.feo.client.view.screen;

import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.controller.MapEditorController;
import de.ativelox.feo.client.controller.behavior.DefaultPlayerBehavior;
import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.model.camera.Camera;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.screen.editor.IMapEditorScreen;
import de.ativelox.feo.client.view.screen.editor.IMapEditorUIScreen;
import de.ativelox.feo.client.view.screen.editor.MapEditorScreen;
import de.ativelox.feo.client.view.screen.editor.MapEditorUIScreen;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class CameraScreenManager implements IScreenManager {

    private int mScreenRemovalCounter;

    private final List<IScreen> mTempAdd;

    private final Deque<IScreen> mScreenStack;

    private final Camera mCamera;

    private final DepthBufferedGraphics mGraphics;

    private final InputManager mInputManager;

    private final Display mDisplay;

    public CameraScreenManager(final Camera camera, final InputManager im, final Display d) {
        mScreenStack = new ArrayDeque<>();
        mTempAdd = new ArrayList<>();

        mDisplay = d;
        mInputManager = im;

        mGraphics = new DepthBufferedGraphics();

        mCamera = camera;

    }

    @Override
    public void draw(Graphics2D g) {
        // TODO: place logic into update cycle
        final List<IScreen> tempList = new ArrayList<>(mScreenStack.size());

        // iterates from head to tail.
        for (final IScreen screen : mScreenStack) {
            tempList.add(screen);

            if (!screen.renderLower()) {
                break;
            }
        }

        Collections.reverse(tempList);
        for (final IScreen screen : tempList) {
            switch (screen.cameraApplied()) {
            case DYNAMIC:
                g.setTransform(mCamera.getAllTransform());
                break;
            case NONE:
                g.setTransform(Camera.IDENTITY);
                break;
            case WINDOW_RESIZE_ONLY:
                g.setTransform(mCamera.getResizeTransform());
                break;
            case USER_ZT:
                g.setTransform(mCamera.getCameraTransform());
                break;
            default:
                break;

            }
            screen.render(mGraphics);
        }
        mGraphics.draw(g);

    }

    @Override
    public void update(TimeSnapshot ts) {

        for (final IScreen screen : mScreenStack) {
            screen.update(ts);

            if (!screen.updateLower()) {
                break;
            }
        }

        // first remove, then add screens, this might need to be addressed in a better
        // fashion in the future.
        for (int i = 0; i < mScreenRemovalCounter; i++) {
            if (mScreenStack.isEmpty()) {
                break;
            }
            mScreenStack.remove();
        }

        for (IScreen screen : mTempAdd) {
            mScreenStack.addFirst(screen);
        }

        mTempAdd.clear();
        mScreenRemovalCounter = 0;
    }

    @Override
    public void addScreen(IScreen screen) {
        mTempAdd.add(screen);

    }

    @Override
    public void removeScreen() {
        mScreenRemovalCounter++;

    }

    @Override
    public void switchTo(EScreen screen) {
        Logger.get().log(ELogType.INFO, "Switching from: " + mScreenStack.getFirst().getIdentifier() + " to " + screen);

        switch (screen) {
        case MAP_EDITOR_SCREEN:
            IMapEditorScreen editorScreen = new MapEditorScreen();
            IMapEditorUIScreen editorUIScreen = new MapEditorUIScreen();
            new MapEditorController(this, mInputManager, mDisplay, editorScreen, editorUIScreen);
            this.addScreen(editorScreen);
            this.addScreen(editorUIScreen);

            break;
        case GAME_SCREEN:
            Map map = new Map("ch0.map", 0, 0);
            mCamera.setBounds(map);
            IGameScreen gameScreen = new GameScreen(map, mCamera, mInputManager);
            new GameController(this, mInputManager, map, gameScreen, new DefaultPlayerBehavior(map));
            this.addScreen(gameScreen);
            break;
        case MAIN_MENU_SCREEN:
            removeUntil(screen);
            break;
        default:
            break;

        }

    }

    private void removeUntil(EScreen screen) {
        switch (screen) {
        case MAIN_MENU_SCREEN:
            mScreenRemovalCounter = mScreenStack.size() - 1;

            break;
        case MAP_EDITOR_SCREEN:
            break;
        case GAME_SCREEN:
            break;
        default:
            break;

        }

    }

}
