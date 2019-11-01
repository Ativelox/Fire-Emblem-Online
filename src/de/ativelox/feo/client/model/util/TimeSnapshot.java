package de.ativelox.feo.client.model.util;

/**
 * A helper class that provides helpful methods to get the time that has passed
 * between frames
 * 
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class TimeSnapshot {

    /**
     * The time after the last frame has finished.
     */
    private long mLastTime;

    /**
     * Initializes a new {@link TimeSnapshot}.
     * 
     */
    public TimeSnapshot() {
        mLastTime = System.currentTimeMillis();

    }

    /**
     * Gets the time (ms) that has passed since that last frame was completely
     * rendered.
     * 
     * @return The time in ms.
     */
    public long getPassed() {
        return System.currentTimeMillis() - mLastTime;

    }

    /**
     * Signifies that the current frame has been completely rendered.
     */
    public void cycleFinished() {
        this.mLastTime = System.currentTimeMillis();

    }

}
