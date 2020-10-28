package de.ativelox.feo.client.view.element.game;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.EAffiliation;
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

    private Image mTargetWeaponImage;
    private Image mAttackerWeaponImage;

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

	Image targetName = mTargetName;
	Image attackerName = mAttackerName;

	Image hitTarget = mHitTarget;
	Image dmgTarget = mDmgTarget;
	Image crtTarget = mCrtTarget;

	Image hitAttacker = mHitAttacker;
	Image dmgAttacker = mDmgAttacker;
	Image crtAttacker = mCrtAttacker;

	Image targetWeapon = mTargetWeapon;
	Image attackerWeapon = mAttackerWeapon;

	Image targetWeaponImage = mTargetWeaponImage;
	Image attackerWeaponImage = mAttackerWeaponImage;

	if (mAttacker.getAffiliation() != EAffiliation.ALLIED) {
	    targetName = mAttackerName;
	    attackerName = mTargetName;

	    hitTarget = mHitAttacker;
	    dmgTarget = mDmgAttacker;
	    crtTarget = mCrtAttacker;

	    hitAttacker = mHitTarget;
	    dmgAttacker = mDmgTarget;
	    crtAttacker = mCrtTarget;

	    targetWeapon = mAttackerWeapon;
	    attackerWeapon = mTargetWeapon;

	    targetWeaponImage = mAttackerWeaponImage;
	    attackerWeaponImage = mTargetWeaponImage;

	}

	g.drawImage(targetName, (int) ((mNameBoxWidth / 2f) - (targetName.getWidth(null) / 2f)),
		(int) (4 * Display.INTERNAL_RES_FACTOR + (mNameBoxHeight / 2f - targetName.getHeight(null) / 2f)),
		BATTLE_OVERLAY_LAYER);
	g.drawImage(attackerName,
		185 * Display.INTERNAL_RES_FACTOR + (int) ((mNameBoxWidth / 2f) - (attackerName.getWidth(null) / 2f)),
		(int) (4 * Display.INTERNAL_RES_FACTOR + (mNameBoxHeight / 2f - attackerName.getHeight(null) / 2f)),
		BATTLE_OVERLAY_LAYER);

	g.drawImage(mHitIdentifier, 9 * Display.INTERNAL_RES_FACTOR, 108 * Display.INTERNAL_RES_FACTOR,
		10 * Display.INTERNAL_RES_FACTOR, 8 * Display.INTERNAL_RES_FACTOR);
	g.drawImage(mDmgIdentifier, 9 * Display.INTERNAL_RES_FACTOR, 115 * Display.INTERNAL_RES_FACTOR,
		10 * Display.INTERNAL_RES_FACTOR, 8 * Display.INTERNAL_RES_FACTOR);
	g.drawImage(mCrtIdentifier, 9 * Display.INTERNAL_RES_FACTOR, 123 * Display.INTERNAL_RES_FACTOR,
		10 * Display.INTERNAL_RES_FACTOR, 8 * Display.INTERNAL_RES_FACTOR);

	g.drawImage(mHitIdentifier, 196 * Display.INTERNAL_RES_FACTOR, 108 * Display.INTERNAL_RES_FACTOR,
		10 * Display.INTERNAL_RES_FACTOR, 8 * Display.INTERNAL_RES_FACTOR);
	g.drawImage(mDmgIdentifier, 196 * Display.INTERNAL_RES_FACTOR, 115 * Display.INTERNAL_RES_FACTOR,
		10 * Display.INTERNAL_RES_FACTOR, 8 * Display.INTERNAL_RES_FACTOR);
	g.drawImage(mCrtIdentifier, 196 * Display.INTERNAL_RES_FACTOR, 123 * Display.INTERNAL_RES_FACTOR,
		10 * Display.INTERNAL_RES_FACTOR, 8 * Display.INTERNAL_RES_FACTOR);

	g.drawImage(hitTarget,
		23 * Display.INTERNAL_RES_FACTOR - hitTarget.getWidth(null) + 21 * Display.INTERNAL_RES_FACTOR,
		108 * Display.INTERNAL_RES_FACTOR, hitTarget.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

	g.drawImage(dmgTarget,
		22 * Display.INTERNAL_RES_FACTOR - dmgTarget.getWidth(null) + 21 * Display.INTERNAL_RES_FACTOR,
		115 * Display.INTERNAL_RES_FACTOR, dmgTarget.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

	g.drawImage(crtTarget,
		22 * Display.INTERNAL_RES_FACTOR - crtTarget.getWidth(null) + 21 * Display.INTERNAL_RES_FACTOR,
		123 * Display.INTERNAL_RES_FACTOR, crtTarget.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

	g.drawImage(hitAttacker,
		209 * Display.INTERNAL_RES_FACTOR - hitAttacker.getWidth(null) + 21 * Display.INTERNAL_RES_FACTOR,
		108 * Display.INTERNAL_RES_FACTOR, hitAttacker.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

	g.drawImage(dmgAttacker,
		209 * Display.INTERNAL_RES_FACTOR - dmgAttacker.getWidth(null) + 21 * Display.INTERNAL_RES_FACTOR,
		115 * Display.INTERNAL_RES_FACTOR, dmgAttacker.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

	g.drawImage(crtAttacker,
		209 * Display.INTERNAL_RES_FACTOR - crtAttacker.getWidth(null) + 21 * Display.INTERNAL_RES_FACTOR,
		123 * Display.INTERNAL_RES_FACTOR, crtAttacker.getWidth(null), 8 * Display.INTERNAL_RES_FACTOR);

	g.drawImage(targetWeapon,
		68 * Display.INTERNAL_RES_FACTOR + mWeaponBoxWidth / 2 - targetWeapon.getWidth(null) / 2,
		119 * Display.INTERNAL_RES_FACTOR + mWeaponBoxHeight / 2 - targetWeapon.getHeight(null) / 2);

	g.drawImage(attackerWeapon,
		140 * Display.INTERNAL_RES_FACTOR + mWeaponBoxWidth / 2 - attackerWeapon.getWidth(null) / 2,
		119 * Display.INTERNAL_RES_FACTOR + mWeaponBoxHeight / 2 - attackerWeapon.getHeight(null) / 2);

	if (attackerWeaponImage != null) {
	    g.drawImage(attackerWeaponImage, 125 * Display.INTERNAL_RES_FACTOR, 117 * Display.INTERNAL_RES_FACTOR,
		    attackerWeaponImage.getWidth(null) * Display.INTERNAL_RES_FACTOR,
		    attackerWeaponImage.getHeight(null) * Display.INTERNAL_RES_FACTOR);
	}
	if (targetWeaponImage != null) {
	    g.drawImage(targetWeaponImage, 53 * Display.INTERNAL_RES_FACTOR, 117 * Display.INTERNAL_RES_FACTOR,
		    targetWeaponImage.getWidth(null) * Display.INTERNAL_RES_FACTOR,
		    targetWeaponImage.getHeight(null) * Display.INTERNAL_RES_FACTOR);
	}

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

	if (CombatRule.canCounterattack(mAttacker, mTarget)) {
	    mHitTarget = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getAccuracy(mTarget, mAttacker) + "");
	    mDmgTarget = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getMight(mTarget, mAttacker) + "");
	    mCrtTarget = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getCriticalChance(mTarget, mAttacker) + "");
	} else {
	    mHitTarget = Assets.getFor(EResource.REGULAR_FONT, "--");
	    mDmgTarget = Assets.getFor(EResource.REGULAR_FONT, "--");
	    mCrtTarget = Assets.getFor(EResource.REGULAR_FONT, "--");
	}
	mHitAttacker = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getAccuracy(mAttacker, mTarget) + "");
	mDmgAttacker = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getMight(mAttacker, mTarget) + "");
	mCrtAttacker = Assets.getFor(EResource.REGULAR_FONT, CombatRule.getCriticalChance(mAttacker, mTarget) + "");

	String weaponTarget = "--";

	if (mTarget.getEquippedWeapon().isPresent()) {
	    weaponTarget = mTarget.getEquippedWeapon().get().getName();
	    mTargetWeaponImage = mTarget.getEquippedWeapon().get().getImage();
	}
	mTargetWeapon = Assets.getFor(EResource.REGULAR_FONT, weaponTarget);

	String weaponAttacker = "--";

	if (mAttacker.getEquippedWeapon().isPresent()) {
	    weaponAttacker = mAttacker.getEquippedWeapon().get().getName();
	    mAttackerWeaponImage = mAttacker.getEquippedWeapon().get().getImage();
	}
	mAttackerWeapon = Assets.getFor(EResource.REGULAR_FONT, weaponAttacker);

    }
}
