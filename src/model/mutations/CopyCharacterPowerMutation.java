package model.mutations;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.characters.general.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.InstanceChecker;

public class CopyCharacterPowerMutation extends Mutation {

	private GameCharacter source;

	public CopyCharacterPowerMutation(GameCharacter target) {
		super(target.getPublicName() + " genes");
		this.source = target;
	}

	@Override
	public CharacterDecorator getDecorator(final Actor forWhom) {
		return new CharacterDecorator(forWhom.getCharacter(), this.getName()) {

			@Override
			public void addCharacterSpecificActions(GameData gameData,
					ArrayList<Action> at) {
				super.addCharacterSpecificActions(gameData, at);
				source.setPosition(this.getPosition());
				source.setActor(forWhom);
				source.addCharacterSpecificActions(gameData, at);
			}

			@Override
			public boolean checkInstance(InstanceChecker infectChecker) {

				if (infectChecker.checkInstanceOf(source)) {
					return true;
				}

				return super.checkInstance(infectChecker);
			}

		};


	}




}
