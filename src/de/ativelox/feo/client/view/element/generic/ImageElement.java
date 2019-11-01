package de.ativelox.feo.client.view.element.generic;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.IRequireResource;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ImageElement extends AScreenElement implements IRequireResource<Image> {

    private final EResource mImageResource;
    private final Image mImage;

    public ImageElement(int x, int y, int width, int height, boolean isPercent, EResource image) {
        super(x, y, width, height, isPercent);
        mImageResource = image;

        mImage = request();
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        g.drawImage(mImage, getX(), getY(), getWidth(), getHeight());

    }

    @Override
    public EResource getResourceTypes() {
        return mImageResource;

    }
}
