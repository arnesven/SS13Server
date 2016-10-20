package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.AlarmOverlayDecorator;
import model.characters.general.HorrorCharacter;
import model.items.NoSuchThingException;
import model.map.Room;
import model.modes.GameMode;
import model.npcs.NPC;
import model.npcs.ParasiteNPC;
import model.npcs.PirateNPC;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.robots.RobotNPC;
import model.objects.consoles.AIConsole;
import model.objects.consoles.GeneratorConsole;
import model.objects.general.GameObject;
import util.Logger;

public class AIConsoleAction extends ConsoleAction {

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
            // we will execute at end of round

            gameData.executeAtEndOfRound(performingClient, this);
        } else if (choice.equals("Shut Down AI")){
            console.shutDown(gameData);

            performingClient.addTolastTurnInfo("You shut down the AI! Your on your own now.");

		} else {
			boolean found = false;
            if (!console.isCorrupt()) {
                for (Room r : gameData.getRooms()) {
                    for (Actor a : r.getActors()) {
                        if (a.getBaseName().equals(crew)) {
                            performingClient.addTolastTurnInfo("-->" + crew + " is in " + a.getPosition().getName() + ".");
                            found = true;
                        }
                    }
                }
            }
			if (! found) {
				performingClient.addTolastTurnInfo(crew + " not found.");
			}
		}
	
	

	}

	@Override
	public void lateExecution(GameData gameData, Actor performingClient) {
        boolean noAlarms = true;
        List<String> alarms = null;
        try {
            alarms = gameData.findObjectOfType(AIConsole.class).getAlarms(gameData);

            for (String alarm : alarms) {
                performingClient.addTolastTurnInfo(alarm);
            }
        } catch (NoSuchThingException nste) {
            Logger.log(Logger.CRITICAL, "NO AI CONSOLE FOUND!");
        }

        if (alarms == null || alarms.size() == 0) {
            performingClient.addTolastTurnInfo("No alarms.");
        } else {
            performingClient.setCharacter(new AlarmOverlayDecorator(performingClient.getCharacter(), gameData));
        }

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
        top.addOption("Shut Down AI");
		
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
