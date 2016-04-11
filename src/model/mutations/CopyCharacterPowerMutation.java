package model.mutations;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;

public class CopyCharacterPowerMutation extends Mutation {

	private GameCharacter source;

	public CopyCharacterPowerMutation(GameCharacter target) {
		super(target.getPublicName() + " genes");
		this.source = target;
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new CharacterDecorator(forWhom.getCharacter(), this.getName()) {
			
			@Override
			public void addCharacterSpecificActions(GameData gameData,
					ArrayList<Action> at) {
				super.addCharacterSpecificActions(gameData, at);
				source.setPosition(this.getPosition());
				source.addCharacterSpecificActions(gameData, at);
			}
			
		};
	}

	

}
