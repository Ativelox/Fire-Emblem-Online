package de.ativelox.feo.client.view.screen;

import de.ativelox.feo.client.model.camera.ECameraApplication;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.IUpdateable;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IScreen extends IRenderable, IUpdateable {
    
    EScreen getIdentifier();
    
    boolean renderLower();
    
    boolean updateLower();
    
    ECameraApplication cameraApplied();

}
