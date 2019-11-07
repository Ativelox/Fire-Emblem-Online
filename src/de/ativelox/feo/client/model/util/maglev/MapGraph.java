package de.ativelox.feo.client.model.util.maglev;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.map.Map;
import de.zabuza.maglev.external.graph.Edge;
import de.zabuza.maglev.external.graph.Graph;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class MapGraph implements Graph<Tile, Edge<Tile>> {

    private final Set<Tile> mVertexSet;
    private final Set<Edge<Tile>> mEdgeSet;
    private final java.util.Map<Tile, Set<Edge<Tile>>> mOutgoingEdges;
    private final java.util.Map<Tile, Set<Edge<Tile>>> mIngoingEdges;

    public MapGraph(Map map) {
        mVertexSet = new HashSet<>();
        mEdgeSet = new HashSet<>();
        mOutgoingEdges = new HashMap<>();
        mIngoingEdges = new HashMap<>();

        generateFrom(map);
    }

    private void generateFrom(Map map) {
        Tile[][] internalMap = map.getInternalMap();

        // setup the first row, since it is the first row it has to be handled
        // differently than the rest.
        Tile current = internalMap[0][0];
        addNode(current);

        for (int i = 0; i < internalMap[0].length - 1; i++) {
            Tile right = internalMap[0][i + 1];

            addEdge(new TileEdge(current, right));
            addEdge(new TileEdge(right, current));
            addNode(right);

            current = right;
        }
        for (int i = 1; i < internalMap.length; i++) {
            current = internalMap[i][0];
            addNode(current);

            for (int j = 0; j < internalMap[i].length - 1; j++) {
                Tile right = internalMap[i][j + 1];

                addEdge(new TileEdge(current, right));
                addEdge(new TileEdge(right, current));
                addNode(right);

                Tile leftTop = internalMap[i - 1][j];

                addEdge(new TileEdge(current, leftTop));
                addEdge(new TileEdge(leftTop, current));

                current = right;

            }
            // link the last in the row with its "column parent"
            Tile top = internalMap[i - 1][internalMap[i].length - 1];

            addEdge(new TileEdge(current, top));
            addEdge(new TileEdge(top, current));
        }
    }

    @Override
    public boolean addEdge(Edge<Tile> edge) {
        if (!mOutgoingEdges.containsKey(edge.getSource())) {
            mOutgoingEdges.put(edge.getSource(), new HashSet<>());
        }
        if (!mIngoingEdges.containsKey(edge.getDestination())) {
            mIngoingEdges.put(edge.getDestination(), new HashSet<>());
        }
        mOutgoingEdges.get(edge.getSource()).add(edge);
        mIngoingEdges.get(edge.getDestination()).add(edge);

        return mEdgeSet.add(edge);
    }

    @Override
    public boolean addNode(Tile node) {
        return mVertexSet.add(node);
    }

    @Override
    public boolean containsEdge(Edge<Tile> edge) {
        return mEdgeSet.contains(edge);
    }

    @Override
    public int getAmountOfEdges() {
        return mEdgeSet.size();
    }

    @Override
    public Stream<Edge<Tile>> getEdges() {
        return mEdgeSet.stream();
    }

    @Override
    public Stream<Edge<Tile>> getIncomingEdges(Tile destination) {
        return mIngoingEdges.get(destination).stream();
    }

    @Override
    public Collection<Tile> getNodes() {
        return mVertexSet;
    }

    @Override
    public Stream<Edge<Tile>> getOutgoingEdges(Tile source) {
        return mOutgoingEdges.get(source).stream();
    }

    @Override
    public boolean removeEdge(Edge<Tile> edge) {
        if (mIngoingEdges.containsKey(edge.getDestination())) {
            mIngoingEdges.get(edge.getDestination()).remove(edge);
        }
        if (mOutgoingEdges.containsKey(edge.getSource())) {
            mOutgoingEdges.remove(edge.getSource()).remove(edge);
        }
        return mEdgeSet.remove(edge);
    }

    @Override
    public boolean removeNode(Tile node) {
        mIngoingEdges.remove(node);
        mOutgoingEdges.remove(node);

        return mVertexSet.remove(node);
    }

    @Override
    public void reverse() {
        mEdgeSet.forEach(e -> ((TileEdge) e).reverse());

    }

    @Override
    public int size() {
        return mVertexSet.size();

    }
}
