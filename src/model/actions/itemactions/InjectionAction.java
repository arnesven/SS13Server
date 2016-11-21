package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.crew.DoctorCharacter;
import model.characters.crew.GeneticistCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.Syringe;
import util.MyRandom;

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
		    return;
        }


        double chance = 0.85;
        if (isProInjector(performingClient)) {
            chance = 0.95;
        }
        if (GameCharacter.isWatching((Actor)target, performingClient)) {
            chance = 0.5;
        }
        if (target == performingClient) {
            chance = 1.0;
        }


        if (MyRandom.nextDouble() < chance) {
            performingClient.addTolastTurnInfo("You injected " + nameOrSelf(performingClient, target) + " with the syringe.");
            if (performingClient.getAsTarget() != target) {
                ((Actor) target).addTolastTurnInfo("Ouch! " + performingClient.getPublicName() + " injected you with a syringe!");
            }

            Actor targetAsActor = (Actor) target;
            CharacterDecorator mutation = s.getMutationDecorator(targetAsActor);
            targetAsActor.setCharacter(mutation);
            s.empty();
        } else {
            ((Actor) target).addTolastTurnInfo("The " + performingClient.getPublicName() + " tried to inject you!");
            performingClient.addTolastTurnInfo("You failed to inject the " + target.getName() + ".");
        }
		
	}

    private boolean isProInjector(Actor performingClient) {
        return performingClient.getCharacter().checkInstance((GameCharacter gc ) -> gc instanceof DoctorCharacter) ||
                performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof GeneticistCharacter);
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
