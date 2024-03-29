package model.objects.general;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.items.weapons.Weapon;
import model.map.rooms.Room;

public class DummyHive extends HiveObject {

	public DummyHive(boolean found, Room pos) {
		super("Hive", pos);
		this.setHealth(1.5);
		this.setFound(found);
		this.setMaxHealth(1.5);
	}
	
	@Override
	public String getName() {
		if (isBroken()) {
			return "Dummy Hive (broken)"; 
		} else {
			return super.getName();
		}
	}

	@Override
	protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) { }

	@Override
	public boolean beAttackedBy(Actor performingClient, Weapon item, GameData gameData) {
		boolean success = super.beAttackedBy(performingClient, item, gameData);
		if (success && this.isBroken()) {
			performingClient.addTolastTurnInfo("What? This hive was made of paper!");
		}
		return success;
	}
	
}
