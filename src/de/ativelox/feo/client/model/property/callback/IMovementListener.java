package de.ativelox.feo.client.model.property.callback;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IMovementListener {

    /**
     * Gets called when the user moved on the x-Axis. When
     * <tt>amount {@literal<} 0</tt> then the movement was to the <i>left</i>
     * otherwise to the <i>right</i>. <tt>amount</tt> will always be a number
     * between <tt> {@literal -1} </tt> and <tt>1</tt> (both inclusive) representing
     * how <i>strong</i> the input in that direction is. The appropriate scaling of
     * that value is left to the implementation.
     * 
     * @param amount how <i>strong</i> the input is on the x axis.
     */
    void onMoveX(double amount);

    /**
     * Gets called when the user moved on the y-Axis. When
     * <tt>amount {@literal<} 0</tt> then the movement was <i>up</i> otherwise
     * <i>down</i>. <tt>amount</tt> will always be a number between
     * <tt> {@literal -1} </tt> and <tt>1</tt> (both inclusive) representing how
     * <i>strong</i> the input in that direction is. The appropriate scaling of that
     * value is left to the implementation.
     * 
     * @param amount how <i>strong</i> the input is on the y axis.
     */
    void onMoveY(double amount);

}
