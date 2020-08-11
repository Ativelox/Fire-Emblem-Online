package de.ativelox.feo.client.model.util.maglev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.util.Pair;
import de.zabuza.maglev.external.algorithms.EdgeCost;
import de.zabuza.maglev.external.algorithms.Path;
import de.zabuza.maglev.external.graph.Edge;
import de.zabuza.maglev.external.graph.simple.SimpleEdge;
import de.zabuza.maglev.internal.collections.ReverseIterator;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class SimplePath implements Path<Tile, Edge<Tile>> {

    private final List<EdgeCost<Tile, Edge<Tile>>> mCol;

    private SimplePath(List<EdgeCost<Tile, Edge<Tile>>> col) {
        mCol = col;

    }

    public static Path<Tile, Edge<Tile>> of(List<Pair<Integer, Integer>> coords, Map map) {
        List<Edge<Tile>> result = new ArrayList<>();

        Iterator<Pair<Integer, Integer>> iterator = coords.iterator();

        Pair<Integer, Integer> first = iterator.next();

        while (iterator.hasNext()) {

            Pair<Integer, Integer> next = iterator.next();

            result.add(new SimpleEdge<Tile>(map.getByPos(first.getFirst(), first.getSecond()),
                    map.getByPos(next.getFirst(), next.getSecond()), 1));

            first = next;

        }

        result.forEach(s -> System.out.println(s.getSource().getX() + " " + s.getSource().getY() + " -> "
                + s.getDestination().getX() + " " + s.getDestination().getY()));

        return SimplePath.of(result);

    }

    public static Path<Tile, Edge<Tile>> of(List<Edge<Tile>> edges) {
        List<EdgeCost<Tile, Edge<Tile>>> result = new ArrayList<>();

        for (Edge<Tile> edge : edges) {
            result.add(new EdgeCost<Tile, Edge<Tile>>(edge, 0));
        }

        return new SimplePath(result);

    }

    @Override
    public Iterator<EdgeCost<Tile, Edge<Tile>>> reverseIterator() {
        return new ReverseIterator<>(mCol);
    }

    @Override
    public Iterator<EdgeCost<Tile, Edge<Tile>>> iterator() {
        return mCol.iterator();
    }

    @Override
    public Tile getDestination() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Tile getSource() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getTotalCost() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int length() {
        // TODO Auto-generated method stub
        return mCol.size();
    }

}
