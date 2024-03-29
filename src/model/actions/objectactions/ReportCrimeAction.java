package model.actions.objectactions;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.objects.consoles.CrimeRecordsConsole;
import model.actions.general.SensoryLevel;

public class ReportCrimeAction extends ConsoleAction {


	private CrimeRecordsConsole console;
	private String selectedGuy;
	private String selectedCrime;
	private int selectedDuration = -1;

	public ReportCrimeAction(CrimeRecordsConsole crimeRecordsConsole) {
		super("Report Crime", SensoryLevel.NO_SENSE);
		this.console = crimeRecordsConsole;
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		List<Actor> acts = new ArrayList<>();
		acts.addAll(gameData.getActors());
		Collections.sort(acts, new Comparator<Actor>() {
			@Override
			public int compare(Actor o1, Actor o2) {
				return o1.getPublicName().compareTo(o2.getPublicName());
			}
		});
		
		for (Actor a : acts) {
			if (a.getCharacter().isCrew() || console.getExtraBaddies().contains(a)) {
				ActionOption guy = new ActionOption(a.getCharacter().getBaseName());
				for (int i = 0; i < console.getAllCrimes().length; ++i) {
					guy.addOption(console.getAllCrimes()[i] +
							" (" + console.getSentenceLengths()[i] + ")");
				}
				opt.addOption(guy);
			}
		}
		return opt;
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		Actor guy = null;
		for (Actor a : gameData.getActors()) {
			if (selectedGuy.contains(a.getBaseName())) {
				guy = a;
				break;
			}
		}
		if (console.isPowered() && !console.isBroken()) {
			if (selectedDuration == -1) {
				console.addReport(guy, selectedCrime, performingClient);
			} else {
				console.addReport(guy, selectedCrime, performingClient, selectedDuration);
			}
			performingClient.addTolastTurnInfo("You reported " + guy.getBaseName() + " for \"" + selectedCrime + "\".");
		} else {
			performingClient.addTolastTurnInfo("Something is wrong with the console...");
		}
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		selectedGuy = args.get(0);
		selectedCrime = args.get(1).replaceAll(" \\(\\d\\)", "");
		if (args.size() > 2) {
			try {
				selectedDuration = Integer.parseInt(args.get(2));
			} catch (NumberFormatException nfe) {

			}
		}
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Reported crime";
	}
	

}
