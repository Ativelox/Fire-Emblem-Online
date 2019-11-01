package de.ativelox.feo.client.model.util.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.ativelox.feo.client.model.gfx.tile.ITile;
import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class GraphUtils {

    private GraphUtils() {

    }

    public static <T extends ITile> Map<T, Deque<T>> dijsktra(T[][] map, T source) {
        return GraphUtils.dijkstra(GraphUtils.convert(map), source);
    }

    public static <T> Map<T, Deque<T>> dijkstra(List<Node<T>> vertexList, T source) {
        Node<T> nodeSource = null;

        for (final Node<T> node : vertexList) {
            if (node.getData() == source) {
                nodeSource = node;
                break;
            }
        }

        if (nodeSource == null) {
            Logger.get().log(ELogType.ERROR, "Source not present in the given representation.");
            return new HashMap<>();
        }

        // start actual dijkstra algorithm
        Map<Node<T>, Integer> dist = new HashMap<>();
        Map<Node<T>, Node<T>> prev = new HashMap<>();
        Set<Node<T>> seen = new HashSet<>();

        // TODO: handle this with a PQ, this wasn't done since the comparing is based
        // off of
        // the dist map, and doesn't trigger a change in the key when that value is
        // changed.
        List<Node<T>> queue = new ArrayList<>();

        for (final Node<T> node : vertexList) {
            dist.put(node, Integer.MAX_VALUE);

            queue.add(node);
        }
        dist.put(nodeSource, 0);

        while (!queue.isEmpty()) {

            // TODO: see above
            queue.sort(Comparator.comparing(t -> dist.get(t)));
            Node<T> current = queue.remove(0);

            seen.add(current);

            for (final Edge<T> outgoing : current.getOutgoingEdges()) {
                final Node<T> neighbor = outgoing.getDestination();

                if (seen.contains(neighbor)) {
                    continue;

                }
                int alt = dist.get(current) + outgoing.getWeight();

                if (dist.get(neighbor) == null) {
                }

                if (alt < dist.get(neighbor)) {
                    dist.put(neighbor, alt);
                    prev.put(neighbor, current);

                }
            }
        }
        Map<T, Deque<T>> paths = new HashMap<>();

        for (final Node<T> node : vertexList) {
            if (node == nodeSource) {
                continue;
            }
            paths.put(node.getData(), reconstruct(prev, node, nodeSource));
        }
        return paths;
    }

    private static <T> Deque<T> reconstruct(Map<Node<T>, Node<T>> prev, Node<T> from, Node<T> source) {
        LinkedList<T> tempRes = new LinkedList<>();
        tempRes.add(from.getData());

        Node<T> current = prev.get(from);

        while (current != source) {
            tempRes.add(current.getData());
            current = prev.get(current);
        }
        Collections.reverse(tempRes);

        return tempRes;

    }

    private static <T extends ITile> List<Node<T>> convert(T[][] map) {

        List<Node<T>> result = new ArrayList<>();
        List<List<Node<T>>> temp = new ArrayList<>();

        // setup 2d list
        for (int i = 0; i < map.length; i++) {
            temp.add(new ArrayList<>());
        }

        // setup first row
        Node<T> current = new Node<T>(map[0][0]);
        temp.get(0).add(current);
        for (int i = 0; i < map[0].length - 1; i++) {
            Node<T> right = new Node<T>(map[0][i + 1]);
            Edge<T> leftToRight = new Edge<>(current, right, right.getData().getCost());
            Edge<T> rightToLeft = new Edge<>(right, current, current.getData().getCost());

            current.addOutgoingEdge(leftToRight);
            right.addOutgoingEdge(rightToLeft);
            current.addIngoingEdge(rightToLeft);
            right.addIngoingEdge(leftToRight);

            current = right;

            temp.get(0).add(right);

        }

        // iteratively link the columns and rows.
        for (int i = 1; i < map.length; i++) {
            Node<T> currentInRow = new Node<T>(map[i][0]);
            temp.get(i).add(currentInRow);

            for (int j = 0; j < map[i].length - 1; j++) {
                Node<T> right = new Node<T>(map[i][j + 1]);

                // setup row link
                Edge<T> leftToRight = new Edge<>(currentInRow, right, right.getData().getCost());
                Edge<T> rightToLeft = new Edge<>(right, currentInRow, currentInRow.getData().getCost());

                currentInRow.addOutgoingEdge(leftToRight);
                right.addOutgoingEdge(rightToLeft);
                currentInRow.addIngoingEdge(rightToLeft);
                right.addIngoingEdge(leftToRight);

                temp.get(i).add(right);

                // setup column link, since the row link above this row already exists.
                Node<T> leftTop = temp.get(i - 1).get(j);

                Edge<T> leftToLeftTop = new Edge<>(currentInRow, leftTop, leftTop.getData().getCost());
                Edge<T> leftTopToLeft = new Edge<>(leftTop, currentInRow, currentInRow.getData().getCost());

                currentInRow.addOutgoingEdge(leftToLeftTop);
                currentInRow.addIngoingEdge(leftTopToLeft);

                leftTop.addOutgoingEdge(leftTopToLeft);
                leftTop.addIngoingEdge(leftToLeftTop);

                currentInRow = right;

            }
            Node<T> top = temp.get(i - 1).get(map[i].length - 1);

            Edge<T> rightToTop = new Edge<>(currentInRow, top, top.getData().getCost());
            Edge<T> topToRight = new Edge<>(top, currentInRow, currentInRow.getData().getCost());

            top.addOutgoingEdge(topToRight);
            top.addIngoingEdge(rightToTop);

            currentInRow.addOutgoingEdge(rightToTop);
            currentInRow.addIngoingEdge(topToRight);

        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                result.add(temp.get(i).get(j));
            }
        }

        return result;
    }
}
