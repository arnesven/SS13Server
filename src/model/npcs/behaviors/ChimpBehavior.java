package model.npcs.behaviors;

import model.Actor;
import model.characters.general.ChimpCharacter;
import model.characters.general.SnakeCharacter;
import util.Logger;
import util.MyRandom;
import model.GameData;
import model.actions.general.SpeechAction;

import java.util.List;

public class ChimpBehavior implements ActionBehavior {

	private AttackIfPossibleBehavior atkBehavior = 
			new AttackAllActorsButNotTheseClasses(List.of(ChimpCharacter.class, SnakeCharacter.class));
	
	private GiveIfPossibleBehavior giveBehavior =
			new GiveIfPossibleBehavior();
	
	private PickUpIfPossibleBehavior pickUpBehavior =
			new PickUpIfPossibleBehavior();

	private SpontaneousAct gibberish = new SpontaneousAct(0.5, new SpeechAction("Oooh oooh  ah!"));

	@Override
	public void act(Actor npc, GameData gameData) {
		if (MyRandom.nextDouble() < 0.5) {
			pickUpBehavior.act(npc, gameData);
			if (pickUpBehavior.didHappen()) {
				Logger.log("Chimp picked up");
				return;
			}
		}
		
		double d = MyRandom.nextDouble();
		Logger.log("chimp random = " + d);
		if (d < 0.2) {
			gibberish.act(npc, gameData);
			Logger.log("Chimp said ooo");
		} else if (d < 0.70) {
			giveBehavior.act(npc, gameData);
			Logger.log("Chimp gave something");
		} else {
			atkBehavior.act(npc, gameData);
			Logger.log("chimp attacked");
		}
	}

}
