package de.ativelox.feo.client.model.property;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IRenderable {

    /**
     * Renders this class to the screen.
     * 
     * @param g The graphics object on which the drawing is performed.
     */
    void render(DepthBufferedGraphics g);

}
