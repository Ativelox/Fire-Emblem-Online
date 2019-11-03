package de.ativelox.feo.client.model.gfx.animation;

import java.awt.image.BufferedImage;

import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.IUpdateable;

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

    IAnimation copy();

}
