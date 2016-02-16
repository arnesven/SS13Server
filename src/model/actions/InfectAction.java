package model.actions;

import java.util.List;

import model.Client;
import model.GameData;
import model.GameItem;
import model.characters.GameCharacter;
import model.characters.InfectedCharacter;

public class InfectAction extends TargetingAction {

	public InfectAction(Client cl) {
		super("Infect", cl);
	}

	@Override
	protected boolean isViableForThisAction(Target target2) {
		if (target2 instanceof Client) {
			return !((Client)target2).isInfected();
		}
		return true;
	}
	
	@Override
	protected void applyTargetingAction(GameData gameData,
			Client performingClient, Target target, GameItem item) {

		if (target instanceof Client) {
			Client targetAsClient = (Client)target;
			if (! targetAsClient.isInfected()) {
				targetAsClient.setCharacter(new InfectedCharacter(targetAsClient.getCharacter()), targetAsClient);
				targetAsClient.addTolastTurnInfo("You were infected by " + performingClient.getCharacterPublicName() + 
						"! You are now on the Host team. Keep the humans from destroying the hive!");
				performingClient.addTolastTurnInfo("You infected " + targetAsClient.getCharacterPublicName() + "!");
			} else {
				performingClient.addTolastTurnInfo(target.getName() + " was already infected.");
			}
		} else {
			performingClient.addTolastTurnInfo("You can not infect " + target.getName() + ".");
		}
		
	}

	
}
