package de.ativelox.feo.client.model.gfx.tile.editor;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import de.ativelox.feo.client.model.gfx.tile.ETileType;
import de.ativelox.feo.client.model.gfx.tile.ITile;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.EClickType;
import de.ativelox.feo.client.model.property.IClickable;
import de.ativelox.feo.client.model.property.IHoverable;
import de.ativelox.feo.client.model.property.callback.IClickListener;
import de.ativelox.feo.client.model.property.callback.IHoverListener;
import de.ativelox.feo.client.model.util.DefaultHoverRoutine;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class EditorTile extends Tile implements IHoverable, IClickable {

    private final IHoverable mRoutine;

    private final List<IClickListener> mListener;

    private boolean mHovered;

    private boolean mPressed;

    public EditorTile(ETileType type, Image image) {
        super(type, image);

        mListener = new ArrayList<>();
        mRoutine = new DefaultHoverRoutine(this);
    }

    public EditorTile(ETileType type, Image image, int id) {
        super(type, image, id);

        mListener = new ArrayList<>();
        mRoutine = new DefaultHoverRoutine(this);
    }

    @Override
    public void hoverStart() {
        mHovered = true;
    }

    @Override
    public void hoverStop() {
        mHovered = false;
    }

    @Override
    public void update(TimeSnapshot ts) {
        mRoutine.update(ts);

    }

    @Override
    public void onMouseMoved(int relX, int relY) {
        mRoutine.onMouseMoved(relX, relY);

    }

    @Override
    public void addHoverListener(IHoverListener listener) {
        mRoutine.addHoverListener(listener);

    }

    @Override
    public void pressed(EClickType type, int relX, int relY) {
        if (!mHovered) {
            return;
        }
        mPressed = true;

    }

    @Override
    public void released(EClickType type, int relX, int relY) {
        if (!mHovered) {
            return;
        }
        if (!mPressed) {
            return;
        }
        mListener.forEach(l -> l.onClick(this, type));
        mPressed = false;

    }

    @Override
    public void addClickListener(IClickListener clickListener) {
        mListener.add(clickListener);

    }

    @Override
    public void removeClickListener(IClickListener clickListener) {
        mListener.remove(clickListener);
        
    }

    @Override
    public void removeHoverListener(IHoverListener listener) {
        mRoutine.removeHoverListener(listener);
        
    }
    
    @Override
    public ITile copy() {
        return new EditorTile(mType, mImage, mId);
    }
}
