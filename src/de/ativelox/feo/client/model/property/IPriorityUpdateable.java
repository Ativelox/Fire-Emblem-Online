package de.ativelox.feo.client.model.property;

/**
 * This interface is similar to {@link IUpdateable} but should always be
 * executed before implementations of {@link IUpdateable}.
 * 
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IPriorityUpdateable extends IUpdateable {

    /**
     * Gets the priority of this class's update. The higher, the earlier it will be
     * called where <tt>0</tt> is the default.
     * 
     * @return
     */
    int getPriority();

}
