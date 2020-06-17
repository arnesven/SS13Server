package model.actions.characteractions;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.characters.general.ChangelingCharacter;

public class SuctionAndTransformAttackAction extends SuctionAttackAction {

	public SuctionAndTransformAttackAction(Actor ap, ChangelingCharacter ling) {
		super(ap, ling);
		this.setName("Suck and Change");
	}
	
	@Override
	protected void afterSucking(ChangelingCharacter changeling, Actor target,
			GameData gameData) {
		String before = changeling.getPublicName();
		changeling.changeInto(target.getCharacter());
		for (Actor a : target.getPosition().getActors()) {
			if (a.getCharacter() != changeling) {
				a.addTolastTurnInfo(before + " changed into " + changeling.getPublicName() + "!");
			}
		}
	}

	@Override
	public Sprite getAbilitySprite() {
		return new Sprite("suckandchangeformabi", "weapons2.png", 20, 44, null);
	}
	


}
