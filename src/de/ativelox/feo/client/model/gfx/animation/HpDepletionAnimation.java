package de.ativelox.feo.client.model.gfx.animation;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.function.Function;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class HpDepletionAnimation implements IRequireResources, IAnimation {

    private final DefaultNonLoopingAnimation mUnderlyingAnimation;

    private BufferedImage[] mSequence;

    private final int mFrom;

    private final int mTo;

    public HpDepletionAnimation(int from, int to, EAnimationDirection direction) {
        mFrom = from;
        mTo = to;

        mSequence = new BufferedImage[from - to + 1];

        this.load();

        mUnderlyingAnimation = new DefaultNonLoopingAnimation(mSequence, direction, mSequence.length * 40);
        mUnderlyingAnimation.setScaling(1);

    }

    @Override
    public void load() {
        int j = 0;
        for (int i = mFrom; i >= mTo; i--) {
            mSequence[j] = Assets.getFor(EResource.REGULAR_FONT, i + "");
            j++;

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
}
