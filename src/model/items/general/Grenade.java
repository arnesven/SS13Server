package model.items.general;

import java.util.ArrayList;

import util.MyRandom;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.itemactions.ThrowGrenadeAction;
import model.events.Damager;

public class Grenade extends GameItem implements Damager {

	public Grenade() {
		super("Grenade", 0.5);
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
		at.add(new ThrowGrenadeAction(cl));
	}

	@Override
	public String getText() {
		return "A grenade exploaded!";
	}


	
	@Override
	public boolean isDamageSuccessful(boolean reduced) {
		return MyRandom.nextDouble() < 0.75;
	}

	@Override
	public String getName() {
		return getBaseName();
	}

	@Override
	public double getDamage() {
		return 1.0;
	}

	@Override
	public Grenade clone() {
		return new Grenade();
	}
	
	@Override
	protected char getIcon() {
		return '}';
	}

}
