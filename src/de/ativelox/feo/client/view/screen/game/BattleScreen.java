package de.ativelox.feo.client.view.screen.game;

import java.awt.Color;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.model.camera.ECameraApplication;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.tile.ETileType;
import de.ativelox.feo.client.model.manager.BattleManager;
import de.ativelox.feo.client.model.manager.IBattleManager;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.Range;
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
    private final BattleManager mBattleManager;

    public BattleScreen(final InputManager im) {
	mBattleOverlay = new BattleOverlay();
	mBattleManager = new BattleManager(this, im);

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
	g.fillRect(BACKGROUND_FILTER, 0, 0, Display.WIDTH, Display.HEIGHT, -2);
	mBattleOverlay.render(g);
	mBattleManager.render(g);

    }

    @Override
    public void update(TimeSnapshot ts) {
	mBattleManager.update(ts);

    }

    @Override
    public void setParticipants(IUnit attacker, IUnit target, ETileType type, int range) {
	mBattleOverlay.setParticipants(attacker, target);
	mBattleManager.attack(attacker, target, type, range);

    }

    @Override
    public void onBattleFinished() {
	mController.getActiveBehavior().onBattleFinished();

    }

    @Override
    public IBattleManager getManager() {
	return mBattleManager;
    }

}
