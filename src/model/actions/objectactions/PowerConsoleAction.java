package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.ActionOption;
import model.actions.SensoryLevel;
import model.objects.GeneratorConsole;

public class PowerConsoleAction extends Action {

	private PowerLevelAction powerLevelAction;
	private PowerPrioAction powerPrioAction;
	private boolean prioSelected = false;
	private GeneratorConsole genRef;
	
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
			System.out.println("Prio changed!");
			powerPrioAction.execute(gameData, performingClient);
		} else {
			System.out.println("Level changed!");
			powerLevelAction.execute(gameData, performingClient);
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
	//	System.out.println(args.get(0) + " =? " + powerLevelAction.getName());
		if (args.get(0).contains(powerLevelAction.getName())) {
			powerLevelAction.setArguments(args.subList(1, args.size()), performingClient);
			prioSelected = false;
		} else {
			powerPrioAction.setArguments(args.subList(1, args.size()), performingClient);
			prioSelected = true;
		}
		
	}

}
