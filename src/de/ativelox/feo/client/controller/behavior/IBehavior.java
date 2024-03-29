package de.ativelox.feo.client.controller.behavior;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.item.IItem;
import de.ativelox.feo.client.model.unit.item.weapon.IWeapon;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IBehavior {
    
    void setAffiliation(EAffiliation affiliation);

    void setController(GameController controller);

    void onUnitSelect(IUnit unit);

    void onUnitDeselect(IUnit unit);

    void onUnitConfirm(IUnit unit);

    void onMovementFinished(IUnit unit);

    void onCancel();

    void onCursorMove(ISpatial cursor);

    void onActionWindowCanceled();

    void onSystemActionWindowCanceled();

    void onWeaponSelectCancel();

    void onTargetSelectCancel();

    void onConfirm();

    void onTurnStart();

    void onTurnEnd();

    void onWaitAction();

    void onAttackAction();

    void onWeaponSelection(IWeapon weapon);

    void onTargetSelection(IUnit target);

    void onTargetSwitch(IUnit target);

    void onBattleFinished();

    void onInventoryOpenAction();

    void onInventoryCancel();

    void onItemUsageSelection(IItem item);

    void onItemUsageSelectionCancel();

    EAffiliation getAffiliation();

    void onItemUseAction(IItem item);

    void onItemDiscardAction(IItem item);

    void onWeaponEquipAction(IWeapon weapon);

    void beforeAttack(IUnit target);
    
    void beforeTurnEnd();
    
    void beforeWait();

}
