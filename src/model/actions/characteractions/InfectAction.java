package model.actions.characteractions;

import graphics.sprites.Sprite;
import model.actions.QuickAction;
import model.characters.general.GameCharacter;
import util.Logger;
import util.MyRandom;
import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
import model.actions.general.AttackAction;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.actions.general.WatchAction;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;
import model.characters.decorators.InfectedCharacter;
import model.items.general.GameItem;
import model.npcs.HumanNPC;

public class InfectAction extends TargetingAction {

	private static final double BASE_INFECT_CHANCE = 0.75;
	private static final double REDUCED_INFECT_CHANCE = 0.25;

	public InfectAction(Actor ap) {
		super("Infect", new SensoryLevel(VisualLevel.STEALTHY, 
										 AudioLevel.INAUDIBLE,
										 OlfactoryLevel.UNSMELLABLE), ap);
	}

	@Override
	public boolean isViableForThisAction(Target target2) {
		return canBeInfected(target2);
	}

	@Override
	protected boolean requiresProximityToTarget() {
		return true;
	}

	public static boolean canBeInfected(Target target2) {
        if (!target2.isTargetable()) {
            return false;
        }

		if (target2 instanceof Player && !target2.isDead()) {
			return !((Player)target2).isInfected();
		}
		if (target2 instanceof HumanNPC && !target2.isDead()) {
			return !((HumanNPC)target2).isInfected();
		}
		return false;
	}
	
	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {

		if (target instanceof Actor) {
			Actor targetAsActor = (Actor)target;
			if (! targetAsActor.isInfected()) {
				double infectChance = BASE_INFECT_CHANCE;
				if (isReducedChance(targetAsActor, performingClient)) {		
					infectChance = REDUCED_INFECT_CHANCE;
					
				}
				if (MyRandom.nextDouble() < infectChance) {
					targetAsActor.beInfected(performingClient);
					performingClient.addTolastTurnInfo("You infected " + targetAsActor.getPublicName() + "!");
					((InfectedCharacter)targetAsActor.getCharacter()).setInfectedInRound(gameData.getRound());
				} else {
					targetAsActor.addTolastTurnInfo("The " + performingClient.getPublicName() + " tried to infect you!");
					performingClient.addTolastTurnInfo("You failed to infect the " + targetAsActor.getPublicName() + "!");
				}
			} else {
				performingClient.addTolastTurnInfo(target.getName() + " was already infected.");
			}
		} else {
			performingClient.addTolastTurnInfo("You can not infect " + target.getName() + ".");
		}
		
	}

	private boolean isReducedChance(Actor actor,
			Actor performingClient) {
		if (! (actor instanceof Player)) {
			return false;
		}
		

         if (GameCharacter.isWatching(actor, performingClient)) {
                Logger.log(Logger.INTERESTING,
                       "infect chance reduced because of watching...");
               return true;
         }


        if (GameCharacter.isAttacking(actor, performingClient)) {
            return true;
        }

		return false;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "infected";
	}


	@Override
	public Sprite getAbilitySprite() {
		return new Sprite("infectability", "weapons2.png", 22, 44, null);
	}
}
