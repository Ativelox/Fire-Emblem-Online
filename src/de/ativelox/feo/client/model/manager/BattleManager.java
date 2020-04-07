package de.ativelox.feo.client.model.manager;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.animation.BattleAnimationMapper;
import de.ativelox.feo.client.model.gfx.animation.ComposedAnimation;
import de.ativelox.feo.client.model.gfx.animation.EAnimationDirection;
import de.ativelox.feo.client.model.gfx.animation.HpBarDepletionAnimation;
import de.ativelox.feo.client.model.gfx.animation.HpDepletionAnimation;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;
import de.ativelox.feo.client.model.gfx.animation.hook.AnimationReplacingHook;
import de.ativelox.feo.client.model.gfx.animation.hook.BlockingAnimationHook;
import de.ativelox.feo.client.model.gfx.animation.hook.IAnimationHook;
import de.ativelox.feo.client.model.gfx.animation.hook.UnitHpDepletionHook;
import de.ativelox.feo.client.model.gfx.dialogue.Dialogue;
import de.ativelox.feo.client.model.gfx.dialogue.DialogueText;
import de.ativelox.feo.client.model.gfx.tile.ETileType;
import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.EBattleAnimType;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.property.ICanDisplayDialogue;
import de.ativelox.feo.client.model.sound.EMusic;
import de.ativelox.feo.client.model.sound.SoundPlayer;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.CombatRule;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.game.DialogueWindow;
import de.ativelox.feo.client.view.screen.game.IBattleScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class BattleManager implements IBattleManager, ICanDisplayDialogue {

    private IUnit mAttacker;
    private IUnit mTarget;

    private int mRange;

    private ComposedAnimation mAnimation;
    private final IBattleScreen mScreen;

    private Image mBattlePlatformLeft;
    private Image mBattlePlatformRight;

    private Image mBattlePlatform;

    private IAnimationHook[] mAttackerHooks;

    private IAnimationHook[] mTargetHooks;

    private List<IAnimation> mToUpdateRender;

    private int mRemainingAttacksAttacker;

    private int mRemainingAttacksTarget;

    private boolean mSwitchSides;

    private DialogueWindow mDialogueWindow;

    private final InputManager mInputManager;

    private boolean mDeathOccured;

    public BattleManager(IBattleScreen screen, final InputManager im) {
	mScreen = screen;
	mInputManager = im;
    }

    /**
     * Gets a complete animation of the following form: <tt>attacker</tt> attacks
     * <tt>target</tt> followed by <tt>target</tt> attacking <tt>attacker</tt>. If
     * the <tt>counterattack</tt> flag is set to <tt>false</tt> then the
     * <tt>target</tt> will not attack the <tt>attacker</tt>.
     * 
     * @param attacker
     * @param target
     * @param range
     * @param counterattack
     * @return
     */
    private ComposedAnimation getOneCycle(IUnit attacker, IUnit target, int range, boolean counterattack) {
	ESide attackerSide = ESide.RIGHT;
	ESide targetSide = ESide.LEFT;

	if (mSwitchSides) {
	    attackerSide = ESide.LEFT;
	    targetSide = ESide.RIGHT;
	}

	boolean attackerHit = CombatRule.getAccuracy(attacker, target) > Math.random() * 100;
	boolean targetHit = CombatRule.getAccuracy(target, attacker) > Math.random() * 100;

	boolean attackerCrit = attackerHit && CombatRule.getCriticalChance(attacker, target) > Math.random() * 100;
	boolean targetCrit = targetHit && CombatRule.getCriticalChance(target, attacker) > Math.random() * 100;

	int targetHpLoss = 0;
	int attackerHpLoss = 0;

	int attackerMultiplier = 1;
	int targetMultiplier = 1;

	if (attackerCrit) {
	    attackerMultiplier = 3;
	}
	if (targetCrit) {
	    targetMultiplier = 3;
	}

	if (attackerHit) {
	    targetHpLoss = CombatRule.getMight(attacker, target);

	}
	if (targetHit) {
	    attackerHpLoss = CombatRule.getMight(target, attacker);
	}

	EBattleAnimType attackerAnimType = null;
	EBattleAnimType targetAnimType = null;

	if (range <= 1) {
	    if (attackerCrit) {
		attackerAnimType = EBattleAnimType.MELEE_CRIT;
	    } else {
		attackerAnimType = EBattleAnimType.MELEE_ATTACK;
	    }
	    if (targetCrit) {
		targetAnimType = EBattleAnimType.MELEE_CRIT;

	    } else {
		targetAnimType = EBattleAnimType.MELEE_ATTACK;
	    }
	} else {
	    if (attackerCrit) {
		attackerAnimType = EBattleAnimType.RANGED_CRIT;

	    } else {
		attackerAnimType = EBattleAnimType.RANGED_ATTACK;
	    }
	    if (targetCrit) {
		targetAnimType = EBattleAnimType.RANGED_CRIT;

	    } else {
		targetAnimType = EBattleAnimType.RANGED_ATTACK;
	    }
	}

	targetHpLoss *= attackerMultiplier;
	attackerHpLoss *= targetMultiplier;

	if (!counterattack) {
	    attackerHpLoss = 0;
	}

	targetHpLoss = Math.min(target.getCurrentHP(), targetHpLoss);
	attackerHpLoss = Math.min(attacker.getCurrentHP(), attackerHpLoss);

	IAnimation hpDepletionTarget = new HpDepletionAnimation(target.getCurrentHP(),
		target.getCurrentHP() - targetHpLoss, EAnimationDirection.FORWARD);
	IAnimation hpBarDepletionTarget = new HpBarDepletionAnimation(target.getMaximumHP(), target.getCurrentHP(),
		target.getCurrentHP() - targetHpLoss, EAnimationDirection.FORWARD);

	IAnimation hpDepletionAttacker = new HpDepletionAnimation(attacker.getCurrentHP(),
		attacker.getCurrentHP() - attackerHpLoss, EAnimationDirection.FORWARD);
	IAnimation hpBarDepletionAttacker = new HpBarDepletionAnimation(attacker.getMaximumHP(),
		attacker.getCurrentHP(), attacker.getCurrentHP() - attackerHpLoss, EAnimationDirection.FORWARD);

	hpDepletionTarget.setX(18 * Display.INTERNAL_RES_FACTOR);
	hpDepletionTarget.setY(137 * Display.INTERNAL_RES_FACTOR);
	hpBarDepletionTarget.setX(38 * Display.INTERNAL_RES_FACTOR);
	hpBarDepletionTarget.setY(140 * Display.INTERNAL_RES_FACTOR);

	hpDepletionAttacker.setX(130 * Display.INTERNAL_RES_FACTOR);
	hpDepletionAttacker.setY(137 * Display.INTERNAL_RES_FACTOR);
	hpBarDepletionAttacker.setX(150 * Display.INTERNAL_RES_FACTOR);
	hpBarDepletionAttacker.setY(140 * Display.INTERNAL_RES_FACTOR);

	if (mSwitchSides) {
	    hpDepletionTarget.setX(130 * Display.INTERNAL_RES_FACTOR);
	    hpBarDepletionTarget.setX(150 * Display.INTERNAL_RES_FACTOR);
	    hpDepletionAttacker.setX(18 * Display.INTERNAL_RES_FACTOR);
	    hpBarDepletionAttacker.setX(38 * Display.INTERNAL_RES_FACTOR);
	}

	if (!attackerHit) {
	    mAttackerHooks = new IAnimationHook[1];

	    mAttackerHooks[0] = new AnimationReplacingHook(
		    BattleAnimationMapper.get(target, EBattleAnimType.DODGE, targetSide));

	    mToUpdateRender.add(hpDepletionTarget);
	    mToUpdateRender.add(hpBarDepletionTarget);

	} else {
	    mAttackerHooks = new IAnimationHook[2];

	    mAttackerHooks[0] = new BlockingAnimationHook(hpDepletionTarget);
	    mAttackerHooks[1] = new BlockingAnimationHook(hpBarDepletionTarget);

	}
	if (!targetHit) {
	    mTargetHooks = new IAnimationHook[1];

	    mTargetHooks[0] = new AnimationReplacingHook(
		    BattleAnimationMapper.get(attacker, EBattleAnimType.DODGE, attackerSide));
	    mToUpdateRender.add(hpDepletionAttacker);
	    mToUpdateRender.add(hpBarDepletionAttacker);

	} else {
	    mTargetHooks = new IAnimationHook[2];

	    mTargetHooks[0] = new BlockingAnimationHook(hpDepletionAttacker);
	    mTargetHooks[1] = new BlockingAnimationHook(hpBarDepletionAttacker);

	}

	IAnimation attackerAnim = BattleAnimationMapper.get(attacker, attackerAnimType, attackerSide, mAttackerHooks);
	IAnimation targetAnim = null;

	if (counterattack) {
	    targetAnim = BattleAnimationMapper.get(target, targetAnimType, targetSide, mTargetHooks);

	} else {
	    targetAnim = Assets.getFor(EResource.BATTLE_ANIMATION, EBattleAnimType.STALE, targetSide, target);
	}

	if (!attackerHit) {
	    ((AnimationReplacingHook) mAttackerHooks[0]).setAnimationToReplace(targetAnim);

	}
	if (!targetHit) {
	    ((AnimationReplacingHook) mTargetHooks[0]).setAnimationToReplace(attackerAnim);

	}
	attackerAnim.addEndHook(new UnitHpDepletionHook(target, targetHpLoss));
	targetAnim.addEndHook(new UnitHpDepletionHook(attacker, attackerHpLoss));

	return new ComposedAnimation(attackerAnim, targetAnim);

    }

    private void attackAgain(IUnit attacker, IUnit target, int range) {
	mToUpdateRender.clear();

	if (mRemainingAttacksAttacker <= 0) {
	    mSwitchSides = !mSwitchSides;
	    mAnimation = getOneCycle(target, attacker, range, false);
	    mRemainingAttacksTarget--;

	} else if (mRemainingAttacksTarget <= 0) {
	    mAnimation = getOneCycle(attacker, target, range, false);
	    mRemainingAttacksAttacker--;

	} else {
	    mAnimation = getOneCycle(attacker, target, range, true);
	    mRemainingAttacksAttacker--;
	    mRemainingAttacksTarget--;

	}

    }

    private void checkUnitDeath(IUnit attacker, IUnit target) {
	if (attacker.getCurrentHP() <= 0) {
	    List<Dialogue> dialogue = new ArrayList<>();
	    dialogue.add(new Dialogue(attacker.getPortrait(), new DialogueText(attacker.getDeathQuote())));

	    mDialogueWindow = new DialogueWindow(dialogue.iterator(), this);
	    mDialogueWindow.registerTo(mInputManager);
	    mDeathOccured = true;

	    SoundPlayer.get().play(EMusic.FINAL_FAREWELL);

	} else if (target.getCurrentHP() <= 0) {
	    List<Dialogue> dialogue = new ArrayList<>();
	    dialogue.add(new Dialogue(target.getPortrait(), new DialogueText(target.getDeathQuote())));

	    mDialogueWindow = new DialogueWindow(dialogue.iterator(), this);
	    mDialogueWindow.registerTo(mInputManager);
	    mDeathOccured = true;

	    SoundPlayer.get().play(EMusic.FINAL_FAREWELL);
	}

    }

    @Override
    public void attack(IUnit attacker, IUnit target, ETileType type, int range) {
	// TODO fetch battle stats, roll random values to determine battle outcome,
	// select appropriate animations, also add appropriate hooks to animations.

	// 0: class
	// 1. gender
	// 2: animation type (dodge, crit, normal, ranged, ...)
	// 3: side (left, right)
	mDialogueWindow = null;
	mBattlePlatform = null;
	mBattlePlatformLeft = null;
	mBattlePlatformRight = null;
	mToUpdateRender = new ArrayList<>();
	mRange = range;
	mAttacker = attacker;
	mTarget = target;
	mSwitchSides = false;
	mDeathOccured = false;

	EMusic current = null;

	List<Dialogue> dialogues = new ArrayList<>();

	if (attacker.getAffiliation() != EAffiliation.ALLIED) {
	    if (attacker.isCommander()) {
		dialogues.add(
			new Dialogue(attacker.getPortrait(), new DialogueText(attacker.getCommanderAttackedQuote())));

		mDialogueWindow = new DialogueWindow(dialogues.iterator(), this);
		mDialogueWindow.registerTo(mInputManager);
		current = EMusic.BATTLE_FOR_TOMORROW;
	    }
	    mSwitchSides = true;

	} else if (target.isCommander()) {
	    dialogues.add(new Dialogue(target.getPortrait(), new DialogueText(target.getCommanderAttackedQuote())));

	    mDialogueWindow = new DialogueWindow(dialogues.iterator(), this);
	    mDialogueWindow.registerTo(mInputManager);
	    current = EMusic.BATTLE_FOR_TOMORROW;

	}

	if (range <= 1) {
	    mBattlePlatform = Assets.getFor(EResource.BATTLE_PLATFORM_CLOSE, type);

	} else {

	    mBattlePlatformLeft = Assets.getFor(EResource.BATTLE_PLATFORM_WIDE, type, ESide.LEFT);
	    mBattlePlatformRight = Assets.getFor(EResource.BATTLE_PLATFORM_WIDE, type, ESide.RIGHT);
	}

	mAnimation = getOneCycle(mAttacker, mTarget, range, true);

	if (CombatRule.hasRepeatedAttack(attacker, target) && CombatRule.hasRepeatedAttack(target, attacker)) {
	    mRemainingAttacksAttacker = 1;
	    mRemainingAttacksTarget = 1;

	} else if (CombatRule.hasRepeatedAttack(attacker, target)) {
	    mRemainingAttacksAttacker = 1;
	    mRemainingAttacksTarget = 0;

	} else if (CombatRule.hasRepeatedAttack(target, attacker)) {
	    mRemainingAttacksTarget = 1;
	    mRemainingAttacksAttacker = 0;

	} else {
	    mRemainingAttacksAttacker = 0;
	    mRemainingAttacksTarget = 0;
	}

	if (current != null) {
	    SoundPlayer.get().play(current);
	} else if (mSwitchSides) {
	    SoundPlayer.get().play(EMusic.REVENGE);
	} else {
	    SoundPlayer.get().play(EMusic.ATTACK);
	}

    }

    @Override
    public void update(TimeSnapshot ts) {

	if (mDialogueWindow != null) {
	    mDialogueWindow.update(ts);
	}

	if (mDialogueWindow != null && !mDialogueWindow.isFinished()) {
	    return;
	}
	if (mDialogueWindow == null) {
	    this.checkUnitDeath(mAttacker, mTarget);
	}

	mAnimation.update(ts);

	for (IAnimationHook anim : mAttackerHooks) {
	    anim.update(ts);

	}
	for (IAnimationHook anim : mTargetHooks) {
	    anim.update(ts);
	}

	mToUpdateRender.forEach(c -> c.update(ts));

	if (mAnimation.isFinished() && (mRemainingAttacksAttacker > 0 || mRemainingAttacksTarget > 0)) {

	    this.attackAgain(mAttacker, mTarget, mRange);

	}

	if (mAnimation.isFinished() && mRemainingAttacksAttacker <= 0 && mRemainingAttacksTarget <= 0) {
	    mScreen.onBattleFinished();

	}
    }

    @Override
    public void render(DepthBufferedGraphics g) {
	if (mDialogueWindow != null) {
	    mDialogueWindow.render(g);
	}

	if (mBattlePlatform != null) {
	    g.drawImage(mBattlePlatform, 30 * Display.INTERNAL_RES_FACTOR, 85 * Display.INTERNAL_RES_FACTOR,
		    mBattlePlatform.getWidth(null) * Display.INTERNAL_RES_FACTOR,
		    mBattlePlatform.getHeight(null) * Display.INTERNAL_RES_FACTOR, -1);
	}
	if (mBattlePlatformLeft != null) {
	    g.drawImage(mBattlePlatformLeft, 0 * Display.INTERNAL_RES_FACTOR, 85 * Display.INTERNAL_RES_FACTOR,
		    mBattlePlatformLeft.getWidth(null) * Display.INTERNAL_RES_FACTOR,
		    mBattlePlatformLeft.getHeight(null) * Display.INTERNAL_RES_FACTOR, -1);

	    g.drawImage(mBattlePlatformRight, 138 * Display.INTERNAL_RES_FACTOR, 85 * Display.INTERNAL_RES_FACTOR,
		    mBattlePlatformRight.getWidth(null) * Display.INTERNAL_RES_FACTOR,
		    mBattlePlatformRight.getHeight(null) * Display.INTERNAL_RES_FACTOR, -1);
	}
	for (IAnimationHook anim : mAttackerHooks) {
	    anim.render(g);
	}
	for (IAnimationHook anim : mTargetHooks) {
	    anim.render(g);
	}
	mToUpdateRender.forEach(c -> c.render(g));

	mAnimation.render(g);

    }

    @Override
    public void onDialogueFinished() {
	mDialogueWindow.unregisterFrom(mInputManager);
	if (mDeathOccured) {
	    mScreen.onBattleFinished();
	    return;
	}
	mDialogueWindow = null;

    }
}
