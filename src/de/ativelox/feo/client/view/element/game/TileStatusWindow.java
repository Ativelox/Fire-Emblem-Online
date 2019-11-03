package de.ativelox.feo.client.view.element.game;

import java.awt.Image;

import de.ativelox.feo.client.model.gfx.Assets;
import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.EResource;
import de.ativelox.feo.client.model.gfx.tile.ETileType;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.ESide;
import de.ativelox.feo.client.model.property.ICanSwitchSides;
import de.ativelox.feo.client.model.property.IRequireResources;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.util.TimeSnapshot;
import de.ativelox.feo.client.view.Display;
import de.ativelox.feo.client.view.element.generic.AScreenElement;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class TileStatusWindow extends AScreenElement implements IRequireResources, IUpdateable, ICanSwitchSides {

    private Image mBackground;

    private Tile mCurrentTile;

    private Image mTileName;
    private Image mTileHealing;
    private Image mTileAvo;

    public TileStatusWindow() {
        super(0, 0, 48 * Display.INTERNAL_RES_FACTOR, 53 * Display.INTERNAL_RES_FACTOR, false);
        load();

        this.setY(Display.HEIGHT - this.getHeight() - 6 * Display.INTERNAL_RES_FACTOR);
    }

    @Override
    public void render(DepthBufferedGraphics g) {
        g.drawImage(mBackground, getX(), getY(), this.getWidth(), this.getHeight());

        if (mTileName != null) {
            g.drawImage(mTileName, (int) (getX() + getWidth() / 6f), getY() + 17 * Display.INTERNAL_RES_FACTOR,
                    (int) (getWidth() / 1.5f), (int) (mTileName.getHeight(null) / 1.5f));
        }
        if (mTileHealing != null) {
            g.drawImage(mTileHealing, (int) (getX() + getWidth() / 6f), getY() + 31 * Display.INTERNAL_RES_FACTOR,
                    (int) (getWidth() / 1.5f), (int) (getHeight() / 7f));
        }

        if (mTileAvo != null) {
            g.drawImage(mTileAvo, (int) (getX() + getWidth() / 6f), getY() + 38 * Display.INTERNAL_RES_FACTOR,
                    (int) (getWidth() / 1.5f), (int) (getHeight() / 7f));
        }
    }

    public void setTile(Tile tile) {
        mCurrentTile = tile;
    }

    @Override
    public void update(TimeSnapshot ts) {
        if (mCurrentTile == null) {
            return;
        }
        fetchTileStats();

    }

    @Override
    public void switchTo(ESide side) {
        switch (side) {
        case LEFT:
            setX(0);
            break;

        case RIGHT:
            setX(Display.WIDTH - this.getWidth() - 3 * Display.INTERNAL_RES_FACTOR);
            break;

        default:
            break;

        }

    }

    @Override
    public void load() {
        mBackground = Assets.getFor(EResource.TILE_STATUS);

    }

    public void fetchTileStats() {
        String type = mCurrentTile.getType().toString();
        String healing = "Heal: " + mCurrentTile.getHealing();
        String avoidance = "Avo.: " + mCurrentTile.getAvoidance();

        if (type.equals(ETileType.PLACEHOLDER.toString())) {
            type = "--";
            mTileName = Assets.getFor(EResource.REGULAR_FONT, type);
            mTileHealing = null;
            mTileAvo = null;

        } else {
            mTileName = Assets.getFor(EResource.REGULAR_FONT, type);
            mTileHealing = Assets.getFor(EResource.REGULAR_FONT, healing);
            mTileAvo = Assets.getFor(EResource.REGULAR_FONT, avoidance);
        }

    }

}
