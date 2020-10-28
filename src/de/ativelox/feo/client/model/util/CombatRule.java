package de.ativelox.feo.client.model.util;

import de.ativelox.feo.client.model.property.EDamageType;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.item.weapon.IWeapon;
import de.ativelox.feo.client.model.util.closy.ManhattanDistance;
import de.ativelox.feo.util.MathUtils;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class CombatRule {

    private CombatRule() {

    }

    /**
     * Returns <tt>true</tt> if <tt>target</tt> can counter attack. Cases where this
     * might not be possible is if the <tt>attacker</tt> attacks ranged and
     * <tt>target</tt> is melee, or vice versa.
     * 
     * @param attacker
     * @param target
     * @return
     */
    public static boolean canCounterattack(final IUnit attacker, final IUnit target) {
	if (target.getEquippedWeapon().isEmpty()) {
	    return false;
	}
	
	

	return target.getEquippedWeapon().get().getRange().intersect(attacker.getEquippedWeapon().get().getRange())
		.isPresent();

    }

    public static boolean hasRepeatedAttack(final IUnit attacker, final IUnit target) {
	return getAttackSpeed(attacker) - 4 >= getAttackSpeed(target);

    }

    private static int getCriticalEvade(final IUnit unit) {
	return unit.getLuck();
    }

    private static int getCriticalRate(final IUnit unit) {
	return (unit.getSkill() / 2) + unit.getEquippedWeapon().get().getCrit();

    }

    public static int getCriticalChance(final IUnit attacker, final IUnit target) {
	if (!attacker.getEquippedWeapon().isPresent()) {
	    return 0;
	}

	return MathUtils.clamp(100, getCriticalRate(attacker) - getCriticalEvade(target), 0);

    }

    private static int getAttack(IUnit unit) {
	// TODO add weapon triangle and effectivness etc.
	return unit.getStr() + unit.getEquippedWeapon().get().getMight();
    }

    public static int getMight(final IUnit attacker, final IUnit target) {
	if (!attacker.getEquippedWeapon().isPresent()) {
	    return 0;
	}
	IWeapon weapon = attacker.getEquippedWeapon().get();

	int defense = target.getDef();

	if (weapon.getDamageType() == EDamageType.MAGIC) {
	    defense = target.getRes();
	}

	return Math.max(getAttack(attacker) - defense, 0);

    }

    private static int getHitRate(IUnit unit) {
	if (unit.getEquippedWeapon().isEmpty()) {
	    return unit.getSkill() * 2 + unit.getLuck() / 2;
	}

	return unit.getEquippedWeapon().get().getAccuracy() + unit.getSkill() * 2 + unit.getLuck() / 2;
    }

    private static int getAttackSpeed(IUnit unit) {
	if (unit.getEquippedWeapon().isEmpty()) {
	    return unit.getSpd();
	}

	return unit.getSpd() - unit.getEquippedWeapon().get().getWeight();

    }

    private static int getEvade(IUnit unit) {
	return getAttackSpeed(unit) * 2 + unit.getLuck();
    }

    public static int getAccuracy(IUnit attacker, IUnit target) {
	return MathUtils.clamp(100, getHitRate(attacker) - getEvade(target), 0);

    }
}
