package de.ativelox.feo.client.controller.input;

import java.util.HashSet;
import java.util.Set;

import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.model.property.callback.IPanningListener;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * Consumes incoming inputs and provides helpful methods to ease input checking
 * for extending classes.
 * 
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class InputReceiver implements IMovementListener, IPanningListener, IActionListener {

    /**
     * Contains all actions that are active in the given cycle.
     */
    private final Set<EAction> mPressedActions;

    /**
     * Contains all actions whose state has been changed from disabled to active
     * this update cycle.
     */
    private final Set<EAction> mInitialPressedActions;

    private double mMovementX;

    private double mMovementY;

    private double mPanningX;

    private double mPanningY;

    private double mLastMovementX;

    private double mLastMovementY;

    /**
     * Creates a new {@link InputReceiver}. Extending classes should make sure to
     * properly call {@link InputReceiver#cycleFinished()}.
     */
    public InputReceiver() {
        mPressedActions = new HashSet<>();
        mInitialPressedActions = new HashSet<>();

        mMovementX = 0;
        mMovementY = 0;
        mPanningX = 0;
        mPanningY = 0;

    }

    /**
     * Checks whether the given action is active in the current cycle or not as
     * specified by {@link InputReceiver#cycleFinished()}.
     * 
     * @param action The action to check.
     * @return <tt>True</tt> if the action is active, <tt>false</tt> otherwise.
     */
    private boolean isActive(EAction action) {
        return mPressedActions.contains(action);

    }

    /**
     * Checks whether the given action was set to active in the current cycle or not
     * as specified by {@link InputReceiver#cycleFinished()}.
     * 
     * @param action The action to check.
     * @return <tt>True</tt> if the action is initially active, <tt>false</tt>
     *         otherwise.
     */
    private boolean isActiveInitially(EAction action) {
        return mInitialPressedActions.contains(action);
    }

    /**
     * Checks whether all the given action are active in the current cycle or not as
     * specified by {@link InputReceiver#cycleFinished()}.
     * 
     * @param action The action to check.
     * @return <tt>True</tt> if the action is initially active, <tt>false</tt>
     *         otherwise.
     */
    protected boolean isActiveInitially(EAction... actions) {
        for (final EAction action : actions) {
            if (!isActiveInitially(action)) {
                return false;
            }
        }
        return true;

    }

    protected double getMovement(EAxis axis) {
        if (axis == EAxis.X) {
            return mMovementX;

        }
        if (axis == EAxis.Y) {
            return mMovementY;

        }
        Logger.get().log(ELogType.ERROR, "Couldn't parse the axis " + axis + " for movement input.");
        return 0;

    }

    protected double getPanning(EAxis axis) {
        if (axis == EAxis.X) {
            return mPanningX;

        }
        if (axis == EAxis.Y) {
            return mPanningY;
        }
        Logger.get().log(ELogType.ERROR, "Couldn't parse the axis " + axis + " for panning input.");
        return 0;

    }

    /**
     * Checks whether all the given action were set to active in the current cycle
     * or not as specified by {@link InputReceiver#cycleFinished()}.
     * 
     * @param action The action to check.
     * @return <tt>True</tt> if the action is initially active, <tt>false</tt>
     *         otherwise.
     */
    protected boolean isActive(EAction... actions) {
        for (final EAction action : actions) {
            if (!isActive(action)) {
                return false;
            }
        }
        return true;

    }

    @Override
    public void onPress(Set<EAction> action) {
        mPressedActions.addAll(action);

    }

    @Override
    public void onRelease(Set<EAction> action) {
        mPressedActions.removeAll(action);
        mInitialPressedActions.addAll(action);

    }

    @Override
    public void onPanX(double amount) {
        mPanningX = amount;

    }

    @Override
    public void onPanY(double amount) {
        mPanningY = amount;

    }

    @Override
    public void onMoveX(double amount) {
        if (amount == mLastMovementX) {
            return;
        }
        mMovementX = amount;
        mLastMovementX = amount;

    }

    @Override
    public void onMoveY(double amount) {
        if (amount == mLastMovementY) {
            return;
        }
        mMovementY = amount;
        mLastMovementY = amount;

    }

    /**
     * Signifies that a cycle has been finished, and cycle dependent inputs are
     * being reset.
     */
    protected void cycleFinished() {
        mInitialPressedActions.clear();
        mMovementX = 0;
        mMovementY = 0;
        mPanningX = 0;
        mPanningY = 0;

    }
}
