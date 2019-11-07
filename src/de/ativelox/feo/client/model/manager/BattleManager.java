package de.ativelox.feo.client.model.manager;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.animation.ComposedAnimation;
import de.ativelox.feo.client.model.gfx.animation.EAnimationDirection;
import de.ativelox.feo.client.model.gfx.animation.HpBarDepletionAnimation;
import de.ativelox.feo.client.model.gfx.animation.HpDepletionAnimation;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;
import de.ativelox.feo.client.model.gfx.tile.ETileType;
import de.ativelox.feo.client.model.property.EBattleAnimType;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.sound.EMusic;
import de.ativelox.feo.client.model.sound.SoundPlayer;
import de.ativelox.feo.client.model.unit.BattleAnimationMapper;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.screen.game.IBattleScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class BattleManager implements IBattleManager {

    private ComposedAnimation mAnimation;
    private final IBattleScreen mScreen;

    private Image mBattlePlatformLeft;
    private Image mBattlePlatformRight;

    private Image mBattlePlatform;

    private IAnimation mHpDepletionTarget;
    private IAnimation mHpBarDepletionTarget;

    private IAnimation mHpDepletionAttacker;
    private IAnimation mHpBarDepletionAttacker;

    public BattleManager(IBattleScreen screen) {
        mScreen = screen;
    }

    @Override
    public void attack(IUnit attacker, IUnit target, ETileType type, int range) {
        // TODO fetch battle stats, roll random values to determine battle outcome,
        // select appropriate animations, also add appropriate hooks to animations.

        // 0: class
        // 1. gender
        // 2: animation type (dodge, crit, normal, ranged, ...)
        // 3: side (left, right)
        mBattlePlatform = null;
        mBattlePlatformLeft = null;
        mBattlePlatformRight = null;
        IAnimation attackerAnim = null;
        IAnimation targetAnim = null;
        mHpDepletionTarget = null;

        mHpDepletionTarget = new HpDepletionAnimation(target.getCurrentHP(), 1, 1000, EAnimationDirection.FORWARD);
        mHpBarDepletionTarget = new HpBarDepletionAnimation(target.getMaximumHP(), target.getCurrentHP(), 1,
                EAnimationDirection.FORWARD, 1000);

        mHpDepletionAttacker = new HpDepletionAnimation(attacker.getCurrentHP(), 0, 1000, EAnimationDirection.FORWARD);
        mHpBarDepletionAttacker = new HpBarDepletionAnimation(attacker.getMaximumHP(), attacker.getCurrentHP(), 0,
                EAnimationDirection.FORWARD, 1000);

        if (range <= 1) {
            attackerAnim = BattleAnimationMapper.get(attacker, EBattleAnimType.MELEE_CRIT, ESide.RIGHT,
                    mHpDepletionTarget, mHpBarDepletionTarget);

            targetAnim = BattleAnimationMapper.get(target, EBattleAnimType.MELEE_CRIT, ESide.LEFT, mHpDepletionAttacker,
                    mHpBarDepletionAttacker);

            mBattlePlatform = Assets.getFor(EResource.BATTLE_PLATFORM_CLOSE, type.toString());
        } else {
            attackerAnim = BattleAnimationMapper.get(attacker, EBattleAnimType.RANGED_ATTACK, ESide.RIGHT,
                    mHpDepletionTarget, mHpBarDepletionTarget);

            targetAnim = BattleAnimationMapper.get(target, EBattleAnimType.RANGED_ATTACK, ESide.LEFT,
                    mHpDepletionAttacker, mHpBarDepletionAttacker);

            attackerAnim.setX(30 * Display.INTERNAL_RES_FACTOR);
            targetAnim.setX(-30 * Display.INTERNAL_RES_FACTOR);

            mBattlePlatformLeft = Assets.getFor(EResource.BATTLE_PLATFORM_WIDE, type.toString(), ESide.LEFT.toString());
            mBattlePlatformRight = Assets.getFor(EResource.BATTLE_PLATFORM_WIDE, type.toString(),
                    ESide.RIGHT.toString());
        }
        mAnimation = new ComposedAnimation(attackerAnim, targetAnim);

        mHpDepletionTarget.setX(18 * Display.INTERNAL_RES_FACTOR);
        mHpDepletionTarget.setY(137 * Display.INTERNAL_RES_FACTOR);
        mHpBarDepletionTarget.setX(38 * Display.INTERNAL_RES_FACTOR);
        mHpBarDepletionTarget.setY(140 * Display.INTERNAL_RES_FACTOR);

        mHpDepletionAttacker.setX(130 * Display.INTERNAL_RES_FACTOR);
        mHpDepletionAttacker.setY(137 * Display.INTERNAL_RES_FACTOR);
        mHpBarDepletionAttacker.setX(150 * Display.INTERNAL_RES_FACTOR);
        mHpBarDepletionAttacker.setY(140 * Display.INTERNAL_RES_FACTOR);

        SoundPlayer.get().play(EMusic.ARENA_BATTLE);

    }

    @Override
    public void update(TimeSnapshot ts) {
        mAnimation.update(ts);
        mHpDepletionTarget.update(ts);
        mHpBarDepletionTarget.update(ts);
        mHpDepletionAttacker.update(ts);
        mHpBarDepletionAttacker.update(ts);

        if (mAnimation.isFinished()) {
            mScreen.onBattleFinished();

        }
    }

    @Override
    public void render(DepthBufferedGraphics g) {
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
//        
//        g.drawImage(mHpTarget, 20 * Display.INTERNAL_RES_FACTOR, 137 * Display.INTERNAL_RES_FACTOR);
//        g.drawImage(mHpAttacker, 133 * Display.INTERNAL_RES_FACTOR, 137 * Display.INTERNAL_RES_FACTOR);

        mHpDepletionTarget.render(g);
        mHpBarDepletionTarget.render(g);
        mHpDepletionAttacker.render(g);
        mHpBarDepletionAttacker.render(g);

        mAnimation.render(g);

    }

}
