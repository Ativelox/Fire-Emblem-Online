package de.ativelox.feo.client.model.util.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Node<T> {

    private final T mData;

    private final List<Edge<T>> mOutgoingEdges;

    private final List<Edge<T>> mIngoingEdges;

    public Node(T data) {
        mData = data;

        mOutgoingEdges = new ArrayList<>();
        mIngoingEdges = new ArrayList<>();

    }

    public void addOutgoingEdge(Edge<T> edge) {
        mOutgoingEdges.add(edge);
    }

    public void addIngoingEdge(Edge<T> edge) {
        mIngoingEdges.add(edge);
    }

    public List<Edge<T>> getOutgoingEdges() {
        return mOutgoingEdges;
    }

    public List<Edge<T>> getIngoingEdges() {
        return mIngoingEdges;

    }

    public List<Node<T>> getNeighbors() {
        List<Node<T>> result = new ArrayList<>();
        mOutgoingEdges.forEach(e -> result.add(e.getDestination()));

        return result;

    }

    public T getData() {
        return mData;
    }

}
