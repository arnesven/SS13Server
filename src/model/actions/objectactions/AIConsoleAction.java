package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.map.Room;
import model.modes.GameMode;
import model.objects.consoles.AIConsole;
import model.objects.consoles.GeneratorConsole;

public class AIConsoleAction extends Action {

	private String choice;
	private String crew;
	private AIConsole console;

	public AIConsoleAction(AIConsole console) {
		super("AI Console", SensoryLevel.OPERATE_DEVICE);
		this.console = console;
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "used the AI Console";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (console.isInUse()) {
			performingClient.addTolastTurnInfo("You failed to activate the AI console.");
			return;
		}
		
		console.setInUse(true);
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
			for (Object ob : gameData.getObjects()) {
				if (ob instanceof GeneratorConsole) {
					GeneratorConsole gc = (GeneratorConsole) ob;
					if (Math.abs(gc.getPowerOutput() - 1.0) > 0.2) {
						performingClient.addTolastTurnInfo("-->Power output anomalous " + (int)(100.0*gc.getPowerOutput()) + "%");
					}
					break;
				}
			}
			if (noAlarms) {
				performingClient.addTolastTurnInfo("No alarms.");
			}
			
		} else {
			boolean found = false;
			for (Actor a : gameData.getActors()) {
				if (a.getBaseName().equals(crew)) {
					performingClient.addTolastTurnInfo("-->" + crew + " is in " + a.getPosition().getName() + ".");
					found = true;
				}
			}
			if (! found) {
				performingClient.addTolastTurnInfo(crew + " not found.");
			}
		}
	
	
		gameData.executeAtEndOfRound(performingClient, this);
	}

	@Override
	public void lateExecution(GameData gameData, Actor performingClient) {
		System.out.println("Executing late ai console...");
		console.setInUse(false);
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption top = new ActionOption(this.getName());
		top.addOption("Check Alarms");
		ActionOption locate = new ActionOption("Locate Crew Member");
		for (String s : GameMode.getAllCharsAsStrings()) {
			locate.addOption(s);
		}
		top.addOption(locate);
		
		return top;
	}
	
	@Override
	public void setArguments(List<String> args, Actor p) {
		choice = args.get(0);
		if (choice.equals("Locate Crew Member")) {
			crew = args.get(1);
		}
	}

}
