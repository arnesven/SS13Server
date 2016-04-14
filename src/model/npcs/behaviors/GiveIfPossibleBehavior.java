package model.npcs.behaviors;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.GameData;
import model.Target;
import model.actions.GiveAction;
import model.items.general.GameItem;
import model.npcs.NPC;

public class GiveIfPossibleBehavior {

	public void act(NPC npc, GameData gameData) {
		GiveAction give = new GiveAction(npc);
		
		if (give.getTargets().size() > 0 && npc.getItems().size() > 0) {
			Target randomTarget = MyRandom.sample(give.getTargets());
			List<String> args = new ArrayList<String>();
			GameItem itemToGive = getItem(npc);
			give.addWithWhat(itemToGive);
			args.add(randomTarget.getName());
			args.add(itemToGive.getPublicName(npc));
			give.setArguments(args, npc);
			System.out.println(npc.getPublicName() + " is giving to " + randomTarget.getName() + "!");
			give.printAndExecute(gameData);
		}
		
	}

	private GameItem getItem(NPC npc) {
		return MyRandom.sample(npc.getItems());
	}

}
