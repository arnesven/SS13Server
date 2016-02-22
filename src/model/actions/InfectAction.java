package model.actions;

import java.util.List;

import util.MyRandom;
import model.Client;
import model.GameData;
import model.characters.GameCharacter;
import model.characters.InfectedCharacter;
import model.items.GameItem;
import model.npcs.ParasiteNPC;

public class InfectAction extends TargetingAction {

	private static final double BASE_INFECT_CHANCE = 0.75;
	private static final double REDUCED_INFECT_CHANCE = 0.25;

	public InfectAction(ActionPerformer ap) {
		super("Infect", true, ap);
	}

	@Override
	protected boolean isViableForThisAction(Target target2) {
		if (target2 instanceof Client) {
			return !((Client)target2).isInfected();
		}
		if (target2 instanceof ParasiteNPC) {
			return false;
		}
		return true;
	}
	
	@Override
	protected void applyTargetingAction(GameData gameData,
			ActionPerformer performingClient, Target target, GameItem item) {

		if (target instanceof Client) {
			Client targetAsClient = (Client)target;
			if (! targetAsClient.isInfected()) {
				double infectChance = BASE_INFECT_CHANCE;
				if (targetAsClient.getNextAction() instanceof WatchAction) {
					if (((WatchAction)targetAsClient.getNextAction()).isArgumentOf(performingClient.getAsTarget())) {
						infectChance = REDUCED_INFECT_CHANCE;
						System.out.println("infect chance reduced because of watching...");
						
					}
				}
				if (MyRandom.nextDouble() < infectChance) {
					targetAsClient.setCharacter(new InfectedCharacter(targetAsClient.getCharacter()));
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

	
}
