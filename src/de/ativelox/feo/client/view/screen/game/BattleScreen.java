package de.ativelox.feo.client.view.screen.game;

import java.awt.Color;
import java.awt.Image;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.camera.ECameraApplication;
import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.tile.ETileType;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.screen.EScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class BattleScreen implements IBattleScreen, IRequireResources {

    private static final Color BACKGROUND_FILTER = new Color(0, 0, 0, 150);

    private GameController mController;

    private Image mBattleBackground;

    public BattleScreen() {
        this.load();
    }

    @Override
    public void setController(GameController gc) {
        mController = gc;

    }

    @Override
    public EScreen getIdentifier() {
        return EScreen.GAME_SCREEN;
    }

    @Override
    public boolean renderLower() {
        return true;
    }

    @Override
    public boolean updateLower() {
        return false;
    }

    @Override
    public ECameraApplication cameraApplied() {
        return ECameraApplication.WINDOW_RESIZE_ONLY;
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        g.fillRect(BACKGROUND_FILTER, 0, 0, Display.WIDTH, Display.HEIGHT);
//        g.drawImage(mTestGround, 32 * Display.INTERNAL_RES_FACTOR, 85 * Display.INTERNAL_RES_FACTOR,
//                mTestGround.getWidth(null) * Display.INTERNAL_RES_FACTOR,
//                mTestGround.getHeight(null) * Display.INTERNAL_RES_FACTOR);
        g.drawImage(mBattleBackground, 0, -30, Display.WIDTH, Display.HEIGHT);

    }

    @Override
    public void update(TimeSnapshot ts) {

    }

    @Override
    public void load() {
        mBattleBackground = Assets.getFor(EResource.BATTLE_BACKGROUND);

    }

    @Override
    public void setParticipants(IUnit attacker, IUnit target) {

    }

}
