package de.ativelox.feo.client.view.screen.editor;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import de.ativelox.feo.client.controller.MapEditorController;
import de.ativelox.feo.client.model.camera.ECameraApplication;
import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.tile.ITile;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.screen.EScreen;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MapEditorUIScreen implements IMapEditorUIScreen, IRequireResources {

    private List<ISpatial> mBorders;

    private ITile mStatTile;

    private MapEditorController mController;

    private String mCost;
    private String mAvoidance;
    private String mHealing;
    private String mId;

    private Image mFormattedCost;
    private Image mFormattedAvoidance;
    private Image mFormattedHealing;
    private Image mFormattedId;

    private boolean mRemove;
    private boolean mAdd;

    public MapEditorUIScreen() {
        mBorders = new ArrayList<>();
        mController = null;
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        if (mController == null) {
            return;
        }
        mBorders.forEach(s -> g.drawRect(Color.red, s.getX(), s.getY(), s.getWidth(), s.getHeight()));

        if (mStatTile == null || mFormattedAvoidance == null) {
            return;
        }

        g.drawImage(mFormattedCost, 0, 0,
                (int) ((3 / 2f) * mFormattedCost.getWidth(null) / Display.INTERNAL_RES_FACTOR),
                (int) ((3 / 2f) * mFormattedCost.getHeight(null) / Display.INTERNAL_RES_FACTOR));

        g.drawImage(mFormattedAvoidance, 0,
                (int) ((3 / 2f) * mFormattedCost.getHeight(null) / Display.INTERNAL_RES_FACTOR),
                (int) ((3 / 2f) * mFormattedAvoidance.getWidth(null) / Display.INTERNAL_RES_FACTOR),
                (int) ((3 / 2f) * mFormattedAvoidance.getHeight(null) / Display.INTERNAL_RES_FACTOR));

        g.drawImage(mFormattedHealing, 0,
                (int) ((3 / 2f) * mFormattedCost.getHeight(null) / Display.INTERNAL_RES_FACTOR)
                        + (int) ((3 / 2f) * mFormattedAvoidance.getHeight(null) / Display.INTERNAL_RES_FACTOR),
                (int) ((3 / 2f) * mFormattedHealing.getWidth(null) / Display.INTERNAL_RES_FACTOR),
                (int) ((3 / 2f) * mFormattedHealing.getHeight(null) / Display.INTERNAL_RES_FACTOR));

        g.drawImage(mFormattedId, 0,
                (int) ((3 / 2f) * mFormattedCost.getHeight(null) / Display.INTERNAL_RES_FACTOR)
                        + (int) ((3 / 2f) * mFormattedAvoidance.getHeight(null) / Display.INTERNAL_RES_FACTOR)
                        + (int) ((3 / 2f) * mFormattedHealing.getHeight(null) / Display.INTERNAL_RES_FACTOR),
                (int) ((3 / 2f) * mFormattedId.getWidth(null) / Display.INTERNAL_RES_FACTOR),
                (int) ((3 / 2f) * mFormattedId.getHeight(null) / Display.INTERNAL_RES_FACTOR));

    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mController == null) {
            return;
        }

        if (mRemove && mAdd) {
            mRemove = false;
            mAdd = false;
            return;
        }

        if (mRemove) {
            mStatTile = null;
        }

    }

    @Override
    public void startRenderingBorder(ISpatial spatial) {
        mBorders.add(spatial);
    }

    @Override
    public void stopRenderingBorder(ISpatial spatial) {
        mBorders.remove(spatial);
    }

    @Override
    public void startDisplayingStats(ITile tile) {
        mStatTile = tile;
        mCost = "Cost: " + mStatTile.getCost() + "";
        mAvoidance = "Avoidance: " + mStatTile.getAvoidance() + "";
        mHealing = "Healing: " + mStatTile.getHealing() + "";
        mId = "Id: " + mStatTile.getId() + "";
        load();
        mAdd = true;
    }

    @Override
    public void stopDisplayingStats(ITile tile) {
        mRemove = true;
    }

    @Override
    public void setController(MapEditorController controller) {
        mController = controller;
    }

    @Override
    public EScreen getIdentifier() {
        return EScreen.MAP_EDITOR_SCREEN;
    }

    @Override
    public boolean renderLower() {
        return true;
    }

    @Override
    public boolean updateLower() {
        return true;
    }

    @Override
    public ECameraApplication cameraApplied() {
        return ECameraApplication.WINDOW_RESIZE_ONLY;

    }

    @Override
    public void load() {
        mFormattedAvoidance = Assets.getFor(EResource.REGULAR_FONT, mAvoidance);
        mFormattedCost = Assets.getFor(EResource.REGULAR_FONT, mCost);
        mFormattedHealing = Assets.getFor(EResource.REGULAR_FONT, mHealing);
        mFormattedId = Assets.getFor(EResource.REGULAR_FONT, mId);

    }
}
