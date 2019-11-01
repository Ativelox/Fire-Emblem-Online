package de.ativelox.feo.client.controller.input;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import de.ativelox.feo.logging.ELogType;
import de.ativelox.feo.logging.Logger;
import de.ativelox.feo.util.Pair;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class InputMapper {

    private InputMapper() {

    }

    public static Set<EAction> getAction(final Set<Integer> keys) {
        final Set<EAction> result = new HashSet<>(keys.size());

        for (final int key : keys) {
            switch (key) {
            case KeyEvent.VK_ENTER:
                result.add(EAction.CONFIRMATION);
                break;

            case KeyEvent.VK_BACK_SPACE:
                result.add(EAction.CANCEL);
                break;

            // cascade events associated with movement/panning.
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
            case KeyEvent.VK_S:
                break;

            default:
                Logger.get().log(ELogType.ERROR, "Couldn't parse the KeyCode associated with: " + key);
            }
        }
        return result;
    }

    public static Pair<Double, Double> getMovement(final Set<Integer> keys) {
        double x = 0;
        double y = 0;

        if (keys.contains(KeyEvent.VK_UP)) {
            y -= 1;
        }
        if (keys.contains(KeyEvent.VK_DOWN)) {
            y += 1;
        }
        if (keys.contains(KeyEvent.VK_LEFT)) {
            x -= 1;
        }
        if (keys.contains(KeyEvent.VK_RIGHT)) {
            x += 1;

        }
        return Pair.of(x, y);
    }

    public static Pair<Double, Double> getPanning(final Set<Integer> keys) {
        double x = 0;
        double y = 0;

        if (keys.contains(KeyEvent.VK_W)) {
            y -= 1;
        }
        if (keys.contains(KeyEvent.VK_S)) {
            y += 1;
        }
        if (keys.contains(KeyEvent.VK_A)) {
            x -= 1;
        }
        if (keys.contains(KeyEvent.VK_D)) {
            x += 1;

        }
        return Pair.of(x, y);
    }
}
