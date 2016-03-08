package model.actions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.map.Room;
import model.modes.GameMode;
import model.objects.GameObject;

public class AIConsoleAction extends Action {

	private String choice;
	private String crew;

	public AIConsoleAction() {
		super("AI Console", SensoryLevel.OPERATE_DEVICE);
	}
	
	@Override
	protected String getVerb() {
		return "used the AI Console";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (choice.equals("Check Alarms")) {
			boolean noAlarms = true;
			for (Room r : gameData.getRooms()) {
				if (r.hasFire()) {
					performingClient.addTolastTurnInfo("-->Fire alarm in " + r.getName() + ".");
					noAlarms = false;
				}
				if (r.hasHullBreach()) {
					performingClient.addTolastTurnInfo("-->Low pressure in " + r.getName() + ".");
					noAlarms = false;
				}
			}
			if (noAlarms) {
				performingClient.addTolastTurnInfo("No alarms.");
			}
			
		} else {
			for (Actor a : gameData.getActors()) {
				if (a.getBaseName().equals(crew)) {
					performingClient.addTolastTurnInfo("-->" + crew + " is in " + a.getPosition().getName() + ".");
					return;
				}
			}
			performingClient.addTolastTurnInfo(crew + " not found.");
		}
	}
		

	@Override
	public String toString() {
		StringBuffer crews = new StringBuffer();
		for (String s : GameMode.getAvailCharsAsStrings()) {
			crews.append(s + "{}");
		}
		
		return getName() + "{Check Alarms{}Locate Crew Member{" + crews.toString() + "}}";
	}
	
	@Override
	public void setArguments(List<String> args) {
		choice = args.get(0);
		if (choice.equals("Locate Crew Member")) {
			crew = args.get(1);
		}
	}

}
