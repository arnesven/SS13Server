package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.SensoryLevel;
import model.actions.TargetingAction;
import model.characters.decorators.CharacterDecorator;
import model.items.general.GameItem;
import model.items.general.Syringe;

public class InjectionAction extends TargetingAction {

	public InjectionAction(Actor ap) {
		super("Inject", SensoryLevel.PHYSICAL_ACTIVITY, ap);
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		Syringe s = Syringe.findSyringe(performingClient);
		if (s == null) {
			performingClient.addTolastTurnInfo("What? The Syringe is gone! Your action failed.");
		}
		performingClient.addTolastTurnInfo("You injected " + nameOrSelf(performingClient, target) + " with the syringe.");
		if (performingClient.getAsTarget() != target) {
			((Actor)target).addTolastTurnInfo("Ouch! " + performingClient.getPublicName() + " injected you with a syringe!");
		}
		
		Actor targetAsActor = (Actor)target;
		CharacterDecorator mutation = s.getMutationDecorator(targetAsActor);
		targetAsActor.setCharacter(mutation);
		s.empty();
		
	}

	private String nameOrSelf(Actor performingClient, Target target) {
		if (performingClient.getAsTarget() == target) {
			return "yourself";
		}
		return target.getName();
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
		return "Injected with syringe";
	}

}
