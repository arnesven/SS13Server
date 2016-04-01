package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.ActionOption;
import model.actions.SensoryLevel;
import model.objects.GeneratorConsole;

public class PowerPrioAction extends Action {

	
	private GeneratorConsole genRef;
	private String selected;

	public PowerPrioAction(GeneratorConsole generatorConsole) {
		super("Power Priority", SensoryLevel.NO_SENSE);
		this.genRef = generatorConsole;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "";
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		for (String prioOrder : genRef.getPrios()) {
			opt.addOption(prioOrder);
		}
		return opt;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		genRef.setHighestPrio(selected);
		performingClient.addTolastTurnInfo("You set \"" + selected + "\" as highest power priority.");
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		selected = args.get(0);
	}

}
