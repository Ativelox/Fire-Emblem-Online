package de.ativelox.feo.client.model.manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.ativelox.feo.client.controller.input.EAxis;
import de.ativelox.feo.client.controller.input.InputReceiver;
import de.ativelox.feo.client.model.property.ISelectable;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class VerticalSelectionManager extends InputReceiver implements IUpdateable {

    private final List<ISelectable> mSelectables;

    private int mSelectionIndex;

    private boolean mIsCircular;

    public VerticalSelectionManager(boolean circular, ISelectable... selectables) {
        mSelectables = new ArrayList<>();

        for (final ISelectable selectable : selectables) {
            mSelectables.add(selectable);
        }
        mSelectionIndex = 0;
        mIsCircular = circular;

        if (mSelectables.size() > 0) {
            this.resort();
            this.updateSelected(mSelectionIndex);
        }
    }

    private void resort() {
        mSelectables.sort(Comparator.comparing(ISelectable::getOrder));
    }

    public void add(ISelectable selectable) {
        mSelectables.add(selectable);
        if (selectable.getOrder() > mSelectables.get(mSelectionIndex).getOrder()) {
            mSelectionIndex++;
        }
        resort();

    }

    public void remove(ISelectable selectable) {
        mSelectables.remove(selectable);
        if (selectable.getOrder() < mSelectables.get(mSelectionIndex).getOrder()) {
            mSelectionIndex--;
        }
    }

    public void updateSelected(int lastIndex) {
        mSelectables.get(lastIndex).deSelected();
        mSelectables.get(mSelectionIndex).selected();

    }

    @Override
    public void update(TimeSnapshot ts) {
        int lastIndex = mSelectionIndex;

        if (getMovement(EAxis.Y) == 1 || getMovement(EAxis.Y) == -1) {
            if (mSelectionIndex + (int) getMovement(EAxis.Y) < 0) {
                if (mIsCircular) {
                    mSelectionIndex = mSelectables.size() - 1;

                } else {
                    mSelectionIndex = 0;
                }

            } else if (mSelectionIndex + (int) getMovement(EAxis.Y) > mSelectables.size() - 1) {
                if (mIsCircular) {
                    mSelectionIndex = 0;

                } else {
                    mSelectionIndex = mSelectables.size() - 1;
                }
            } else {
                mSelectionIndex += getMovement(EAxis.Y);

            }
            this.updateSelected(lastIndex);

        }
        cycleFinished();
    }
}
