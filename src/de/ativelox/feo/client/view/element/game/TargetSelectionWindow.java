package de.ativelox.feo.client.view.element.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.ativelox.feo.client.controller.input.EAction;
import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.controller.input.InputReceiver;
import de.ativelox.feo.client.model.manager.VerticalSelectionManager;
import de.ativelox.feo.client.model.property.ICancelable;
import de.ativelox.feo.client.model.property.IConfirmable;
import de.ativelox.feo.client.model.property.ISelectable;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.ICancelListener;
import de.ativelox.feo.client.model.property.callback.IConfirmListener;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.model.property.callback.ISelectionListener;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class TargetSelectionWindow implements IUpdateable, ISelectionListener, IConfirmable, ICancelable {

    private final VerticalSelectionManager<IUnit> mSelectionManager;

    private IUnit mCurrentlySelected;

    private final List<IConfirmListener> mConfirmListener;

    private final List<ICancelListener> mCancelListener;

    private final InputReceiver mReceiver;

    private final Collection<IUnit> mTargets;

    private ISelectionListener mSelectionListener;

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param isPercent
     */
    public TargetSelectionWindow(Collection<IUnit> targets, ISelectionListener listener) {

        mTargets = targets;
        mSelectionListener = listener;

        mCancelListener = new ArrayList<>();
        mConfirmListener = new ArrayList<>();

        mReceiver = new InputReceiver();

        targets.forEach(t -> t.add(this));
        mSelectionManager = new VerticalSelectionManager<>(true, targets);

    }

    public void registerTo(InputManager im) {
        im.register((IMovementListener) mSelectionManager);
        im.register((IActionListener) mReceiver);

    }

    public void reset() {
        mSelectionListener = null;
        mTargets.forEach(t -> mSelectionManager.remove(t));
        mTargets.forEach(t -> t.remove(this));
    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mReceiver.isActiveInitially(EAction.CONFIRMATION)) {
            this.confirm();

        } else if (mReceiver.isActiveInitially(EAction.CANCEL)) {
            this.cancel();

        }
        mSelectionManager.update(ts);

        mReceiver.cycleFinished();

    }

    @Override
    public void onSelect(ISelectable selectable) {
        if (selectable instanceof IUnit) {
            mCurrentlySelected = (IUnit) selectable;

            if (mSelectionListener != null) {
                mSelectionListener.onSelect(selectable);
            }
        }
    }

    @Override
    public void onDeSelect(ISelectable selectable) {
        // TODO Auto-generated method stub

    }

    @Override
    public void cancel() {
        mCancelListener.forEach(l -> l.onCancel(this));

    }

    @Override
    public void addCancelListener(ICancelListener listener) {
        mCancelListener.add(listener);

    }

    @Override
    public void removeCancelListener(ICancelListener listener) {
        mCancelListener.remove(listener);

    }

    @Override
    public void confirm() {
        mConfirmListener.forEach(l -> l.onConfirm(this));

    }

    @Override
    public void add(IConfirmListener listener) {
        mConfirmListener.add(listener);

    }

    @Override
    public void remove(IConfirmListener listener) {
        mConfirmListener.remove(listener);

    }

    public IUnit getSelectedUnit() {
        return mCurrentlySelected;
    }

}
