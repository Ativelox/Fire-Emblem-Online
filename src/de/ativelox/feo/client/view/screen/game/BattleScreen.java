package de.ativelox.feo.client.view.screen.game;

import java.awt.Color;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.camera.ECameraApplication;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.tile.ETileType;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.game.BattleOverlay;
import de.ativelox.feo.client.view.screen.EScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class BattleScreen implements IBattleScreen {

    private static final Color BACKGROUND_FILTER = new Color(0, 0, 0, 150);

    private GameController mController;

    private final BattleOverlay mBattleOverlay;

    public BattleScreen() {
        mBattleOverlay = new BattleOverlay();

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
        mBattleOverlay.render(g);

    }

    @Override
    public void update(TimeSnapshot ts) {

    }

    @Override
    public void setParticipants(IUnit attacker, IUnit target, ETileType type, int range) {
        mBattleOverlay.setParticipants(attacker, target);

    }

}
