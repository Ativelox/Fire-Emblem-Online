package de.ativelox.feo.server.controller;

/**
 * Provides a container for {@link IGameControllerReceiver} and
 * {@link IGameControllerSender} batching both of them together.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public interface IGameController<POut, PIn> extends IGameControllerReceiver, IGameControllerSender<POut, PIn> {

}
