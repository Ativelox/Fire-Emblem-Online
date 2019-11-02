package de.ativelox.feo.client.model.camera;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import de.ativelox.feo.client.model.property.IPriorityUpdateable;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.callback.IMovementListener;
import de.ativelox.feo.client.model.property.callback.IPanningListener;
import de.ativelox.feo.client.model.property.callback.IResizeListener;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.model.util.UpdatePriority;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.logging.Logger;
import de.ativelox.feo.util.Pair;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Camera implements IPriorityUpdateable, IMovementListener, IPanningListener, IResizeListener {

    private double mTranslationX;
    private double mTranslationY;

    private double mScale;

    private double mResizeScaleX;
    private double mResizeScaleY;

    private double mShearX;
    private double mShearY;

    private AffineTransform mCameraTransform;

    private AffineTransform mResizeMatrix;

    private AffineTransform mAll;

    private ISpatial mFollow;

    public static final AffineTransform IDENTITY = new AffineTransform();

    private ISpatial mBounds;

    public Camera() {
        mCameraTransform = new AffineTransform();
        mResizeMatrix = new AffineTransform();
        mAll = new AffineTransform();

        mTranslationX = 0;
        mTranslationY = 0;

        mScale = 2;

        mShearX = 0;
        mShearY = 0;

        mResizeScaleX = 1;
        mResizeScaleY = 1;
    }

    @Override
    public void update(TimeSnapshot ts) {
        // make sure all the values are valid, e.g. not clipping out of bounds.
        validateValues();

        // update the underlying matrix defined by the affine transform.
        mCameraTransform.setTransform(mScale, mShearY, mShearX, mScale, mTranslationX, mTranslationY);

        mResizeMatrix.setToScale(mResizeScaleX, mResizeScaleY);

        mAll.setTransform((mScale - 1) * mResizeScaleX, mShearY, mShearX, (mScale - 1) * mResizeScaleY, mTranslationX,
                mTranslationY);
    }

    public void ensureInViewport(ISpatial follow) {
        mFollow = follow;
    }

    private void validateValues() {
        if (mBounds == null) {
            return;
        }

        if (mTranslationX > 0) {
            mTranslationX = 0;
        }

        // adjust scale, if scale alone is enough to make the camera go out of bounds.
        if ((mScale - 1) * mBounds.getHeight() < Display.HEIGHT) {
            mScale = ((float) Display.HEIGHT) / (mBounds.getHeight()) + 1;

        }
        // now we know that the scale is valid, thus we can check for the translation
        // "isolated".
        else if ((mScale - 1) * mBounds.getHeight() + mTranslationY < Display.HEIGHT) {
            mTranslationY = Display.HEIGHT - (mScale - 1) * mBounds.getHeight();

        }

        if ((mScale - 1) * mBounds.getWidth() < Display.WIDTH) {
            mScale = ((float) Display.WIDTH) / mBounds.getWidth() + 1;

        } else if ((mScale - 1) * mBounds.getWidth() + mTranslationX < Display.WIDTH) {
            mTranslationX = Display.WIDTH - (mScale - 1) * mBounds.getWidth();
        }

        if (mTranslationY > 0) {
            mTranslationY = 0;
        }

        if (mFollow != null) {
            if ((mFollow.getX() + mFollow.getWidth()) * (mScale - 1) + mTranslationX > Display.WIDTH) {
                mTranslationX -= mFollow.getWidth();
            }
            if ((mFollow.getX() + mFollow.getWidth()) * (mScale - 1) + mTranslationX <= 0) {
                mTranslationX += mFollow.getWidth();
            }

            if ((mFollow.getY() + mFollow.getHeight()) * (mScale - 1) + mTranslationY > Display.HEIGHT) {
                mTranslationY -= mFollow.getHeight();
            }

            if ((mFollow.getY() + mFollow.getHeight()) * (mScale - 1) + mTranslationY <= 0) {
                mTranslationY += mFollow.getWidth();
            }

        }

    }

    public Pair<Integer, Integer> invert(int x, int y) {
        Point dest = new Point(x, y);

        try {
            mResizeMatrix.inverseTransform(new Point(x, y), dest);

        } catch (NoninvertibleTransformException e) {
            Logger.get().logError("Matrix couldn't be inverted.", e);

        }
        return Pair.of((int) dest.getX(), (int) dest.getY());

    }

    @Override
    public int getPriority() {
        return UpdatePriority.CAMERA;

    }

    @Override
    public void onMoveX(double amount) {

    }

    @Override
    public void onMoveY(double amount) {

    }

    @Override
    public void onPanX(double amount) {

    }

    @Override
    public void onPanY(double amount) {
        mScale += mScale * 1 / 100f * amount;
        mScale = Math.max(-1, mScale);

    }

    public AffineTransform getCameraTransform() {
        return mCameraTransform;
    }

    public AffineTransform getAllTransform() {
        return mAll;
    }

    public AffineTransform getResizeTransform() {
        return mResizeMatrix;
    }

    @Override
    public void onResize(double xFactor, double yFactor) {
        mResizeScaleX = xFactor;
        mResizeScaleY = yFactor;
    }

    public void setBounds(ISpatial spatial) {
        mBounds = spatial;
    }
}
