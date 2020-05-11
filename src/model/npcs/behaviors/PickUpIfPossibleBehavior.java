package model.npcs.behaviors;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import util.MyRandom;
import model.GameData;
import model.actions.general.PickUpAction;
import model.items.general.GameItem;

public class PickUpIfPossibleBehavior implements ActionBehavior {

	private boolean didHappen = false;

	@Override
	public void act(Actor npc, GameData gameData) {
		List<GameItem> items = npc.getPosition().getItems();
		
		if (items.size() > 0) {
			PickUpAction pua = new PickUpAction(npc);
			List<String> args = new ArrayList<>();
			args.add(MyRandom.sample(pua.getOptions(gameData, npc).getSuboptions()).getName());
			pua.setActionTreeArguments(args, npc);
			pua.doTheAction(gameData, npc);
			this.didHappen  = true;
		}
	}
	
	public boolean didHappen() {
		return didHappen;
	}

}
