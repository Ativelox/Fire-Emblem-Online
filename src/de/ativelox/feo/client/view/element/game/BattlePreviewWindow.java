package de.ativelox.feo.client.view.element.game;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.property.ICanSwitchSides;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.CombatRule;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.ImageElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class BattlePreviewWindow extends ImageElement implements ICanSwitchSides, IRequireResources {

    private IUnit mAttacker;
    private IUnit mTarget;

    private boolean mIsHidden;

    private Image mHpIdentifier;
    private Image mMtIdentifier;
    private Image mHitIdentifier;
    private Image mCritIdentifier;

    private Image mHpAttacker;
    private Image mMtAttacker;
    private Image mHitAttacker;
    private Image mCritAttacker;

    private Image mHpTarget;
    private Image mMtTarget;
    private Image mHitTarget;
    private Image mCritTarget;

    private Image mNameAttacker;
    private Image mNameTarget;

    private Image mAttackCountAttacker;
    private Image mAttackCountTarget;

    private Image mWeaponNameTarget;

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param isPercent
     * @param image
     */
    public BattlePreviewWindow() {
        super(3 * Display.INTERNAL_RES_FACTOR, 3 * Display.INTERNAL_RES_FACTOR,
                (int) (73 * Display.INTERNAL_RES_FACTOR), (int) (121 * Display.INTERNAL_RES_FACTOR), false,
                EResource.BATTLE_PREVIEW);
    }

    public void switchParticipants(final IUnit attacker, final IUnit target) {
        mAttacker = attacker;
        mTarget = target;

        mAttackCountAttacker = null;
        mAttackCountTarget = null;

        this.load();

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (mIsHidden) {
            return;
        }
        super.render(g);

        g.drawImage(mNameAttacker,
                getX() + 24 * Display.INTERNAL_RES_FACTOR
                        + (20 * Display.INTERNAL_RES_FACTOR - mNameAttacker.getWidth(null) / 2),
                getY() + 5 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mNameTarget,
                getX() + 9 * Display.INTERNAL_RES_FACTOR
                        + (20 * Display.INTERNAL_RES_FACTOR - mNameTarget.getWidth(null) / 2),
                getY() + 85 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mWeaponNameTarget,
                getX() + 9 * Display.INTERNAL_RES_FACTOR
                        + (23 * Display.INTERNAL_RES_FACTOR - mWeaponNameTarget.getWidth(null) / 2),
                getY() + 101 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mHpAttacker, 16 * Display.INTERNAL_RES_FACTOR - mHpAttacker.getWidth(null) + getX()
                + 51 * Display.INTERNAL_RES_FACTOR, getY() + 23 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mHpIdentifier, getX() + 31 * Display.INTERNAL_RES_FACTOR,
                getY() + 23 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mHpTarget,
                16 * Display.INTERNAL_RES_FACTOR - mHpTarget.getWidth(null) + getX() + 5 * Display.INTERNAL_RES_FACTOR,
                getY() + 23 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mMtTarget,
                16 * Display.INTERNAL_RES_FACTOR - mMtTarget.getWidth(null) + getX() + 5 * Display.INTERNAL_RES_FACTOR,
                getY() + 39 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mMtIdentifier, getX() + 30 * Display.INTERNAL_RES_FACTOR,
                getY() + 39 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mMtAttacker, 16 * Display.INTERNAL_RES_FACTOR - mMtAttacker.getWidth(null) + getX()
                + 51 * Display.INTERNAL_RES_FACTOR, getY() + 39 * Display.INTERNAL_RES_FACTOR);

        if (mHitTarget.getWidth(null) >= 16 * Display.INTERNAL_RES_FACTOR) {
            g.drawImage(mHitTarget, getX() + 5 * Display.INTERNAL_RES_FACTOR, getY() + 55 * Display.INTERNAL_RES_FACTOR,
                    16 * Display.INTERNAL_RES_FACTOR, mHitTarget.getHeight(null));
        } else {
            g.drawImage(mHitTarget, 16 * Display.INTERNAL_RES_FACTOR - mHitAttacker.getWidth(null) + getX()
                    + 5 * Display.INTERNAL_RES_FACTOR, getY() + 55 * Display.INTERNAL_RES_FACTOR);
        }

        g.drawImage(mHitIdentifier, getX() + 29 * Display.INTERNAL_RES_FACTOR,
                getY() + 55 * Display.INTERNAL_RES_FACTOR);

        if (mHitAttacker.getWidth(null) >= 16 * Display.INTERNAL_RES_FACTOR) {
            g.drawImage(mHitAttacker, getX() + 51 * Display.INTERNAL_RES_FACTOR,
                    getY() + 55 * Display.INTERNAL_RES_FACTOR, 16 * Display.INTERNAL_RES_FACTOR,
                    mHitAttacker.getHeight(null));
        } else {
            g.drawImage(mHitAttacker, 16 * Display.INTERNAL_RES_FACTOR - mHitAttacker.getWidth(null) + getX()
                    + 51 * Display.INTERNAL_RES_FACTOR, getY() + 55 * Display.INTERNAL_RES_FACTOR);
        }

        g.drawImage(mCritTarget, 16 * Display.INTERNAL_RES_FACTOR - mCritTarget.getWidth(null) + getX()
                + 5 * Display.INTERNAL_RES_FACTOR, getY() + 71 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mCritIdentifier, getX() + 29 * Display.INTERNAL_RES_FACTOR,
                getY() + 71 * Display.INTERNAL_RES_FACTOR, 15 * Display.INTERNAL_RES_FACTOR,
                mCritIdentifier.getHeight(null));

        g.drawImage(mCritAttacker, 16 * Display.INTERNAL_RES_FACTOR - mCritAttacker.getWidth(null) + getX()
                + 51 * Display.INTERNAL_RES_FACTOR, getY() + 71 * Display.INTERNAL_RES_FACTOR);

        if (mAttackCountAttacker != null) {
            g.drawImage(mAttackCountAttacker, getX() + 42 * Display.INTERNAL_RES_FACTOR,
                    getY() + 39 * Display.INTERNAL_RES_FACTOR);
        }

        if (mAttackCountTarget != null) {
            g.drawImage(mAttackCountAttacker, getX() + -4 * Display.INTERNAL_RES_FACTOR,
                    getY() + 39 * Display.INTERNAL_RES_FACTOR);

        }

    }

    @Override
    public void switchTo(ESide side) {
        switch (side) {
        case LEFT:
            setX(3 * Display.INTERNAL_RES_FACTOR);
            setY(3 * Display.INTERNAL_RES_FACTOR);
            break;
        case RIGHT:
            setX(Display.WIDTH - getWidth() - 10 * Display.INTERNAL_RES_FACTOR);
            setY(3 * Display.INTERNAL_RES_FACTOR);
            break;
        default:
            break;

        }

    }

    public void show() {
        mIsHidden = false;
    }

    public void hide() {
        mIsHidden = true;
    }

    @Override
    public void load() {
        mNameAttacker = Assets.getFor(EResource.REGULAR_FONT, mAttacker.getName());
        mNameTarget = Assets.getFor(EResource.REGULAR_FONT, mTarget.getName());

        mHpIdentifier = Assets.getFor(EResource.REGULAR_FONT, "HP");
        mMtIdentifier = Assets.getFor(EResource.REGULAR_FONT, "Mt");
        mHitIdentifier = Assets.getFor(EResource.REGULAR_FONT, "Hit");
        mCritIdentifier = Assets.getFor(EResource.REGULAR_FONT, "Crit");

        mHpAttacker = Assets.getFor(EResource.REGULAR_FONT, mAttacker.getCurrentHP() + "");
        mHpTarget = Assets.getFor(EResource.REGULAR_FONT, mTarget.getCurrentHP() + "");

        mMtAttacker = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getMight(mAttacker, mTarget) + "");
        mMtTarget = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getMight(mTarget, mAttacker) + "");

        mHitAttacker = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getAccuracy(mAttacker, mTarget) + "");
        mHitTarget = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getAccuracy(mTarget, mAttacker) + "");

        mCritAttacker = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getCriticalChance(mAttacker, mTarget) + "");
        mCritTarget = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getCriticalChance(mTarget, mAttacker) + "");

        if (CombatRule.hasRepeatedAttack(mAttacker, mTarget)) {
            mAttackCountAttacker = Assets.getFor(EResource.REGULAR_FONT, "2x");

        }

        if (CombatRule.hasRepeatedAttack(mTarget, mAttacker)) {
            mAttackCountTarget = Assets.getFor(EResource.REGULAR_FONT, "2x");

        }

        String weaponName = "--";

        if (mTarget.getEquippedWeapon().isPresent()) {
            weaponName = mTarget.getEquippedWeapon().get().getName();
        }

        mWeaponNameTarget = Assets.getFor(EResource.REGULAR_FONT, weaponName);
    }
}
