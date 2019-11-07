package de.ativelox.feo.client.model.gfx.animation.hook;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.animation.IAnimation;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class AnimationReplacingHook implements IAnimationHook {

    private final IAnimation mNewAnimation;

    private IAnimation mOldAnimation;

    public AnimationReplacingHook(IAnimation newAnim, IAnimation oldAnim) {
        newAnim.hide();

        mNewAnimation = newAnim;
        mOldAnimation = oldAnim;

    }

    public AnimationReplacingHook(IAnimation newAnim) {
        this(newAnim, null);
    }

    public void setAnimationToReplace(IAnimation toReplace) {
        mOldAnimation = toReplace;

    }

    @Override
    public Boolean apply(TimeSnapshot t) {
        mNewAnimation.show();
        mNewAnimation.start();
        mOldAnimation.hide();

        return true;
    }

    @Override
    public void update(TimeSnapshot ts) {
        mNewAnimation.update(ts);

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (!mOldAnimation.isHidden()) {
            return;
        }
        mNewAnimation.render(g);

    }

}
