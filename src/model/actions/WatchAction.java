package model.actions;

import util.MyRandom;
import model.Client;
import model.GameData;
import model.items.GameItem;
import model.npcs.NPC;


public class WatchAction extends TargetingAction {


	public WatchAction(ActionPerformer ap) {
		super("Watch", true, ap);
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			ActionPerformer performingClient, Target target, GameItem item) {
		
		
		String healthStr = "healthy";
		if (target.getHealth() < target.getMaxHealth() ){
			healthStr = "unhealthy";
		}
		
		String itemStr = null;
		if (target instanceof Client) {
			Client cl = (Client)target;
			if (cl.getItems().size() > 0) {
				GameItem randomItem = cl.getItems().get(MyRandom.nextInt(cl.getItems().size()));
				itemStr = " and is carrying a " + randomItem.getName();
			}
		}
		
		performingClient.addTolastTurnInfo(target.getName() + " looks " + healthStr + (itemStr==null?"":itemStr) + ".");

//		} else {
//			performingClient.addTolastTurnInfo("You're watching " + target.getName() + ".");
//			
//		}
	
	}

	@Override
	protected boolean isViableForThisAction(Target target2) {
		return target2 instanceof Client || target2 instanceof NPC;
	}


}
