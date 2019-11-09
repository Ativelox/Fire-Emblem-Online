package de.ativelox.feo.client.model.gfx.animation;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.animation.hook.IHook;
import de.ativelox.feo.client.model.property.EBattleAnimType;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.unit.IUnit;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class BattleAnimationMapper {

    private BattleAnimationMapper() {

    }

    public static IAnimation get(IUnit unit, EBattleAnimType type, ESide side, IHook... insertOnHit) {
        IAnimation temp = Assets.getFor(EResource.BATTLE_ANIMATION, type, side, unit);

        UnitHookInserter.insert(temp, unit.getAnimationHookName(), type);

        UnitHookInserter.insert(temp, unit.getAnimationHookName(), type, insertOnHit);

        return temp;

    }
}
