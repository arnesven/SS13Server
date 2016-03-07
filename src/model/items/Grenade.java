package model.items;

import java.util.ArrayList;

import model.Player;
import model.actions.Action;
import model.actions.ThrowGrenadeAction;
import model.events.Damager;

public class Grenade extends Weapon implements Damager {

	public Grenade() {
		super("Grenade", 0.75, 1.0, true);
	}
	
	@Override
	public void addYourActions(ArrayList<Action> at, Player cl) {
		at.add(new ThrowGrenadeAction(cl));
	}

	@Override
	public String getText() {
		return "A grenade exploaded!";
	}

	@Override
	public boolean isDamageSuccessful(boolean reduced) {
		return isAttackSuccessful(reduced);
	}

}
