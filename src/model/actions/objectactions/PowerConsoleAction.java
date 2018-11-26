package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.objects.consoles.GeneratorConsole;
import util.Logger;

public class PowerConsoleAction extends ConsoleAction {

	private PowerLevelAction powerLevelAction;
	private PowerPrioAction powerPrioAction;
	private boolean prioSelected = false;
	private GeneratorConsole genRef;
	private boolean levelSelected;
	
	public PowerConsoleAction(GeneratorConsole generatorConsole) {
		super("Power Console", SensoryLevel.OPERATE_DEVICE);
		powerLevelAction = new PowerLevelAction(generatorConsole);
		powerPrioAction = new PowerPrioAction(generatorConsole);
		this.genRef = generatorConsole;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Fiddled with power console";
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		opt.addOption("Power Status");
		opt.addOption(powerLevelAction.getOptions(gameData, whosAsking));
		opt.addOption(powerPrioAction.getOptions(gameData, whosAsking));
		return opt;
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (genRef.isInUse()) {
			performingClient.addTolastTurnInfo("You failed to activate the Power Console.");
			return;
		}


		
		if (prioSelected) {
			Logger.log("Prio changed!");
			powerPrioAction.execute(gameData, performingClient);
		} else if (levelSelected) {
			Logger.log("Level changed!");
			powerLevelAction.execute(gameData, performingClient);
		} else {
		    for (String status : genRef.getSource().getStatusMessages()) {
		        performingClient.addTolastTurnInfo(status);
            }

		}
		
		genRef.setInUse(true);
		gameData.executeAtEndOfRound(performingClient, this);
	}
	
	@Override
	public void lateExecution(GameData gameData, Actor performingClient) {
		genRef.setInUse(false);
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		prioSelected = false;
		levelSelected = false;
		if (args.get(0).contains(powerLevelAction.getName())) {
			powerLevelAction.setArguments(args.subList(1, args.size()), performingClient);
			levelSelected = true;
		} else if (args.get(0).contains(powerPrioAction.getName())){
			powerPrioAction.setArguments(args.subList(1, args.size()), performingClient);
			prioSelected = true;
		}
		
	}

}
