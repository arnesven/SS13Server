package model.items;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.events.ElectricalFire;
import model.events.Event;
import model.map.Room;

public class FireExtinguisher extends BluntWeapon {

	private int level = 2;
	
	public FireExtinguisher() {
		super("Fire ext.");
	}
	
	@Override
	public String getName() {
		if (level == 2) {
			return super.getName() + "(full)";
		} else if (level == 1) {
			return super.getName() + "(half)";
		} 
		
		return super.getName() + "(empty)";
	}

	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
		if (hasFire(cl.getPosition()) != null && level > 0) {
			at.add(new Action("Put out fire", 
					new SensoryLevel(VisualLevel.CLEARLY_VISIBLE,
							AudioLevel.SAME_ROOM,
							OlfactoryLevel.WHIFF)) {

				@Override
				protected String getVerb() {
					return "put out the fire";
				}
				
				@Override
				public void setArguments(List<String> args) { }

				@Override
				protected void execute(GameData gameData, Actor performingClient) {
					ElectricalFire fire = hasFire(performingClient.getPosition());
					fire.fix();
					level--;
					performingClient.addTolastTurnInfo("You put out the fire.");
				}
			});
		}

	}

	private ElectricalFire hasFire(Room position) {
		for (Event e : position.getEvents()) {
			if (e instanceof ElectricalFire) {
				return (ElectricalFire)e;
			}
		}
		return null;
	}

}
