package model.npcs.behaviors;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.items.NoSuchThingException;
import model.npcs.NPC;
import model.objects.consoles.CrimeRecordsConsole;

public class ArrestCriminalBehavior implements ActionBehavior {

	private CrimeRecordsConsole console;

	public ArrestCriminalBehavior(CrimeRecordsConsole console) throws NoSuchThingException {
		this.console = console;
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
		
		Actor worst = acts.get(0);
		int points = console.sumCrimesFor(acts.get(0));
		for (Actor a : acts) {
			int sent = console.sumCrimesFor(a);
			if (sent > points) {
				points = sent;
				worst = a;
			}
		}
		
		for (Actor a : npc.getPosition().getActors()) {
            try {
                a.addTolastTurnInfo("SecuriTRON; \"" + worst.getBaseName() +
                                    " you are arrested for " + console.getCrimeStringFor(worst) + ".\"");
            } catch (NoSuchThingException e) {
                throw new IllegalStateException("Can not arrest anybody if there is no crime console on the station");
            }
            if (a != worst) {
				a.addTolastTurnInfo(worst.getPublicName() + " was telebrigged by SecuriTRON!");
			}
		}
		console.teleBrig(worst, gameData);
	}

}
