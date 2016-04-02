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
import model.items.weapons.BluntWeapon;
import model.map.Room;

public class FireExtinguisher extends BluntWeapon {

	private int level = 2;
	
	public FireExtinguisher() {
		super("Fire ext.", 1.0);
	}
	
	@Override
	public String getFullName(Actor whosAsking) {
		if (level == 2) {
			return super.getBaseName() + "(full)";
		} else if (level == 1) {
			return super.getBaseName() + "(half)";
		} 
		
		return super.getBaseName() + "(empty)";
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
		if (hasFire(cl.getPosition()) != null && level > 0) {
			at.add(new Action("Put out fire", 
					new SensoryLevel(VisualLevel.CLEARLY_VISIBLE,
							AudioLevel.SAME_ROOM,
							OlfactoryLevel.WHIFF)) {

				@Override
				protected String getVerb(Actor whosAsking) {
					return "put out the fire";
				}
				
				@Override
				public void setArguments(List<String> args, Actor p) { }

				@Override
				protected void execute(GameData gameData, Actor performingClient) {
					if (GameItem.hasAnItem(performingClient, new FireExtinguisher())) {
						ElectricalFire fire = hasFire(performingClient.getPosition());
						fire.fix();
						level--;
						performingClient.addTolastTurnInfo("You put out the fire.");
					} else {
						performingClient.addTolastTurnInfo("What the fire extinguisher is gone! Your action failed.");
					}
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
	
	@Override
	protected char getIcon() {
		return 'x';
	}

}
