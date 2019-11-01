package de.ativelox.feo.client.view.screen;

import java.awt.Graphics2D;

import de.ativelox.feo.client.model.property.IUpdateable;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IScreenManager extends IUpdateable {

    /**
     * Adds the given screen to this manager.
     * 
     * @param screen The screen to add.
     */
    void addScreen(final IScreen screen);

    /**
     * Removes a screen from this manager. The fashion in which the removal works in
     * solely dependent on the implementation.
     */
    void removeScreen();

    void draw(Graphics2D g);

    void switchTo(EScreen screen);

}
