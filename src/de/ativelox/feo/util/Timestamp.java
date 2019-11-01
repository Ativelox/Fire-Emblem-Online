package de.ativelox.feo.util;

import java.time.LocalTime;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Timestamp {

    private Timestamp() {

    }

    public static String now() {
        final LocalTime time = LocalTime.now();
        return time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();
    }

}
