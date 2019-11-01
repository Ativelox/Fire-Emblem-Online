package de.ativelox.feo.client.model.property.callback;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IResizeListener {

    /**
     * Called when a specific component gets resized. The exact contract is
     * specified by the implementing class. Let <tt>width</tt> be the original width
     * of the component and <tt>newWidth</tt> the width after the resize of the
     * component, analogously for <tt>height</tt>. Then the arguments are generated
     * by the following formula:
     * <p>
     * <tt>xFactor * width = newWidth</tt><br>
     * <tt>yFactor * height = newHeight</tt>
     * </p>
     * 
     * @param xFactor the factor specified.
     * @param yFactor the factor specified.
     */
    void onResize(double xFactor, double yFactor);

}
