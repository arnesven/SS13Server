package model.actions.general;

import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;
import model.items.general.GameItem;
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

        boolean visible = true;
        if (target2 instanceof Actor) {
            visible = ((Actor) target2).getCharacter().isVisible();
        }

		return (target2 instanceof Player || target2 instanceof NPC) && visible;
	}

	@Override
	protected boolean requiresProximityToTarget() {
		return false;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "watched";
	}

	


}
