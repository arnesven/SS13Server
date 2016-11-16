package model.actions.objectactions;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.objects.consoles.GeneticsConsole;

public class GeneTIXAction extends ConsoleAction {

	private List<ConsoleAction> subActs = new ArrayList<>();
	private GeneticsConsole console;
	private ConsoleAction selected;

	public GeneTIXAction(GameData gameData, Actor cl,
			GeneticsConsole geneticsConsole) {
		super(geneticsConsole.getName(), SensoryLevel.OPERATE_DEVICE);
		this.console = geneticsConsole;
		{
			ConsoleAction mutate = new MutateSyringeAction(console);
			if (mutate.getOptions(gameData, cl).numberOfSuboptions() > 0) {
				subActs.add(mutate);
			}
		}
		{
			ConsoleAction mutate = new FillWithKnownMutationAction(console);
			if (mutate.getOptions(gameData, cl).numberOfSuboptions() > 0) {
				subActs.add(mutate);
			}
		}
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		for (Action a : subActs) {
			opt.addOption(a.getOptions(gameData, whosAsking));
		}
		return opt;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Fiddled with " + console.getName();
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		this.selected.execute(gameData, performingClient);
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		for (ConsoleAction a : subActs) {
			if (a.getName().equals(args.get(0))) {
				selected = a;
				a.setArguments(args.subList(1, args.size()), performingClient);
				break;
			}
		}

	}

}
