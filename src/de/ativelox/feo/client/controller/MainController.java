package de.ativelox.feo.client.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.model.camera.Camera;
import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.property.IPriorityUpdateable;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.model.property.callback.IPanningListener;
import de.ativelox.feo.client.model.sound.EMusic;
import de.ativelox.feo.client.model.sound.SoundMapping;
import de.ativelox.feo.client.model.sound.SoundPlayer;
import de.ativelox.feo.client.model.unit.Palette;
import de.ativelox.feo.client.model.unit.item.weapon.WeaponFactory;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.screen.CameraScreenManager;
import de.ativelox.feo.client.view.screen.IScreenManager;
import de.ativelox.feo.client.view.screen.menu.MainMenuScreen;
import de.ativelox.feo.logging.Logger;

/**
 * 
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MainController implements Runnable {

    private final List<IPriorityUpdateable> mPriorities;

    private Display mDisplay;
    private Camera mCamera;
    private InputManager mInputManager;
    private BufferStrategy mBufferStrategy;
    private IScreenManager mScreenManager;

    private boolean mIsRunning;

    private static final int FPS = 60;

    private final int mSleepTime;

    public MainController() {
        mPriorities = new ArrayList<>();
        mIsRunning = true;

        mSleepTime = (int) (1000f / FPS);

    }

    public void initialize() {
        Assets.init();
        Palette.init();
        SoundMapping.init();
        WeaponFactory.init();

        SoundPlayer.get().play(EMusic.FIRE_EMBLEM_THEME);

        mDisplay = new Display("Fire Emblem Online", Display.WIDTH, Display.HEIGHT);
        mCamera = new Camera();
        mInputManager = new InputManager(mCamera);
        mBufferStrategy = mDisplay.getBufferStrategy();

        DisplayController dc = new DisplayController(mDisplay, this);
        dc.register(mCamera);

        mPriorities.add(mCamera);
        mPriorities.add(mInputManager);
        mPriorities.add(dc);
        mPriorities.add(SoundPlayer.get());

        // sort descending by priority
        mPriorities.sort(Comparator.comparing(p -> {
            return -p.getPriority();
        }));

        mScreenManager = new CameraScreenManager(mCamera, mInputManager, mDisplay);

        MainMenuScreen screen = new MainMenuScreen();
        new MainMenuController(mScreenManager, mInputManager, screen);
        mScreenManager.addScreen(screen);

        mDisplay.addKeyListener(mInputManager);
        mDisplay.addMouseMotionListener(mInputManager);
        mInputManager.register((IMovementListener) mCamera);
        mInputManager.register((IPanningListener) mCamera);

    }

    @Override
    public void run() {
        TimeSnapshot ts = new TimeSnapshot();
        Graphics2D g = null;

        while (mIsRunning) {
            mPriorities.forEach(c -> c.update(ts));
            mScreenManager.update(ts);

            g = (Graphics2D) mBufferStrategy.getDrawGraphics();
            g.clearRect(0, 0, mDisplay.getCurrentWidth(), mDisplay.getCurrentHeight());

            mScreenManager.draw(g);

            mBufferStrategy.show();
            g.dispose();

            ts.cycleFinished();

            try {
                Thread.sleep(mSleepTime);

            } catch (InterruptedException e) {
                Logger.get().logError(e);

            }
        }
        mDisplay.close();

    }

    public void stop() {
        mIsRunning = false;

    }
}
