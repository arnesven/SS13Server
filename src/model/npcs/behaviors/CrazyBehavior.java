package model.npcs.behaviors;

import util.MyRandom;
import model.GameData;
import model.actions.general.SpeechAction;
import model.npcs.NPC;

public class CrazyBehavior implements ActionBehavior {

	private AttackIfPossibleBehavior atkBehavior = 
						new AttackIfPossibleBehavior();
	
	private SpontaneousAct gibberish = new SpontaneousAct(0.5, new SpeechAction("Aaaaargh!"));
	
	@Override
	public void act(NPC npc, GameData gameData) {
		if (MyRandom.nextDouble() < 0.5) {
			gibberish.act(npc, gameData);
		} else {
			atkBehavior.act(npc, gameData);
		}
	}

}
