package de.ativelox.feo.client.view.screen.editor;

import de.ativelox.feo.client.controller.MapEditorController;
import de.ativelox.feo.client.model.gfx.tile.ITile;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.view.screen.IScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IMapEditorUIScreen extends IScreen {

    public void startRenderingBorder(ISpatial spatial);

    public void stopRenderingBorder(ISpatial spatial);

    public void startDisplayingStats(ITile tile);

    public void stopDisplayingStats(ITile tile);

    public void setController(MapEditorController controller);

}
