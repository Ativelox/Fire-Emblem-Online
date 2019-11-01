package de.ativelox.feo.client.view.element.specific;

import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISpatial;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class HorizontallyAlignedPanel<T extends IRenderable & ISpatial> extends APanel<T> {

    private final int mSpacing;

    /**
     * @param percentX
     * @param percentY
     * @param percentWidth
     * @param percentHeight
     */
    public HorizontallyAlignedPanel(int percentX, int percentY, int percentWidth, int percentHeight, int spacing) {
        super(percentX, percentY, percentWidth, percentHeight);

        mSpacing = spacing;
    }

    @Override
    protected void align() {
        if (mObjects.size() <= 0) {
            return;
        }

        float sampleWidth = mObjects.get(0).getWidth() + mSpacing;

        while (Math.floor((getWidth() / sampleWidth)) * Math.floor((getHeight() / sampleWidth)) < mObjects.size()) {
            sampleWidth--;

        }
        int column = 0;
        int row = 0;

        for (final T object : mObjects) {
            object.setWidth((int) sampleWidth - mSpacing);
            object.setHeight((int) sampleWidth - mSpacing);

            if (column > 0 && column % (getWidth() / object.getWidth()) == 0) {
                column = 0;
                row++;
            }
            object.setX(getX() + (column * (object.getWidth() + mSpacing)));
            object.setY(getY() + (row * (object.getHeight() + mSpacing)));

            column++;
        }
    }
}
