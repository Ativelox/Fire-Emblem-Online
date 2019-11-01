package de.ativelox.feo.client.view.element.specific;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.SpatialObject;
import de.ativelox.feo.client.model.property.EClickType;
import de.ativelox.feo.client.model.property.IClickable;
import de.ativelox.feo.client.model.property.IHoverable;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.IRequireResource;
import de.ativelox.feo.client.model.property.callback.IClickListener;
import de.ativelox.feo.client.model.property.callback.IHoverListener;
import de.ativelox.feo.client.model.util.DefaultHoverRoutine;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public abstract class AInputField<T> extends SpatialObject
        implements KeyListener, IHoverable, IRenderable, IClickable, IRequireResource<Image> {

    private final IHoverable mRoutine;

    private final List<Character> mToAdd;

    protected String mContent;

    private int mToRemove;

    private final Color mBase;

    private Color mCurrent;

    private boolean mHovered;

    private final int mLimit;

    private boolean mFocused;

    private boolean mPressed;

    private List<IClickListener> mListener;

    private Image mFormattedContent;

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public AInputField(int percentX, int percentY, int percentWidth, int fontSize, int limit) {
        super((int) ((percentX / 100f) * Display.WIDTH), (int) ((percentY / 100f) * Display.HEIGHT),
                (int) ((percentWidth / 100f) * Display.WIDTH), fontSize + 6);

        mListener = new ArrayList<>();

        mContent = "";
        mToAdd = new ArrayList<>();

        mRoutine = new DefaultHoverRoutine(this);

        mBase = new Color(0, 0, 200);
        mCurrent = mBase;
        mLimit = limit;
    }

    protected abstract boolean validInput(final char input);

    public abstract T getContent();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!mFocused) {
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            mToRemove++;
            return;
        }
        if (mContent.length() >= mLimit || !validInput(e.getKeyChar())) {
            return;
        }
        mToAdd.add(e.getKeyChar());

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(TimeSnapshot ts) {
        mRoutine.update(ts);

        if (mContent.length() > 0) {
            mContent = mContent.substring(0, mContent.length() - mToRemove);
        }
        mToAdd.forEach(c -> mContent += c);

        if (mToRemove > 0 || mToAdd.size() > 0) {
            mFormattedContent = request();
        }

        mToRemove = 0;
        mToAdd.clear();

    }

    @Override
    public void onMouseMoved(int relX, int relY) {
        mRoutine.onMouseMoved(relX, relY);

    }

    @Override
    public void hoverStart() {
        mHovered = true;
        mCurrent = new Color(0, 0, 160);

    }

    @Override
    public void hoverStop() {
        mHovered = false;
        mCurrent = mBase;

    }

    @Override
    public void addHoverListener(IHoverListener listener) {
        mRoutine.addHoverListener(listener);

    }

    @Override
    public void removeHoverListener(IHoverListener listener) {
        mRoutine.removeHoverListener(listener);
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        g.fillRect(mCurrent, getX(), getY(), getWidth(), getHeight());

        if (mFocused) {
            g.fillRect(new Color(0, 0, 100), getX() + 3, getY() + 3, getWidth() - 5, getHeight() - 5);

        }
        g.drawImage(mFormattedContent, getX() + 3, getY(), getWidth() - 5, getHeight());

    }

    @Override
    public void pressed(EClickType type, int relX, int relY) {
        if (type != EClickType.LEFT) {
            return;
        }

        if (!mHovered) {
            return;
        }
        if (!mPressed) {
            return;
        }
        mPressed = true;

    }

    @Override
    public void released(EClickType type, int relX, int relY) {
        if (type != EClickType.LEFT) {
            return;
        }

        if (!mHovered) {
            mFocused = false;
            return;
        }
        mListener.forEach(l -> l.onClick(this, EClickType.LEFT));
        mFocused = true;
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
    public EResource getResourceTypes() {
        return EResource.REGULAR_FONT;
    }

    @Override
    public Image request() {
        return Assets.getFor(this, mContent);
    }

}
