package de.ativelox.feo.client.model.unit;

import java.awt.Image;
import java.util.Optional;

import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.EClass;
import de.ativelox.feo.client.model.property.EGender;
import de.ativelox.feo.client.model.property.EUnit;
import de.ativelox.feo.client.model.property.ICanMove;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISelectable;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.IUpdateable;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IUnit extends IRenderable, IUpdateable, ISpatial, ISelectable, ICanMove {

    public int getMovement();

    public int getRange();

    public void moveInstantly(int x, int y);

    public void finished();

    public void ready();

    public boolean isWaiting();

    public Image getPortrait();

    public String getName();

    public int getCurrentHP();

    public int getMaximumHP();

    public EAffiliation getAffiliation();

    public Inventory getInventory();

    public int getStr();

    public int getSkill();

    public int getSpd();

    public int getLuck();

    public int getDef();

    public int getRes();

    public EGender getGender();

    public EClass getCurrentClass();

    public EUnit getUnit();

    public Optional<IWeapon> getEquippedWeapon();

    public void equip(IWeapon weapon);

}
