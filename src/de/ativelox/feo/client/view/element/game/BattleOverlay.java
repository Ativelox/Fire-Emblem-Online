package de.ativelox.feo.client.view.element.game;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.CombatRule;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.AScreenElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class BattleOverlay extends AScreenElement implements IRequireResources {

    private IUnit mAttacker;
    private IUnit mTarget;

    private Image mAttackerName;
    private Image mTargetName;

    private final int mNameBoxWidth;
    private final int mNameBoxHeight;

    private final int mWeaponBoxWidth;
    private final int mWeaponBoxHeight;

    private Image mBattleBackground;

    private Image mHitIdentifier;
    private Image mDmgIdentifier;
    private Image mCrtIdentifier;

    private Image mHitTarget;
    private Image mDmgTarget;
    private Image mCrtTarget;

    private Image mHitAttacker;
    private Image mDmgAttacker;
    private Image mCrtAttacker;

    private Image mTargetWeapon;
    private Image mAttackerWeapon;

    private static final int BATTLE_OVERLAY_LAYER = 5;

    public BattleOverlay() {
        super(0, 0, Display.WIDTH, Display.HEIGHT, false);

        mNameBoxWidth = 55 * Display.INTERNAL_RES_FACTOR;
        mNameBoxHeight = 16 * Display.INTERNAL_RES_FACTOR;

        mWeaponBoxHeight = 14 * Display.INTERNAL_RES_FACTOR;
        mWeaponBoxWidth = 50 * Display.INTERNAL_RES_FACTOR;
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        g.drawImage(mBattleBackground, 0, -30, Display.WIDTH, Display.HEIGHT);

        g.drawImage(mTargetName, (int) ((mNameBoxWidth / 2f) - (mTargetName.getWidth(null) / 2f)),
                (int) (4 * Display.INTERNAL_RES_FACTOR + (mNameBoxHeight / 2f - mTargetName.getHeight(null) / 2f)),
                BATTLE_OVERLAY_LAYER);
        g.drawImage(mAttackerName,
                185 * Display.INTERNAL_RES_FACTOR + (int) ((mNameBoxWidth / 2f) - (mAttackerName.getWidth(null) / 2f)),
                (int) (4 * Display.INTERNAL_RES_FACTOR + (mNameBoxHeight / 2f - mAttackerName.getHeight(null) / 2f)),
                BATTLE_OVERLAY_LAYER);

        g.drawImage(mHitIdentifier, 9 * Display.INTERNAL_RES_FACTOR, 108 * Display.INTERNAL_RES_FACTOR,
                mHitIdentifier.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);
        g.drawImage(mDmgIdentifier, 9 * Display.INTERNAL_RES_FACTOR, 115 * Display.INTERNAL_RES_FACTOR,
                mHitIdentifier.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);
        g.drawImage(mCrtIdentifier, 9 * Display.INTERNAL_RES_FACTOR, 123 * Display.INTERNAL_RES_FACTOR,
                mHitIdentifier.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mHitIdentifier, 196 * Display.INTERNAL_RES_FACTOR, 108 * Display.INTERNAL_RES_FACTOR,
                mHitIdentifier.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);
        g.drawImage(mDmgIdentifier, 196 * Display.INTERNAL_RES_FACTOR, 115 * Display.INTERNAL_RES_FACTOR,
                mHitIdentifier.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);
        g.drawImage(mCrtIdentifier, 196 * Display.INTERNAL_RES_FACTOR, 123 * Display.INTERNAL_RES_FACTOR,
                mHitIdentifier.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mHitTarget,
                22 * Display.INTERNAL_RES_FACTOR - mHitTarget.getWidth(null) + 21 * Display.INTERNAL_RES_FACTOR,
                108 * Display.INTERNAL_RES_FACTOR, mHitTarget.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mDmgTarget,
                22 * Display.INTERNAL_RES_FACTOR - mDmgTarget.getWidth(null) + 21 * Display.INTERNAL_RES_FACTOR,
                115 * Display.INTERNAL_RES_FACTOR, mDmgTarget.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mCrtTarget,
                22 * Display.INTERNAL_RES_FACTOR - mCrtTarget.getWidth(null) + 21 * Display.INTERNAL_RES_FACTOR,
                123 * Display.INTERNAL_RES_FACTOR, mCrtTarget.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mHitAttacker,
                209 * Display.INTERNAL_RES_FACTOR - mHitAttacker.getWidth(null) + 21 * Display.INTERNAL_RES_FACTOR,
                108 * Display.INTERNAL_RES_FACTOR, mHitAttacker.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mDmgAttacker,
                209 * Display.INTERNAL_RES_FACTOR - mDmgAttacker.getWidth(null) + 21 * Display.INTERNAL_RES_FACTOR,
                115 * Display.INTERNAL_RES_FACTOR, mDmgAttacker.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mCrtAttacker,
                209 * Display.INTERNAL_RES_FACTOR - mCrtAttacker.getWidth(null) + 21 * Display.INTERNAL_RES_FACTOR,
                123 * Display.INTERNAL_RES_FACTOR, mCrtAttacker.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

        g.drawImage(mTargetWeapon,
                68 * Display.INTERNAL_RES_FACTOR + mWeaponBoxWidth / 2 - mTargetWeapon.getWidth(null) / 2,
                119 * Display.INTERNAL_RES_FACTOR + mWeaponBoxHeight / 2 - mTargetWeapon.getHeight(null) / 2);

        g.drawImage(mAttackerWeapon,
                140 * Display.INTERNAL_RES_FACTOR + mWeaponBoxWidth / 2 - mAttackerWeapon.getWidth(null) / 2,
                119 * Display.INTERNAL_RES_FACTOR + mWeaponBoxHeight / 2 - mAttackerWeapon.getHeight(null) / 2);

    }

    public void setParticipants(IUnit attacker, IUnit target) {
        mAttacker = attacker;
        mTarget = target;

        this.load();

    }

    @Override
    public void load() {
        mBattleBackground = Assets.getFor(EResource.BATTLE_BACKGROUND);

        mAttackerName = Assets.getFor(EResource.REGULAR_FONT, mAttacker.getName());
        mTargetName = Assets.getFor(EResource.REGULAR_FONT, mTarget.getName());

        mHitIdentifier = Assets.getFor(EResource.REGULAR_FONT, "Hit");
        mDmgIdentifier = Assets.getFor(EResource.REGULAR_FONT, "Dmg");
        mCrtIdentifier = Assets.getFor(EResource.REGULAR_FONT, "Crt");

        mHitTarget = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getAccuracy(mTarget, mAttacker) + "");
        mDmgTarget = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getMight(mTarget, mAttacker) + "");
        mCrtTarget = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getCriticalChance(mTarget, mAttacker) + "");

        mHitAttacker = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getAccuracy(mAttacker, mTarget) + "");
        mDmgAttacker = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getMight(mAttacker, mTarget) + "");
        mCrtAttacker = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getCriticalChance(mAttacker, mTarget) + "");

        String weaponTarget = "--";

        if (mTarget.getEquippedWeapon().isPresent()) {
            weaponTarget = mTarget.getEquippedWeapon().get().getName();
        }
        mTargetWeapon = Assets.getFor(EResource.REGULAR_FONT, weaponTarget);

        String weaponAttacker = "--";

        if (mAttacker.getEquippedWeapon().isPresent()) {
            weaponAttacker = mAttacker.getEquippedWeapon().get().getName();
        }
        mAttackerWeapon = Assets.getFor(EResource.REGULAR_FONT, weaponAttacker);

    }
}
