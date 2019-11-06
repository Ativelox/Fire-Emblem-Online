package de.ativelox.feo.client.view.element.game;

import java.awt.Color;
import java.util.Set;

import de.ativelox.feo.client.model.gfx.DepthBufferedGraphics;
import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.sound.ESoundEffect;
import de.ativelox.feo.client.model.sound.SoundPlayer;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MovementRange implements IRenderable {

    public static final Color MOVEMENT_COLOR = new Color(0, 0, 255, 100);
    public static final Color ATTACK_COLOR = new Color(255, 0, 0, 100);

    public final Set<Tile> mMovement;

    private final Set<Tile> mRange;

    public MovementRange(Set<Tile> movement, Set<Tile> range) {
        mMovement = movement;
        mRange = range;

    }

    @Override
    public void render(DepthBufferedGraphics g) {
        mMovement.forEach(t -> g.fillRect(MOVEMENT_COLOR, t.getX(), t.getY(), t.getWidth(), t.getHeight()));
        mRange.forEach(t -> g.fillRect(ATTACK_COLOR, t.getX(), t.getY(), t.getWidth(), t.getHeight()));

    }
}
