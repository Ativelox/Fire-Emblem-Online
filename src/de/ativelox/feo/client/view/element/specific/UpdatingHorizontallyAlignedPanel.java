package de.ativelox.feo.client.view.element.specific;

import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class UpdatingHorizontallyAlignedPanel<T extends ISpatial & IRenderable & IUpdateable>
        extends HorizontallyAlignedPanel<T> implements IUpdateable {

    /**
     * @param percentX
     * @param percentY
     * @param percentWidth
     * @param percentHeight
     * @param spacing
     */
    public UpdatingHorizontallyAlignedPanel(int percentX, int percentY, int percentWidth, int percentHeight,
            int spacing) {
        super(percentX, percentY, percentWidth, percentHeight, spacing);
    }

    @Override
    public void update(TimeSnapshot ts) {
        mObjects.forEach(o -> o.update(ts));

    }

}
