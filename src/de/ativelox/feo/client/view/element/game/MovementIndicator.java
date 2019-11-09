package de.ativelox.feo.client.view.element.game;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.property.EIndicatorDirection;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.view.element.generic.ImageElement;
import de.ativelox.feo.util.Pair;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MovementIndicator implements IRenderable, IRequireResources {

    private final List<ImageElement> mIndicators;

    private final Map<EIndicatorDirection, Image> mImageMapping;

    public MovementIndicator() {
        mIndicators = new ArrayList<>();
        mImageMapping = new HashMap<>();

        this.load();
    }

    public void set(Iterable<Pair<ISpatial, EIndicatorDirection>> representation) {
        mIndicators.clear();
        representation.forEach(p -> add(p.getFirst(), p.getSecond()));

    }

    private void add(ISpatial fit, EIndicatorDirection direction) {
        mIndicators.add(new ImageElement(fit.getX(), fit.getY(), fit.getWidth(), fit.getHeight(), false,
                mImageMapping.get(direction)));
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mIndicators.forEach(i -> i.render(g));
//        g.drawImage(mImageMapping.get(EIndicatorDirection.BEGINNING_LEFT_RIGHT), 0, 0);

    }

    @Override
    public void load() {
        mImageMapping.put(EIndicatorDirection.BEGINNING_LEFT_RIGHT,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.BEGINNING_LEFT_RIGHT));
        mImageMapping.put(EIndicatorDirection.BEGINNING_DOWN_UP,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.BEGINNING_DOWN_UP));
        mImageMapping.put(EIndicatorDirection.BEGINNING_RIGHT_LEFT,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.BEGINNING_RIGHT_LEFT));
        mImageMapping.put(EIndicatorDirection.BEGINNING_UP_DOWN,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.BEGINNING_UP_DOWN));

        mImageMapping.put(EIndicatorDirection.ARC_DOWN_RIGHT,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.ARC_DOWN_RIGHT));
        mImageMapping.put(EIndicatorDirection.ARC_LEFT_DOWN,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.ARC_LEFT_DOWN));
        mImageMapping.put(EIndicatorDirection.ARC_LEFT_UP,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.ARC_LEFT_UP));
        mImageMapping.put(EIndicatorDirection.ARC_UP_RIGHT,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.ARC_UP_RIGHT));

        mImageMapping.put(EIndicatorDirection.END_DOWN_UP,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.END_DOWN_UP));
        mImageMapping.put(EIndicatorDirection.END_LEFT_RIGHT,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.END_LEFT_RIGHT));
        mImageMapping.put(EIndicatorDirection.END_RIGHT_LEFT,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.END_RIGHT_LEFT));
        mImageMapping.put(EIndicatorDirection.END_UP_DOWN,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.END_UP_DOWN));

        mImageMapping.put(EIndicatorDirection.VERTICAL,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.VERTICAL));
        mImageMapping.put(EIndicatorDirection.HORIZONTAL,
                Assets.getFor(EResource.MAP_INDICATOR, EIndicatorDirection.HORIZONTAL));

    }

}
