package de.ativelox.feo.client.model.gfx.tile;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface ITile {

    int getCost();

    int getAvoidance();

    int getHealing();
    
    int getId();
    
    ITile copy();

}
