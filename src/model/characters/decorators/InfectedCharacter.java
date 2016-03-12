package model.characters.decorators;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.Player;
import model.GameData;
import model.actions.Action;
import model.actions.characteractions.InfectAction;
import model.characters.GameCharacter;
import model.items.GameItem;
import model.npcs.CatNPC;
import model.npcs.ParasiteNPC;


/**
 * @author erini02
 * Decorator class for indicating that this character is
 * infected. I.e. that player is an antagonist for the
 * host game mode and can infect other players.
 */
public class InfectedCharacter extends CharacterDecorator {

	private Actor infector;
	private int infectedInRound;

	public InfectedCharacter(GameCharacter chara, Actor performingClient) {
		super(chara, "infected");
		this.infector = performingClient;
	}
	
	public void setInfectedInRound(int r) {
		this.infectedInRound = r;
	}
	
	public int getInfectedInRound() {
		return infectedInRound;
	}
	
	@Override
	public String getFullName() {
		return super.getFullName() + " (infected)";
	}

	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
//		int noOfTargets = 0;
//		
//		for (Actor cl : getInner().getPosition().getActors()) {
//			if (!cl.isInfected() && inf.isViableForThisAction(cl.getAsTarget())) {
//				noOfTargets++;
//			}
//		}
//		if (noOfTargets > 0) {
		InfectAction infect = new InfectAction(this.getClient());	
		if (infect.getTargets().size() > 0) {
			at.add(infect);
		}
		//}
	}
	
	@Override
	public boolean checkInstance(InstanceChecker infectChecker) {
		if (infectChecker.checkInstanceOf(this)) {
			return true;
		}
		return super.checkInstance(infectChecker);
	}

	@Override
	public List<GameItem> getStartingItems() {
		return getInner().getStartingItems();
	}

	public Actor getInfector() {
		return infector;
	}
	
}