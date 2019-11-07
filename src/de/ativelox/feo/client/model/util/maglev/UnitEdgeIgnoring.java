package de.ativelox.feo.client.model.util.maglev;

import java.util.Optional;
import java.util.function.Predicate;

import de.ativelox.feo.client.model.gfx.tile.Tile;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.unit.IUnit;
import de.zabuza.maglev.external.graph.Edge;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class UnitEdgeIgnoring implements Predicate<Edge<Tile>> {

    private final IUnit mActor;

    private final Map mMap;

    public UnitEdgeIgnoring(IUnit unit, Map map) {
        mActor = unit;
        mMap = map;
    }

    @Override
    public boolean test(Edge<Tile> t) {
        Optional<IUnit> sourceOccupater = mMap.isOccupied(t.getSource().getX(), t.getSource().getY());
        Optional<IUnit> destinationOccupater = mMap.isOccupied(t.getDestination().getX(), t.getDestination().getY());

        boolean ignore = false;

        if (sourceOccupater.isPresent()) {
            IUnit unitSource = sourceOccupater.get();

            if (unitSource == mActor) {
                ignore = false;

            } else {
                ignore = true;
            }

        }
        if (destinationOccupater.isPresent()) {
            IUnit unitDest = destinationOccupater.get();

            if (unitDest.getAffiliation() != mActor.getAffiliation()) {
                ignore = true;
            }
        }

        return ignore;
    }

}
