package de.ativelox.feo.client.view.screen.editor;

import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Collection;

import de.ativelox.feo.client.controller.MapEditorController;
import de.ativelox.feo.client.model.gfx.tile.editor.EditorTile;
import de.ativelox.feo.client.model.property.callback.IClickListener;
import de.ativelox.feo.client.model.property.callback.IHoverListener;
import de.ativelox.feo.client.model.property.callback.IRelativeMouseMoveListener;
import de.ativelox.feo.client.view.screen.IScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IMapEditorScreen
        extends IScreen, IRelativeMouseMoveListener, IHoverListener, KeyListener, MouseListener, IClickListener {

    public void updateGrid(int width, int height);

    public void setTileSet(String tileSetName, Collection<EditorTile> tileSet);

    public void setController(MapEditorController controller);

    public void newBind(EditorTile tile);

    public int getSnapTileSize();

    public Rectangle getSnapBounds();

    public void updateGridValue(EditorTile data, int colIndex, int rowIndex);

    public void clearMap();

}
