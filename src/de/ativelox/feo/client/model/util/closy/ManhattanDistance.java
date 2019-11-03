package de.ativelox.feo.client.model.util.closy;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.util.Pair;
import de.zabuza.closy.external.Metric;

/**
 * Provides a metric on every {@link ISpatial} that is normed by the
 * <tt>width</tt> and <tt>height</tt> of tiles in the game. The metric defined
 * is known as the <tt>Manhattan Metric</tt> which is the equivalent of an
 * <tt>Euclidean Metric</tt> given only vertical and horizontal grid movement.
 * 
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ManhattanDistance<E extends ISpatial> implements Metric<E> {

    @Override
    public double distance(E arg0, E arg1) {
        Pair<Integer, Integer> normedFirst = Pair.of(arg0.getX() / Tile.WIDTH, arg0.getY() / Tile.HEIGHT);
        Pair<Integer, Integer> normedSecond = Pair.of(arg1.getX() / Tile.WIDTH, arg1.getY() / Tile.HEIGHT);

        return Math.abs(normedFirst.getFirst() - normedSecond.getFirst())
                + Math.abs(normedFirst.getSecond() - normedSecond.getSecond());
    }
}
