package de.ativelox.feo.client.view.element.generic;

import java.awt.image.BufferedImage;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.IConfirmable;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.ISelectable;
import de.ativelox.feo.client.model.property.callback.IConfirmListener;
import de.ativelox.feo.client.model.property.callback.ISelectionListener;
import de.ativelox.feo.client.model.property.routine.ConfirmableRoutine;
import de.ativelox.feo.client.model.property.routine.SelectableRoutine;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public abstract class AButtonElement extends ImageElement implements ISelectable, IConfirmable, IRequireResources {

    private final ConfirmableRoutine mConfirmableRoutine;

    private final SelectableRoutine mSelectableRoutine;

    private final String mText;

    private BufferedImage mFormattedText;

    private int mTextWidth;

    private int mTextHeight;

    protected int mXOffset;

    protected int mYOffset;

    public AButtonElement(int x, int y, int width, int height, boolean isPercent, EResource background, int order,
            String text) {
        super(x, y, width, height, isPercent, background);

        mText = text;

        mConfirmableRoutine = new ConfirmableRoutine(this);
        mSelectableRoutine = new SelectableRoutine(this, order);

        if (!text.isEmpty()) {
            load();
        }

        adjustTextWidth(mFormattedText.getWidth());
        adjustTextHeight(mFormattedText.getHeight());
    }

    public AButtonElement(int x, int y, int width, int height, boolean isPercent, EResource background, int order) {
        this(x, y, width, height, isPercent, background, order, "");
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        super.render(g);

        if (this.isSelected()) {
            renderSelection(g);

        }

        if (mFormattedText == null) {
            return;
        }

        g.drawImage(mFormattedText, mXOffset + getX() + (getWidth() / 2) - (mTextWidth / 2),
                mYOffset + getY() + (getHeight() / 2) - (mTextHeight / 2), mTextWidth, mTextHeight);

    }

    public void adjustTextWidth(int width) {
        mTextWidth = width;
    }

    public void adjustTextHeight(int height) {
        mTextHeight = height;

    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
    }

    protected abstract void renderSelection(DepthBufferedGraphics g);

    @Override
    public void confirm() {
        mConfirmableRoutine.confirm();

    }

    @Override
    public void add(IConfirmListener listener) {
        mConfirmableRoutine.add(listener);

    }

    @Override
    public void remove(IConfirmListener listener) {
        mConfirmableRoutine.remove(listener);

    }

    @Override
    public void selected() {
        mSelectableRoutine.selected();

    }

    @Override
    public void deSelected() {
        mSelectableRoutine.deSelected();

    }

    @Override
    public boolean isSelected() {
        return mSelectableRoutine.isSelected();
    }

    @Override
    public int getOrder() {
        return mSelectableRoutine.getOrder();
    }

    @Override
    public void add(ISelectionListener listener) {
        mSelectableRoutine.add(listener);

    }

    @Override
    public void remove(ISelectionListener listener) {
        mSelectableRoutine.remove(listener);

    }

    @Override
    public void load() {
        mFormattedText = Assets.getFor(EResource.REGULAR_FONT, mText);
    }

    public String getText() {
        return mText;
    }
}
