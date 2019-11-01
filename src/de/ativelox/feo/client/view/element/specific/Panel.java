package de.ativelox.feo.client.view.element.specific;

import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISpatial;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Panel<T extends ISpatial & IRenderable> extends APanel<T> {

    public Panel(int percentX, int percentY, int percentWidth, int percentHeight) {
        super(percentX, percentY, percentWidth, percentHeight);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void align() {
        return;

    }

}
