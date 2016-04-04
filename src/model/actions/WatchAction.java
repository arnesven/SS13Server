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
		
		

		performingClient.addTolastTurnInfo(((Actor)target).getCharacter().getWatchString(performingClient));
	
	}

	@Override
	public boolean isViableForThisAction(Target target2) {
		return target2 instanceof Player || target2 instanceof NPC;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "watched";
	}

	


}
