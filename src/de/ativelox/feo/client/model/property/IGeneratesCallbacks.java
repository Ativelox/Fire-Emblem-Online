package de.ativelox.feo.client.model.property;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IGeneratesCallbacks<T> {

    void add(T listener);

    void remove(T listener);

}
