package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.QuickAction;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.items.general.GameItem;
import model.items.general.Syringe;
import model.actions.general.SensoryLevel.*;

import java.util.ArrayList;
import java.util.List;

public class DrawBloodAction extends TargetingAction implements QuickAction {

	public DrawBloodAction(Actor ap) {
		super("Draw Blood", new SensoryLevel(VisualLevel.STEALTHY, 
				AudioLevel.INAUDIBLE, OlfactoryLevel.UNSMELLABLE), ap);
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		Syringe syringe = Syringe.findSyringe(performingClient, false);
		if (syringe == null) {
			performingClient.addTolastTurnInfo("What? The Syringe is gone! Your action failed.");
		    return;
        }
		performingClient.addTolastTurnInfo("You drew blood from " + target.getName() + ".");
		syringe.setBloodFrom((Actor)target, gameData);
	}


	@Override
	public boolean isViableForThisAction(Target target2) {
		return Syringe.hasBloodToDraw(target2);
	}
	
	@Override
	protected void addMoreTargets(Actor ap) {
		addTarget(ap.getAsTarget());
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "Drew blood into a syringe";
	}

	@Override
	public void performQuickAction(GameData gameData, Player performer) {
		execute(gameData, performer);
	}

	@Override
	public boolean isValidToExecute(GameData gameData, Player performer) {
		return true;
	}

	@Override
	public List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer) {
		List<Player> result = new ArrayList<>();
		result.add(performer);
		if (target instanceof  Player) {
			result.add((Player)target);
		}
		return result;
	}
}
