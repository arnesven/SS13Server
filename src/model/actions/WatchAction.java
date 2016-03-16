package model.actions;

import util.MyRandom;
import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.items.GameItem;
import model.npcs.NPC;


public class WatchAction extends TargetingAction {


	public WatchAction(Actor ap) {
		super("Watch", new SensoryLevel(VisualLevel.VISIBLE_IF_CLOSE, 
										AudioLevel.INAUDIBLE, 
										OlfactoryLevel.UNSMELLABLE), ap);
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		
		
		String healthStr = "healthy";
		if (target.isDead()) {
			healthStr = "dead";
		} else if (target.getHealth() < target.getMaxHealth() ){
			healthStr = "unhealthy";
		}
		
		String itemStr = null;
		String name = target.getName();
		if (target instanceof Actor) {
			Actor cl = (Actor)target;
			name = cl.getBaseName();
			if (cl.getItems().size() > 0) {
				GameItem randomItem = cl.getItems().get(MyRandom.nextInt(cl.getItems().size()));
				itemStr = " and is carrying a " + randomItem.getPublicName(performingClient);
			}
		}		
		
		performingClient.addTolastTurnInfo(name + " looks " + healthStr + (itemStr==null?"":itemStr) + ".");

//		} else {
//			performingClient.addTolastTurnInfo("You're watching " + target.getName() + ".");
//			
//		}
	
	}

	@Override
	public boolean isViableForThisAction(Target target2) {
		return target2 instanceof Player || target2 instanceof NPC;
	}


}
