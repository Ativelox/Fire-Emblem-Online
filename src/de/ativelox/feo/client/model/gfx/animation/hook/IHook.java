package de.ativelox.feo.client.model.gfx.animation.hook;

import java.util.function.Function;

import de.ativelox.feo.client.model.util.TimeSnapshot;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IHook extends Function<TimeSnapshot, Boolean> {

}
