package de.ativelox.feo.client.model.util;

import java.util.HashSet;
import java.util.Set;

import de.ativelox.feo.client.model.gfx.SpatialObject;
import de.ativelox.feo.client.model.property.IHoverable;
import de.ativelox.feo.client.model.property.callback.IHoverListener;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DefaultHoverRoutine extends SpatialObject implements IHoverable {

    private final Set<IHoverListener> mListener;

    private boolean mIsHovered;

    private int mMouseX;

    private int mMouseY;

    private final IHoverable mCaller;

    public DefaultHoverRoutine(IHoverable bounds) {
        super(bounds.getX(), bounds.getWidth(), bounds.getWidth(), bounds.getHeight());

        mCaller = bounds;

        mListener = new HashSet<>();
        mIsHovered = false;

    }

    @Override
    public void hoverStart() {
        mCaller.hoverStart();

        for (final IHoverListener listener : mListener) {
            listener.onHoverStart(mCaller);

        }
        mIsHovered = true;
    }

    @Override
    public void hoverStop() {
        mCaller.hoverStop();

        for (final IHoverListener listener : mListener) {
            listener.onHoverLoss(mCaller);
        }
        mIsHovered = false;

    }

    @Override
    public void update(TimeSnapshot ts) {
        if (!mIsHovered && mCaller.contains(mMouseX, mMouseY)) {
            this.hoverStart();

        } else if (mIsHovered && !mCaller.contains(mMouseX, mMouseY)) {
            this.hoverStop();

        }

        if (mIsHovered) {
            for (final IHoverListener listener : mListener) {
                listener.onHover(mCaller);
            }
        }

    }

    @Override
    public void onMouseMoved(int relX, int relY) {
        mMouseX = relX;
        mMouseY = relY;

    }

    @Override
    public void addHoverListener(IHoverListener listener) {
        mListener.add(listener);

    }

    @Override
    public void removeHoverListener(IHoverListener listener) {
        mListener.remove(listener);

    }

}
