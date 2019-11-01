package de.ativelox.feo.client.model.property.routine;

import java.util.ArrayList;
import java.util.List;

import de.ativelox.feo.client.model.property.ISelectable;
import de.ativelox.feo.client.model.property.callback.ISelectionListener;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class SelectableRoutine implements ISelectable {

    private final List<ISelectionListener> mSelectionListener;

    private boolean mIsSelected;

    private final int mOrder;

    private final ISelectable mCaller;

    public SelectableRoutine(ISelectable caller, int order) {
        mCaller = caller;
        mSelectionListener = new ArrayList<>();
        mIsSelected = false;

        mOrder = order;
    }

    @Override
    public void add(ISelectionListener listener) {
        mSelectionListener.add(listener);

    }

    @Override
    public void remove(ISelectionListener listener) {
        mSelectionListener.remove(listener);

    }

    @Override
    public void selected() {
        mIsSelected = true;
        mSelectionListener.forEach(s -> s.onSelect(mCaller));

    }

    @Override
    public void deSelected() {
        mIsSelected = false;
        mSelectionListener.forEach(s -> s.onDeSelect(mCaller));

    }

    @Override
    public boolean isSelected() {
        return mIsSelected;

    }

    @Override
    public int getOrder() {
        return mOrder;
    }
}
