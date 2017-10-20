package model.npcs.behaviors;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.items.general.ItemStack;
import util.Logger;
import util.MyRandom;
import model.GameData;
import model.Target;
import model.actions.general.GiveAction;
import model.items.general.GameItem;
import model.npcs.NPC;

public class GiveIfPossibleBehavior implements ActionBehavior {

	public void act(Actor npc, GameData gameData) {
		GiveAction give = new GiveAction(npc);
		
		if (give.getTargets().size() > 0 && npc.getItems().size() > 0) {
			Target randomTarget = MyRandom.sample(give.getTargets());
			List<String> args = new ArrayList<String>();
			GameItem itemToGive = getItem(npc);

			give.addWithWhat(itemToGive);
			args.add(randomTarget.getName());
			args.add(itemToGive.getPublicName(npc));
            if (itemToGive instanceof ItemStack) {
                String amount = "" + ((ItemStack) itemToGive).getAmount();
                args.add(amount);
            }
			give.setArguments(args, npc);
			Logger.log(npc.getPublicName() + " is giving to " + randomTarget.getName() + ", a " + itemToGive.getBaseName());
			give.printAndExecute(gameData);
		}
		
	}

	private GameItem getItem(Actor npc) {
		return MyRandom.sample(npc.getItems());
	}

}
