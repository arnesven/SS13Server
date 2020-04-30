package model.actions.objectactions;

import java.util.List;
import java.util.Scanner;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.objects.consoles.GeneratorConsole;

public class PowerLevelAction extends Action {

	private String[] opts = new String[]{"Fixed Decrease", "Ongoing Decrease"};
	private boolean fixed;
	private boolean increase;
	private GeneratorConsole genRef;
	private String selected;
	
	public PowerLevelAction(GeneratorConsole generatorConsole) {
		super("Power Level", SensoryLevel.NO_SENSE);
		this.genRef = generatorConsole;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "";
	}
	
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		String str = String.format("%.1f", genRef.getSource().getPowerLevel());
		ActionOption opt = new ActionOption(this.getName() + "(" + str + " MW)");
		for (int i = 0; i < opts.length ; ++i) {
			opt.addOption(opts[i]);
		}
		return opt;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		genRef.getSource().setLevel(fixed, increase);
		performingClient.addTolastTurnInfo("You set the power level to \"" + selected + "\"");

	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		selected = args.get(0);
		Scanner scanner = new Scanner(args.get(0));
		fixed = (scanner.next().equals("Fixed"));
		increase = (scanner.next().equals("Increase"));
		scanner.close();
		
	}

}
