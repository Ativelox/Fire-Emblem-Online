package de.ativelox.feo.client.model.manager;

import de.ativelox.feo.client.model.gfx.tile.ETileType;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.Range;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IBattleManager extends IUpdateable, IRenderable {

    void attack(IUnit attacker, IUnit target, ETileType type, int range);

    void setSeed(long seed);

}
