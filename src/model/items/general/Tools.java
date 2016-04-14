package model.items.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.actions.itemactions.RepairAction;
import model.events.Event;
import model.events.HullBreach;
import model.items.weapons.BluntWeapon;
import model.map.Room;
import model.objects.general.GameObject;
import model.objects.general.Repairable;


public class Tools extends BluntWeapon {

	public Tools() {
		super("Toolkit", 1.0);
	}
	
	@Override
	public Tools clone() {
		return new Tools();
	}
	
	
	@Override
	protected char getIcon() {
		return 'i';
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
		if (hasBrokenObjects(cl.getPosition())) {
			at.add(new RepairAction(cl));
		}
		if (cl.getPosition().hasHullBreach()) {
			at.add(new Action("Seal hull breach", 
						SensoryLevel.PHYSICAL_ACTIVITY) {
				
				@Override
				public void setArguments(List<String> args, Actor p) { }
				
				@Override
				protected String getVerb(Actor whosAsking) {
					return "sealed the breach";
				}
				
				@Override
				protected void execute(GameData gameData, Actor performingClient) {
					if (GameItem.hasAnItem(performingClient, new Tools())) {
						List<Event> evs = performingClient.getPosition().getEvents();
						for (Event e : evs) {
							if (e instanceof HullBreach) {
								((HullBreach)e).fix();
								break;
							}
						}
						performingClient.addTolastTurnInfo("You sealed the breach.");
					} else {
						performingClient.addTolastTurnInfo("What? The tools are gone! Your action failed.");
					}
				}
			});
		}
	}


	private boolean hasBrokenObjects(Room position) {
		for (GameObject ob : position.getObjects()) {
			if (ob instanceof Target) {
				if (ob instanceof Repairable) {
					if (((Repairable)ob).isDamaged() || ((Repairable)ob).isBroken()) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
