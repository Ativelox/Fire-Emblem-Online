package de.ativelox.feo.client.view.element.game;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.AScreenElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class BattleOverlay extends AScreenElement {

    private IUnit mAttacker;

    private IUnit mTarget;

    public BattleOverlay() {
        super(0, 0, Display.WIDTH, Display.HEIGHT, false);

    }

    @Override
    public void render(DepthBufferedGraphics g) {

    }

    public void setParticipants(IUnit attacker, IUnit target) {
        mAttacker = attacker;
        mTarget = target;

    }
}
