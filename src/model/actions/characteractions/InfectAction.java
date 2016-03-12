package model.actions.characteractions;

import util.MyRandom;
import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
import model.actions.AttackAction;
import model.actions.SensoryLevel;
import model.actions.TargetingAction;
import model.actions.WatchAction;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.characters.decorators.InfectedCharacter;
import model.items.GameItem;
import model.npcs.CatNPC;
import model.npcs.HumanNPC;
import model.npcs.ParasiteNPC;

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
	
	public static boolean canBeInfected(Target target2) {
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
		
		Player actorAsPlayer = (Player)actor;
		if (actorAsPlayer.getNextAction() instanceof WatchAction) {
			if (((WatchAction)actorAsPlayer.getNextAction()).isArgumentOf(performingClient.getAsTarget())) {
				System.out.println("infect chance reduced because of watching...");			
				return true;
			}
		}
		
		if (actorAsPlayer.getNextAction() instanceof AttackAction) {
			if (((AttackAction)actorAsPlayer.getNextAction()).isArgumentOf(performingClient.getAsTarget())) {
				System.out.println("infect chance reduced because of attacking...");
				return true;
			}
		}
		
		return false;
	}

	
}
