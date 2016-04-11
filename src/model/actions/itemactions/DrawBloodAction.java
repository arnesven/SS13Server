package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.SensoryLevel;
import model.actions.TargetingAction;
import model.items.GameItem;
import model.items.Syringe;
import model.npcs.NPC;
import model.actions.SensoryLevel.*;
import model.characters.RobotCharacter;
import model.characters.crew.RoboticistCharacter;

public class DrawBloodAction extends TargetingAction {

	public DrawBloodAction(Actor ap) {
		super("Draw Blood", new SensoryLevel(VisualLevel.STEALTHY, 
				AudioLevel.INAUDIBLE, OlfactoryLevel.UNSMELLABLE), ap);
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		Syringe syringe = Syringe.findSyringe(performingClient);
		if (syringe == null) {
			performingClient.addTolastTurnInfo("What? The Syringe is gone! Your action failed.");
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

}
