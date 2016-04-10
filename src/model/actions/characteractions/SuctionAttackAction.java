package model.actions.characteractions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.actions.TargetingAction;
import model.characters.AnimalCharacter;
import model.characters.ChangelingCharacter;
import model.characters.GameCharacter;
import model.characters.RobotCharacter;
import model.characters.decorators.HuskDecorator;
import model.items.GameItem;
import model.items.weapons.Weapon;
import model.npcs.HumanNPC;
import model.objects.GameObject;

public class SuctionAttackAction extends TargetingAction {

	private ChangelingCharacter ling;

	public SuctionAttackAction(Actor ap, ChangelingCharacter ling) {
		super("Suck Life", SensoryLevel.PHYSICAL_ACTIVITY, ap);
		this.ling = ling;
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		boolean succ = target.beAttackedBy(performingClient, new Weapon("Proboscis", 0.95, 1.0, false, 0.0));
		if (succ) {
			Actor targetAsActor = ((Actor) target);
			ling.addToSucked(((Actor) targetAsActor));
			afterSucking(ling, ((Actor) targetAsActor), gameData);
			if (target.isDead()) {
				targetAsActor.setCharacter(new HuskDecorator(targetAsActor.getCharacter()));
			}
		}

	}

	protected void afterSucking(ChangelingCharacter changeling, Actor target, GameData gameData) { }

	@Override
	public boolean isViableForThisAction(Target target2) {
		
		
		return ChangelingCharacter.isDetectable(target2);
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "Sucked the life out of ";
	}



}
