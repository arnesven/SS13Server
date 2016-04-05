package model.npcs.behaviors;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.npcs.NPC;
import model.objects.consoles.CrimeRecordsConsole;

public class ArrestCriminalBehavior implements ActionBehavior {

	private CrimeRecordsConsole console;

	public ArrestCriminalBehavior(GameData gameData) {
		this.console = CrimeRecordsConsole.find(gameData);
	}

	@Override
	public void act(NPC npc, GameData gameData) {
		List<Actor> acts = new ArrayList<>();
		
		for (Actor a : console.getReportedActors().keySet()) {
			if (npc.getPosition().getActors().contains(a)) {
				acts.add(a);
			}
		}
		
		if (acts.size() == 0) {
			return; // nobody to arrest
		}
		
		Actor worst = null;
		int points = -1;
		for (Actor a : acts) {
			int sent = console.sumCrimesFor(a);
			if (sent > points) {
				points = sent;
				worst = a;
			}
		}
		
		for (Actor a : npc.getPosition().getActors()) {
			a.addTolastTurnInfo("SecuriTRON; \"" + worst.getBaseName() + 
								" you are arrested for " + console.getCrimeStringFor(worst) + ".\"");
			if (a != worst) {
				a.addTolastTurnInfo(worst.getPublicName() + " was telebrigged by SecuriTRON!");
			}
		}
		console.teleBrig(worst, gameData);
	}

}
