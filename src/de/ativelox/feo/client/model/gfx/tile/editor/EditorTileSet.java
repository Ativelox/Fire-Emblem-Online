package de.ativelox.feo.client.model.gfx.tile.editor;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.tile.ATileSet;
import de.ativelox.feo.client.model.gfx.tile.ETileSet;
import de.ativelox.feo.client.model.gfx.tile.ETileType;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.property.callback.IClickListener;
import de.ativelox.feo.client.model.property.callback.IHoverListener;
import de.ativelox.feo.client.model.property.callback.IRelativeMouseMoveListener;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class EditorTileSet extends ATileSet<EditorTile>
        implements IRenderable, IUpdateable, IRelativeMouseMoveListener {

    private final Collection<EditorTile> mTiles;

    public EditorTileSet(ETileSet set) {
        super(set);
        mTiles = new ArrayList<>();

        generateTiles(request());
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        for (final EditorTile tile : mTiles) {
            tile.render(g);
        }

    }

    @Override
    protected void put(int index, BufferedImage[] tiles, ETileType type) {
        if (!mMapping.containsKey(type)) {
            mMapping.put(type, new ArrayList<>());

        }
        EditorTile tile = new EditorTile(type, tiles[index + (32 * mRow)]);
        mTiles.add(tile);
        mMapping.get(type).add(tile);
        mIdMapping.put(tile.getId(), tile);

    }

    public Collection<EditorTile> getTiles() {
        return mTiles;
    }

    @Override
    public void onMouseMoved(int relX, int relY) {
        for (final EditorTile tile : mTiles) {
            tile.onMouseMoved(relX, relY);
        }

    }

    @Override
    public void update(TimeSnapshot ts) {
        for (final EditorTile tile : mTiles) {
            tile.update(ts);
        }

    }
    

    public List<EditorTile> get(ETileType type) {
        return mMapping.get(type);
    }

    public void addHoverListener(final IHoverListener listener) {
        mTiles.forEach(t -> t.addHoverListener(listener));

    }

    public void addClickListener(final IClickListener listener) {
        mTiles.forEach(t -> t.addClickListener(listener));
    }
}
