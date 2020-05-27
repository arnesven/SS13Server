package model.npcs.behaviors;

import model.Actor;
import util.MyRandom;
import model.GameData;
import model.actions.general.SpeechAction;

import java.util.ArrayList;

public class CrazyBehavior implements ActionBehavior {

	private AttackIfPossibleBehavior atkBehavior = 
						new AttackAllActorsButNotTheseClasses(new ArrayList<>());
	
	private SpontaneousAct gibberish = new SpontaneousAct(0.5, new SpeechAction("Aaaaargh!"));
	
	@Override
	public void act(Actor npc, GameData gameData) {
		if (MyRandom.nextDouble() < 0.5) {
			gibberish.act(npc, gameData);
		} else {
			atkBehavior.act(npc, gameData);
		}
	}

}
