package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.ActionOption;
import model.events.SentenceCountdownEvent;
import model.items.GameItem;
import model.items.Syringe;
import model.mutations.MutationFactory;
import model.objects.consoles.GeneticsConsole;
import model.actions.SensoryLevel;

public class MutateSyringeAction extends ConsoleAction {

	private GeneticsConsole console;
	private Syringe syringe;

	public MutateSyringeAction(GeneticsConsole geneticsConsole) {
		super("Analyze Genes", SensoryLevel.OPERATE_DEVICE);
		this.console = geneticsConsole;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Fiddled with console";
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		
		for (GameItem gi : whosAsking.getItems()) {
			if (gi instanceof Syringe) {
				if (((Syringe)gi).isFilled()) {
					opt.addOption(getNameFor((Syringe)gi));
				}
			}
		}
		
		return opt;
	}
	
	private String getNameFor(Syringe gi) {
		return gi.getBaseName() + " (" + gi.getMutation().getName() + ")";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (performingClient.getItems().contains(syringe)) {
			performingClient.addTolastTurnInfo(GeneticsConsole.BLAST_STRING);
			console.getKnownMutations().add(syringe.getMutation());
			syringe.setMutation(MutationFactory.getRandomMutation());
		} else {
			performingClient.addTolastTurnInfo("What? The syringe is gone! Your action failed!");
		}

	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		this.syringe = null;
		for (GameItem gi : performingClient.getItems()) {
			if (gi instanceof Syringe) {
				if (getNameFor((Syringe)gi).equals(args.get(0))) {
					this.syringe = (Syringe)gi;
					break;
				}
			}
		}
		
	}

}
