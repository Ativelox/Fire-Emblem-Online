/**
 * 
 */
package de.ativelox.feo.tools.mapcloner;

import de.ativelox.feo.client.model.gfx.tile.ETileSet;

/**
 * @author Julian Tischner <juliantischner97@gmail.com>
 *
 */
public class Launcher {
    
    public static void main(final String[] args) {
	TileMatcher s = new TileMatcher(ETileSet.FIELDS);
	
	s.matchAgainst("ch5.png");
	
    }

}
