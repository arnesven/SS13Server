package model.events.damage;

import model.GameData;
import model.Target;
import model.objects.general.BreakableObject;
import sounds.Sound;

import java.io.Serializable;

public interface Damager extends Serializable {
	
	
	/**
	 * @return the text to the client when it hits it.
	 */
	String getText();

	boolean isDamageSuccessful(boolean reduced);

//	double getDamage();

	/**
	 * Name of the damager type, e.g. fire, asphyxiation.
	 * @return
	 */
	String getName();

    void doDamageOnMe(Target target);

    default boolean hasRealSound() {return false;}

    default Sound getRealSound() {return null;}
}
