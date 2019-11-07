package de.ativelox.feo.client.model.unit;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;
import de.ativelox.feo.client.model.property.EBattleAnimType;
import de.ativelox.feo.client.model.property.ESide;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class BattleAnimationMapper {

    private BattleAnimationMapper() {

    }

    public static IAnimation get(IUnit unit, EBattleAnimType type, ESide side, IAnimation... insertOnHit) {
        IAnimation temp = Assets.getFor(EResource.BATTLE_ANIMATION, unit.getCurrentClass().toString(),
                unit.getGender().toString(), type.toString(), side.toString());

        UnitHookInserter.insert(temp, unit.getCurrentClass(), type);
        UnitHookInserter.insert(temp, unit.getCurrentClass(), type, insertOnHit);

        return temp;

    }
}
