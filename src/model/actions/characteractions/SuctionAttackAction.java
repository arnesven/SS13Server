package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.general.ChangelingCharacter;
import model.characters.decorators.HuskDecorator;
import model.items.general.GameItem;
import model.items.weapons.Probocis;

public class SuctionAttackAction extends TargetingAction {

	private ChangelingCharacter ling;

	public SuctionAttackAction(Actor ap, ChangelingCharacter ling) {
		super("Suck Life", SensoryLevel.PHYSICAL_ACTIVITY, ap);
		this.ling = ling;
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		boolean succ = target.beAttackedBy(performingClient, new Probocis(), gameData);
		if (succ) {
			Actor targetAsActor = ((Actor) target);
			ling.addToSucked(targetAsActor);
			afterSucking(ling, targetAsActor, gameData);
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
