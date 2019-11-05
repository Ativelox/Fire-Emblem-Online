package de.ativelox.feo.client.model.gfx.animation;

import java.awt.image.BufferedImage;
import java.util.function.Function;

import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IAnimation extends IUpdateable, IRenderable, ISpatial, Iterable<BufferedImage> {

    boolean isLooping();

    boolean isFinished();

    void start();

    void stop();

    void reset();

    void hide();

    void show();

    /**
     * Adds a hook to the animation in the given frame. A hook can be anything that
     * comforts to the given {@link Function} signature, but the intended way is for
     * this animation to halt progression until the given hook returns
     * <tt>true</tt>. For this the hook is given a {@link TimeSnapshot} to properly
     * handle its own execution.
     * 
     * @param frame The frame in the animation execution sequence at which to
     *              execute the given hook.
     * @param hook  The hook described above, able to halt animation execution and
     *              do arbitrary execution until its conditions are met.
     */
    void addHook(int frame, Function<TimeSnapshot, Boolean> hook);

}
