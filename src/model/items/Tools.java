package model.items;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.actions.Target;
import model.events.Event;
import model.events.HullBreach;
import model.map.Room;
import model.objects.BreakableObject;
import model.objects.GameObject;
import model.objects.Repairable;

public class Tools extends BluntWeapon {

	public Tools() {
		super("Tools");
	}
	
	@Override
	public void addYourActions(ArrayList<Action> at, Player cl) {
		if (hasBrokenObjects(cl.getPosition())) {
			at.add(new RepairAction("Repair", SensoryLevel.PHYSICAL_ACTIVITY, cl));
		}
		if (cl.getPosition().hasHullBreach()) {
			at.add(new Action("Seal hull breach", 
						SensoryLevel.PHYSICAL_ACTIVITY) {
				
				@Override
				public void setArguments(List<String> args) { }
				
				@Override
				protected String getVerb() {
					return "sealed the breach";
				}
				
				@Override
				protected void execute(GameData gameData, Actor performingClient) {
					List<Event> evs = performingClient.getPosition().getEvents();
					for (Event e : evs) {
						if (e instanceof HullBreach) {
							((HullBreach)e).fix();
							break;
						}
					}
					performingClient.addTolastTurnInfo("You sealed the breach.");
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
