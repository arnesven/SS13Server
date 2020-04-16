package model.actions.objectactions;

import java.util.Iterator;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Syringe;
import model.mutations.Mutation;
import model.objects.consoles.GeneticsConsole;
import util.Logger;

public class FillWithKnownMutationAction extends ConsoleAction {

	private GeneticsConsole console;
	private Mutation selected;

	public FillWithKnownMutationAction(GeneticsConsole console2) {
		super("Mutate Genes", SensoryLevel.OPERATE_DEVICE);
		this.console = console2;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
        Syringe s = null;
        try {
            s = (Syringe) GameItem.getItem(performingClient, new Syringe());
        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "What, no syringe found?");
            performingClient.addTolastTurnInfo("What, the syringe is missing! Your action failed.");
            return;
        }

        if (s != null) {
			performingClient.addTolastTurnInfo(GeneticsConsole.BLAST_STRING);
			s.setBloodFrom(performingClient, gameData);
			s.setMutation(selected);
		} else {
			performingClient.addTolastTurnInfo("What? The syringe is gone! Your action failed.");
		}
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		if (GameItem.hasAnItem(whosAsking, new Syringe()) && 
				console.nuOfKnown() > 0) {
			Mutation m;
			for (Iterator<Mutation> it = console.getKnownMutationsIterator(); it.hasNext() ; ) {
				m = it.next();
				opt.addOption(m.getName());
			}
		}
		return opt;	
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "Fiddled with GeneTIX";
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		selected = null;
		Mutation m;
		for (Iterator<Mutation> it = console.getKnownMutationsIterator(); it.hasNext() ; ) {
			m = it.next();
			if (args.get(0).equals(m.getName())) {
				this.selected = m;
				break;
			}
		}

	}

}
