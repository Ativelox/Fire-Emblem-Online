package de.ativelox.feo.client.controller.behavior;

import java.util.List;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.item.IItem;
import de.ativelox.feo.client.model.unit.item.weapon.IWeapon;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class BehaviorAdapter implements IBehavior {

    protected EAffiliation mAffiliation;
    protected List<IUnit> mUnits;
    protected GameController mController;
    protected Map mMap;

    protected boolean mIsOnTurn;

    public BehaviorAdapter(final Map map, EAffiliation affiliation) {
        mAffiliation = affiliation;

        mMap = map;

        mUnits = map.getUnitsBy(affiliation);

    }

    @Override
    public void setController(GameController controller) {
        mController = controller;

    }

    @Override
    public void onUnitSelect(IUnit unit) {

    }

    @Override
    public void onUnitDeselect(IUnit unit) {

    }

    @Override
    public void onUnitConfirm(IUnit unit) {

    }

    @Override
    public void onMovementFinished(IUnit unit) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onCursorMove(ISpatial cursor) {

    }

    @Override
    public void onActionWindowCanceled() {

    }

    @Override
    public void onSystemActionWindowCanceled() {

    }

    @Override
    public void onWeaponSelectCancel() {

    }

    @Override
    public void onTargetSelectCancel() {

    }

    @Override
    public void onConfirm() {

    }

    @Override
    public void onTurnStart() {
        mIsOnTurn = true;
        mController.turnStart(this);

    }

    @Override
    public void onTurnEnd() {
        mIsOnTurn = false;
        mController.turnEnd(this);

    }

    @Override
    public void onWaitAction() {

    }

    @Override
    public void onAttackAction() {

    }

    @Override
    public void onWeaponSelection(IWeapon weapon) {

    }

    @Override
    public void onTargetSelection(IUnit target) {

    }

    @Override
    public void onTargetSwitch(IUnit target) {

    }

    @Override
    public void onBattleFinished() {

    }

    @Override
    public EAffiliation getAffiliation() {
        return mAffiliation;

    }

    @Override
    public void onInventoryOpenAction() {

    }

    @Override
    public void onInventoryCancel() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onItemUsageSelection(IItem item) {

    }

    @Override
    public void onItemUsageSelectionCancel() {

    }

    @Override
    public void onItemUseAction(IItem item) {

    }

    @Override
    public void onItemDiscardAction(IItem item) {

    }

    @Override
    public void onWeaponEquipAction(IWeapon weapon) {

    }
}
