package de.ativelox.feo.client.view.element.specific;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.callback.IClickListener;
import de.ativelox.feo.client.model.property.callback.IHoverListener;
import de.ativelox.feo.client.model.util.DefaultHoverRoutine;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Button extends SpatialObject implements IHoverable, IClickable, IRenderable, IRequireResources {

    private final IHoverable mRoutine;

    private final String mText;

    private boolean mHovered;

    private boolean mPressed;

    private final List<IClickListener> mListener;

    private Color mCurrent;

    private final Color mDefault;

    private BufferedImage mFormattedText;

    private int mTextHeight;

    private int mTextWidth;

    public Image mSelector;

    /**
     * @param xPercent
     * @param yPercent
     * @param widthPercent
     * @param heightPercent
     */
    public Button(String text, int xPercent, int yPercent, int widthPercent, int heightPercent) {
        super((int) ((xPercent / 100f) * Display.WIDTH), (int) ((yPercent / 100f) * Display.HEIGHT),
                (int) ((widthPercent / 100f) * Display.WIDTH), (int) ((heightPercent / 100f) * Display.WIDTH));

        mRoutine = new DefaultHoverRoutine(this);
        mListener = new ArrayList<>();

        mDefault = new Color(255, 255, 255, 0);
        mCurrent = mDefault;

        mText = text;

        this.load();
    }

    @Override
    public void update(TimeSnapshot ts) {
        mRoutine.update(ts);

        if (mPressed) {
            mCurrent = new Color(200, 200, 200, 50);
        }

    }

    @Override
    public void onMouseMoved(int relX, int relY) {
        mRoutine.onMouseMoved(relX, relY);

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
        mCurrent = new Color(255, 255, 255, 50);
        mPressed = false;
        mListener.forEach(c -> c.onClick(this, type));

    }

    @Override
    public void hoverStart() {
        mCurrent = new Color(255, 255, 255, 50);
        mHovered = true;
    }

    @Override
    public void hoverStop() {
        mCurrent = mDefault;
        mHovered = false;

    }

    @Override
    public void addHoverListener(IHoverListener listener) {
        mRoutine.addHoverListener(listener);

    }

    @Override
    public void addClickListener(IClickListener clickListener) {
        mListener.add(clickListener);

    }

    @Override
    public void render(DepthBufferedGraphics g) {

        g.drawImage(mSelector, getX(), getY(), getWidth(), getHeight());

        g.fillRect(mCurrent, getX() + 2, getY() + 4, getWidth() - 5, getHeight() - 7);

        g.drawImage(mFormattedText, getX() + (getWidth() / 2) - (mTextWidth / 2),
                getY() + (getHeight() / 2) - (mTextHeight / 2), mTextWidth, mTextHeight);

    }

    public String getText() {
        return mText;
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
    public void load() {
        mFormattedText = Assets.getFor(EResource.REGULAR_FONT, mText);

        int width = mFormattedText.getWidth();
        int height = mFormattedText.getHeight();

        float ratio = ((float) height) / width;

        mTextWidth = this.getWidth() - 3 * Display.INTERNAL_RES_FACTOR;
        mTextHeight = (int) (mTextWidth * ratio);

        mSelector = Assets.getFor(EResource.MENU_BUTTON);

    }
}
