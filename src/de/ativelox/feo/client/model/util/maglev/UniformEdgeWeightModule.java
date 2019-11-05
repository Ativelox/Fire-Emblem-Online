package de.ativelox.feo.client.model.util.maglev;

import java.util.OptionalDouble;

import de.zabuza.maglev.external.algorithms.DijkstraModule;
import de.zabuza.maglev.external.graph.Edge;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class UniformEdgeWeightModule<N> implements DijkstraModule<N, Edge<N>> {

    private final double mCost;

    public UniformEdgeWeightModule(double cost) {
        mCost = cost;
    }

    @Override
    public OptionalDouble provideEdgeCost(final Edge<N> edge, final double tentativeDistance) {
        return OptionalDouble.of(mCost);
    }

}
