package model.actions.objectactions;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import util.Pair;
import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.ActionOption;
import model.actions.SensoryLevel;
import model.objects.CrimeRecordsConsole;

public class PardonAction extends Action {

	private String selectedGuy;
	private CrimeRecordsConsole console;

	public PardonAction(CrimeRecordsConsole console) {
		super("Pardon Crimes", SensoryLevel.NO_SENSE);
		this.console = console;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Pardoned crime";
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		
		for (Actor a : console.getReportedActors().keySet()) {
			String entry = a.getBaseName();
			
			entry += " (" + console.getCrimeStringFor(a) + ")";
			
			opt.addOption(entry);
		}
		
		return opt;
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		Actor guy = null;
		for (Actor a : console.getReportedActors().keySet()) {
			System.out.println("Selected guy: " + selectedGuy + " a: " + a);
			if (selectedGuy.contains(a.getBaseName())) {
				guy = a;
				break;
			}
		}
		
		List<Pair<String, Actor>> record = console.getReportedActors().get(guy);
		Iterator<Pair<String, Actor>> iter = record.iterator();
		while (iter.hasNext()) {
			Pair<String, Actor> pair = iter.next();
			if (pair.second.getSpeed() > performingClient.getSpeed()) {
				performingClient.addTolastTurnInfo("Cannot pardon \"" + pair.first + "\". " + pair.second.getBaseName() + " outranks you!");
			} else {
				iter.remove();
				performingClient.addTolastTurnInfo("Pardoned \"" + pair.first + "\". ");
			}
		}
		if (record.isEmpty()) {
			console.getReportedActors().remove(guy);
		}
	
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		selectedGuy = args.get(0);
	}
}
