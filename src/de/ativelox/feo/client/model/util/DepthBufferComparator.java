package de.ativelox.feo.client.model.util;

import java.util.Comparator;

import de.ativelox.feo.client.model.property.EGraphicsOperation;
import de.ativelox.feo.util.Pair;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class DepthBufferComparator implements Comparator<Pair<EGraphicsOperation, Object[]>> {

    @Override
    public int compare(Pair<EGraphicsOperation, Object[]> o1, Pair<EGraphicsOperation, Object[]> o2) {
        int first = (int) o1.getSecond()[o1.getSecond().length - 1];
        int second = (int) o2.getSecond()[o2.getSecond().length - 1];

        return Integer.compare(first, second);
    }

}
