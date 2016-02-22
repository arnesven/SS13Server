package model.characters;

import java.util.ArrayList;

import model.Client;
import model.GameData;
import model.actions.Action;
import model.actions.ClientActionPerformer;
import model.actions.InfectAction;


/**
 * @author erini02
 * Decorator class for indicating that this character is
 * infected. I.e. that player is an antagonist for the
 * host game mode and can infect other players.
 */
public class InfectedCharacter extends CharacterDecorator {

	public InfectedCharacter(GameCharacter chara) {
		super(chara, "infected");
	}
	
	@Override
	public String getFullName() {
		return super.getFullName() + " (infected)";
	}

	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		int noOfTargets = 0;
		for (Client cl : getInner().getPosition().getClients()) {
			if (!cl.isInfected()) {
				noOfTargets++;
			}
		}
		if (noOfTargets > 0) {
			at.add(new InfectAction(new ClientActionPerformer(getClient())));
		}
	}
	
	@Override
	public boolean checkInstance(InstanceChecker infectChecker) {
		if (infectChecker.checkInstanceOf(this)) {
			return true;
		}
		return super.checkInstance(infectChecker);
	}
	
}
