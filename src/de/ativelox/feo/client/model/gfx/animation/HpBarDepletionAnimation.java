package de.ativelox.feo.client.model.gfx.animation;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.util.SpriteSheet;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class HpBarDepletionAnimation implements IAnimation, IRequireResources {

    private BufferedImage[] mSequence;

    private final int mMaxHp;

    private final int mCurrentHp;

    private final int mAfterHp;

    private final DefaultNonLoopingAnimation mUnderlyingAnimation;

    public HpBarDepletionAnimation(int maxHp, int currentHp, int afterHp, EAnimationDirection direction) {
        mMaxHp = maxHp;
        mCurrentHp = currentHp;
        mAfterHp = afterHp;

        mSequence = new BufferedImage[currentHp - afterHp + 1];
        this.load();

        mUnderlyingAnimation = new DefaultNonLoopingAnimation(mSequence, direction, mSequence.length * 40);

        mUnderlyingAnimation.setScaling(1);
    }

    @Override
    public void load() {
        BufferedImage empty = Assets.getFor(EResource.HP_BAR_EMPTY);
        BufferedImage full = Assets.getFor(EResource.HP_BAR_FULL);
        BufferedImage trailing = Assets.getFor(EResource.HP_BAR_TRAILING);

        for (int j = 0; j <= mCurrentHp - mAfterHp; j++) {
            List<Image> toStitch = new ArrayList<>();
            for (int i = 1; i < mMaxHp; i++) {
                if (i + j <= mCurrentHp) {
                    toStitch.add(full);
                } else {
                    toStitch.add(empty);
                }

            }
            toStitch.add(trailing);

            mSequence[j] = SpriteSheet.stitchHorizontally(toStitch);
        }

    }

    @Override
    public void update(TimeSnapshot ts) {
        mUnderlyingAnimation.update(ts);

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mUnderlyingAnimation.render(g);

    }

    @Override
    public int getX() {
        return mUnderlyingAnimation.getX();
    }

    @Override
    public int getY() {
        return mUnderlyingAnimation.getY();
    }

    @Override
    public void setX(int x) {
        mUnderlyingAnimation.setX(x);

    }

    @Override
    public void setY(int y) {
        mUnderlyingAnimation.setY(y);

    }

    @Override
    public int getWidth() {
        return mUnderlyingAnimation.getWidth();
    }

    @Override
    public int getHeight() {
        return mUnderlyingAnimation.getHeight();
    }

    @Override
    public void setWidth(int width) {
        mUnderlyingAnimation.setWidth(width);

    }

    @Override
    public void setHeight(int height) {
        mUnderlyingAnimation.setHeight(height);

    }

    @Override
    public Iterator<BufferedImage> iterator() {
        return mUnderlyingAnimation.iterator();
    }

    @Override
    public boolean isLooping() {
        return mUnderlyingAnimation.isLooping();
    }

    @Override
    public boolean isFinished() {
        return mUnderlyingAnimation.isFinished();
    }

    @Override
    public void start() {
        mUnderlyingAnimation.start();

    }

    @Override
    public void stop() {
        mUnderlyingAnimation.stop();

    }

    @Override
    public void reset() {
        mUnderlyingAnimation.reset();

    }

    @Override
    public void hide() {
        mUnderlyingAnimation.hide();

    }

    @Override
    public void show() {
        mUnderlyingAnimation.show();

    }

    @Override
    public void addHook(int frame, Function<TimeSnapshot, Boolean> hook) {
        mUnderlyingAnimation.addHook(frame, hook);

    }

    @Override
    public boolean isHidden() {
        return mUnderlyingAnimation.isHidden();
    }

    @Override
    public void addEndHook(Function<TimeSnapshot, Boolean> hook) {
        mUnderlyingAnimation.addEndHook(hook);
        
    }

}
