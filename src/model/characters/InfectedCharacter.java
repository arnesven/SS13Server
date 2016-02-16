package model.characters;

import java.util.ArrayList;

import model.GameData;
import model.actions.Action;


public class InfectedCharacter extends CharacterDecorator {

	public InfectedCharacter(GameCharacter chara) {
		super(chara, "infected");
	}
	
	@Override
	public String getRealName() {
		return getName() + " (infected)";
	}

	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		at.add(new InfectAction(getClient()));
	}
	
}
