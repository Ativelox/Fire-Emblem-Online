package de.ativelox.feo.client.model.manager;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class BattleManager implements IBattleManager {

    @Override
    public void attack(IUnit attacker, IUnit target) {
        // TODO fetch battle stats, roll random values to determine battle outcome,
        // select appropriate animations, also add appropriate hooks to animations.

    }

    @Override
    public void update(TimeSnapshot ts) {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        // TODO Auto-generated method stub

    }

}
