package de.ativelox.feo.client.model.camera;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public enum ECameraApplication {

    /**
     * Signifies that only the resize of the window will influence how the objects
     * are resized.
     */
    WINDOW_RESIZE_ONLY,

    /**
     * Signifies that nothing will change the position of objects on the screen.
     */
    NONE,

    /**
     * Signifies that resizing the window, and user zooming/transformation do apply
     * to the objects on the screen.
     */
    DYNAMIC,
    /**
     * Signifies that only user zoom and transformations do apply to the objects on
     * the screen.
     */
    USER_ZT;

}
