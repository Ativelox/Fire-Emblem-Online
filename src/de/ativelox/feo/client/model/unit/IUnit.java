package de.ativelox.feo.client.model.unit;

import java.awt.Image;
import java.util.Optional;

import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.property.EBattleAnimType;
import de.ativelox.feo.client.model.property.EClass;
import de.ativelox.feo.client.model.property.EGender;
import de.ativelox.feo.client.model.property.EUnit;
import de.ativelox.feo.client.model.property.ICanMove;
import de.ativelox.feo.client.model.property.IRenderable;
import de.ativelox.feo.client.model.property.ISelectable;
import de.ativelox.feo.client.model.property.ISpatial;
import de.ativelox.feo.client.model.property.IUpdateable;
import de.ativelox.feo.client.model.unit.item.Inventory;
import de.ativelox.feo.client.model.unit.item.weapon.IWeapon;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public interface IUnit extends IRenderable, IUpdateable, ISpatial, ISelectable, ICanMove {

    public void removeHp(int hp);

    public int getMov();

    public int getMaxRange();

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

    public String getBattleAnimation(EBattleAnimType type);

    public String getBattlePaletteName();

    public EGender getGender();

    public EClass getCurrentClass();

    public EUnit getUnit();

    public Optional<IWeapon> getEquippedWeapon();

    public void equip(IWeapon weapon);

    public String getAnimationHookName();

    public String getDeathQuote();

    public boolean isCommander();

    public void setToCommander();

    public String getCommanderAttackedQuote();
    
    public void addHp(int hp);
    
    public int getId();

}
