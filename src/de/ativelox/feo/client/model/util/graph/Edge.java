package de.ativelox.feo.client.model.util.graph;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Edge<T> {

    private final Node<T> mSource;

    private final Node<T> mDestination;

    private final int mWeight;

    public Edge(Node<T> src, Node<T> dest, int weight) {
        mSource = src;
        mDestination = dest;
        mWeight = weight;

    }

    public Node<T> getSource() {
        return mSource;

    }

    public Node<T> getDestination() {
        return mDestination;
    }

    public int getWeight() {
        return mWeight;
    }
}
