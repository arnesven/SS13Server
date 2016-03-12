package model.actions;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.events.Damager;
import model.items.Explosive;
import model.items.GameItem;
import model.items.Grenade;
import model.items.MedKit;
import model.items.Weapon;

/**
 * @author erini02
 * Interface for targets. A target is an object which can be targeted
 * by a targeting action, i.e. the attack action. Anything targetable
 * should implement this interface, e.g. characters, gameobjects, items etc.
 */
public interface Target {

	/**
	 * Gets the name of this target is it appears when being targeted
	 * @return the target's name
	 */
	String getName();

	boolean isTargetable();

	void beAttackedBy(Actor performingClient, Weapon item);

	boolean isDead();

	double getHealth();

	double getMaxHealth();

	boolean hasSpecificReaction(MedKit objectRef);

	void addToHealth(double d);

	List<GameItem> getItems();

	void beExposedTo(Actor performingClient, Damager damager);

	boolean hasInventory();

	boolean isHealable();

}
