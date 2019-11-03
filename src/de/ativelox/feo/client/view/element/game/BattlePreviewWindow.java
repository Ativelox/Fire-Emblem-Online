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

        this.load();

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (mIsHidden) {
            return;
        }
        super.render(g);

        g.drawImage(mNameAttacker, getX() + 25 * Display.INTERNAL_RES_FACTOR, getY() + 7 * Display.INTERNAL_RES_FACTOR,
                35 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mNameTarget, getX() + 9 * Display.INTERNAL_RES_FACTOR, getY() + 86 * Display.INTERNAL_RES_FACTOR,
                35 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);
        
        g.drawImage(mWeaponNameTarget, getX() + 9 * Display.INTERNAL_RES_FACTOR, getY() + 103 * Display.INTERNAL_RES_FACTOR,
                45 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mHpAttacker, getX() + 52 * Display.INTERNAL_RES_FACTOR, getY() + 25 * Display.INTERNAL_RES_FACTOR,
                15 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mHpIdentifier, getX() + 29 * Display.INTERNAL_RES_FACTOR, getY() + 25 * Display.INTERNAL_RES_FACTOR,
                15 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mHpTarget, getX() + 5 * Display.INTERNAL_RES_FACTOR, getY() + 25 * Display.INTERNAL_RES_FACTOR,
                15 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mMtTarget, getX() + 5 * Display.INTERNAL_RES_FACTOR, getY() + 41 * Display.INTERNAL_RES_FACTOR,
                15 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mMtIdentifier, getX() + 30 * Display.INTERNAL_RES_FACTOR, getY() + 41 * Display.INTERNAL_RES_FACTOR,
                15 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mMtAttacker, getX() + 52 * Display.INTERNAL_RES_FACTOR, getY() + 41 * Display.INTERNAL_RES_FACTOR,
                15 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mHitTarget, getX() + 5 * Display.INTERNAL_RES_FACTOR, getY() + 57 * Display.INTERNAL_RES_FACTOR,
                15 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mHitIdentifier, getX() + 29 * Display.INTERNAL_RES_FACTOR,
                getY() + 57 * Display.INTERNAL_RES_FACTOR, 15 * Display.INTERNAL_RES_FACTOR,
                10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mHitAttacker, getX() + 52 * Display.INTERNAL_RES_FACTOR, getY() + 57 * Display.INTERNAL_RES_FACTOR,
                15 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mCritTarget, getX() + 5 * Display.INTERNAL_RES_FACTOR, getY() + 73 * Display.INTERNAL_RES_FACTOR,
                15 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mCritIdentifier, getX() + 29 * Display.INTERNAL_RES_FACTOR,
                getY() + 73 * Display.INTERNAL_RES_FACTOR, 15 * Display.INTERNAL_RES_FACTOR,
                10 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mCritAttacker, getX() + 52 * Display.INTERNAL_RES_FACTOR, getY() + 73 * Display.INTERNAL_RES_FACTOR,
                15 * Display.INTERNAL_RES_FACTOR, 10 * Display.INTERNAL_RES_FACTOR);

    }

    @Override
    public void switchTo(ESide side) {
        switch (side) {
        case LEFT:
            setX(3 * Display.INTERNAL_RES_FACTOR);
            setY(3 * Display.INTERNAL_RES_FACTOR);
            break;
        case RIGHT:
            setX(Display.WIDTH - getWidth() - 5 * Display.INTERNAL_RES_FACTOR);
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

        String weaponName = "--";

        if (mTarget.getEquippedWeapon().isPresent()) {
            weaponName = mTarget.getEquippedWeapon().get().getName();
        }

        mWeaponNameTarget = Assets.getFor(EResource.REGULAR_FONT, weaponName);
    }
}
