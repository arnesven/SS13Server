package model.actions;

import util.MyRandom;
import model.Actor;
import model.Player;
import model.GameData;
import model.characters.InfectedCharacter;
import model.items.GameItem;
import model.npcs.CatNPC;
import model.npcs.ParasiteNPC;

public class InfectAction extends TargetingAction {

	private static final double BASE_INFECT_CHANCE = 0.75;
	private static final double REDUCED_INFECT_CHANCE = 0.25;

	public InfectAction(Actor ap) {
		super("Infect", true, ap);
	}

	@Override
	protected boolean isViableForThisAction(Target target2) {
		if (target2 instanceof Player) {
			return !((Player)target2).isInfected();
		}
		if (target2 instanceof ParasiteNPC || target2 instanceof CatNPC) {
			return false;
		}
		return true;
	}
	
	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {

		if (target instanceof Player) {
			Player targetAsClient = (Player)target;
			if (! targetAsClient.isInfected()) {
				double infectChance = BASE_INFECT_CHANCE;
				if (isReducedChance(targetAsClient, performingClient)) {		
					infectChance = REDUCED_INFECT_CHANCE;
					
				}
				if (MyRandom.nextDouble() < infectChance) {
					targetAsClient.setCharacter(new InfectedCharacter(targetAsClient.getCharacter(), performingClient));
					targetAsClient.addTolastTurnInfo("You were infected by " + performingClient.getPublicName() + 
							"! You are now on the Host team. Keep the humans from destroying the hive!");
					performingClient.addTolastTurnInfo("You infected " + targetAsClient.getCharacterPublicName() + "!");
				} else {
					targetAsClient.addTolastTurnInfo("The " + performingClient.getPublicName() + " tried to infect you!");
					performingClient.addTolastTurnInfo("You failed to infect the " + targetAsClient.getCharacterPublicName() + "!");
				}
			} else {
				performingClient.addTolastTurnInfo(target.getName() + " was already infected.");
			}
		} else {
			performingClient.addTolastTurnInfo("You can not infect " + target.getName() + ".");
		}
		
	}

	private boolean isReducedChance(Player targetAsClient,
			Actor performingClient) {
		if (targetAsClient.getNextAction() instanceof WatchAction) {
			if (((WatchAction)targetAsClient.getNextAction()).isArgumentOf(performingClient.getAsTarget())) {
				System.out.println("infect chance reduced because of watching...");			
				return true;
			}
		}
		
		if (targetAsClient.getNextAction() instanceof AttackAction) {
			if (((AttackAction)targetAsClient.getNextAction()).isArgumentOf(performingClient.getAsTarget())) {
				System.out.println("infect chance reduced because of attacking...");
				return true;
			}
		}
		
		return false;
	}

	
}
