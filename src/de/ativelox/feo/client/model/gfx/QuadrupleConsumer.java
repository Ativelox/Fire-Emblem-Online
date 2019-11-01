package de.ativelox.feo.client.model.gfx;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
@FunctionalInterface
public interface QuadrupleConsumer<U, V, R, S> {

    void apply(U u, V v, R r, S s);

}
