package de.ativelox.feo.client.controller.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.ativelox.feo.client.model.camera.Camera;
import de.ativelox.feo.client.model.property.IPriorityUpdateable;
import de.ativelox.feo.client.model.property.callback.IActionListener;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.model.property.callback.IPanningListener;
import de.ativelox.feo.client.model.property.callback.IRelativeMouseMoveListener;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.model.util.UpdatePriority;
import de.ativelox.feo.util.Pair;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class InputManager implements IPriorityUpdateable, KeyListener, MouseMotionListener {

    private final List<IMovementListener> mMovementListener;
    private final List<IPanningListener> mPanningListener;
    private final List<IActionListener> mActionListener;
    private final List<IRelativeMouseMoveListener> mMouseMoveListener;

    private final Set<Integer> mPressedKeys;
    private final Set<Integer> mReleasedKeys;

    private final List<IMovementListener> mTempMovementAdd;
    private final List<IMovementListener> mTempMovementRemove;

    private final List<IPanningListener> mTempPanAdd;
    private final List<IPanningListener> mTempPanRemove;

    private final List<IActionListener> mTempActionAdd;
    private final List<IActionListener> mTempActionRemove;

    private final List<IRelativeMouseMoveListener> mTempRelAdd;
    private final List<IRelativeMouseMoveListener> mTempRelRemove;

    private int mMouseX;
    private int mMouseY;

    private boolean mIsKeyboard;

    private final Camera mCamera;

    public InputManager(final Camera camera) {
        mIsKeyboard = true;

        mPressedKeys = new HashSet<>();
        mReleasedKeys = new HashSet<>();

        mMovementListener = new ArrayList<>();
        mPanningListener = new ArrayList<>();
        mActionListener = new ArrayList<>();
        mMouseMoveListener = new ArrayList<>();

        mTempMovementAdd = new ArrayList<>();
        mTempMovementRemove = new ArrayList<>();

        mTempPanAdd = new ArrayList<>();
        mTempPanRemove = new ArrayList<>();

        mTempActionAdd = new ArrayList<>();
        mTempActionRemove = new ArrayList<>();

        mTempRelAdd = new ArrayList<>();
        mTempRelRemove = new ArrayList<>();

        mCamera = camera;

    }

    public boolean register(final IRelativeMouseMoveListener listener) {
        return mTempRelAdd.add(listener);
    }

    public boolean remove(final IRelativeMouseMoveListener listener) {
        return mTempRelRemove.add(listener);
    }

    public boolean register(final IMovementListener listener) {
        return mTempMovementAdd.add(listener);

    }

    public boolean remove(final IMovementListener listener) {
        return mTempMovementRemove.add(listener);

    }

    public boolean register(final IPanningListener listener) {
        return mTempPanAdd.add(listener);

    }

    public boolean remove(final IPanningListener listener) {
        return mTempPanRemove.add(listener);
    }

    public boolean register(final IActionListener listener) {
        return mTempActionAdd.add(listener);

    }

    public boolean remove(final IActionListener listener) {
        return mTempActionRemove.add(listener);
    }

    @Override
    public void update(final TimeSnapshot ts) {
        Pair<Double, Double> movement = null;
        Pair<Double, Double> panning = null;
        Set<EAction> pressedActions = null;
        Set<EAction> releasedActions = null;

        if (mIsKeyboard) {
            movement = InputMapper.getMovement(new HashSet<>(mPressedKeys));
            panning = InputMapper.getPanning(new HashSet<>(mPressedKeys));
            pressedActions = InputMapper.getAction(new HashSet<>(mPressedKeys));
            releasedActions = InputMapper.getAction(new HashSet<>(mReleasedKeys));

        } else {
            // fetch inputs for controller.

        }
        boolean hasPanningX = panning.getFirst() != 0;
        boolean hasPanningY = panning.getSecond() != 0;

        boolean hasPressedActions = pressedActions.size() > 0;
        boolean hasReleasedActions = releasedActions.size() > 0;

        for (final IMovementListener listener : mMovementListener) {
            listener.onMoveX(movement.getFirst());
            listener.onMoveY(movement.getSecond());

        }

        if (hasPanningX || hasPanningY)

        {
            for (

            final IPanningListener listener : mPanningListener) {
                if (hasPanningX) {
                    listener.onPanX(panning.getFirst());

                }
                if (hasPanningY) {
                    listener.onPanY(panning.getSecond());

                }
            }
        }

        if (hasPressedActions || hasReleasedActions) {
            for (final IActionListener listener : mActionListener) {
                if (hasPressedActions) {
                    listener.onPress(pressedActions);
                }
                if (hasReleasedActions) {
                    listener.onRelease(releasedActions);
                }
            }
        }

        if (mMouseX != 0 || mMouseY != 0) {
            Pair<Integer, Integer> values = mCamera.invert(mMouseX, mMouseY);

            for (final IRelativeMouseMoveListener listener : mMouseMoveListener) {
                listener.onMouseMoved(values.getFirst(), values.getSecond());

            }
        }

        for (final IMovementListener mov : mTempMovementRemove) {
            mMovementListener.remove(mov);
        }
        for (final IMovementListener mov : mTempMovementAdd) {
            mMovementListener.add(mov);
        }

        for (final IRelativeMouseMoveListener mov : mTempRelRemove) {
            mMouseMoveListener.remove(mov);
        }
        for (final IRelativeMouseMoveListener mov : mTempRelAdd) {
            mMouseMoveListener.add(mov);
        }

        for (final IPanningListener mov : mTempPanRemove) {
            mPanningListener.remove(mov);
        }
        for (final IPanningListener mov : mTempPanAdd) {
            mPanningListener.add(mov);
        }

        for (final IActionListener mov : mTempActionRemove) {
            mActionListener.remove(mov);
        }
        for (final IActionListener mov : mTempActionAdd) {
            mActionListener.add(mov);
        }

        mMouseX = 0;
        mMouseY = 0;
        mReleasedKeys.clear();

        mTempActionAdd.clear();
        mTempActionRemove.clear();
        mTempMovementAdd.clear();
        mTempMovementRemove.clear();
        mTempPanAdd.clear();
        mTempPanRemove.clear();
        mTempRelAdd.clear();
        mTempRelRemove.clear();
    }

    @Override
    public int getPriority() {
        return UpdatePriority.INPUT;

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not needed.

    }

    @Override
    public void keyPressed(KeyEvent e) {
        mIsKeyboard = true;
        mPressedKeys.add(e.getKeyCode());

    }

    @Override
    public void keyReleased(KeyEvent e) {
        mIsKeyboard = true;
        mReleasedKeys.add(e.getKeyCode());
        mPressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // not needed;

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mIsKeyboard = true;
        mMouseX = e.getX();
        mMouseY = e.getY();

    }
}
