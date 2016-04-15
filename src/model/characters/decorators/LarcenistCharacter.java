package model.characters.decorators;

import java.util.ArrayList;

import model.GameData;
import model.actions.Action;
import model.actions.characteractions.StealAction;
import model.characters.general.GameCharacter;

public class LarcenistCharacter extends CharacterDecorator {

	public LarcenistCharacter(GameCharacter chara) {
		super(chara, "Larcenist");
	}

	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		Action stealAction = new StealAction(this.getActor());
		if (stealAction.getOptions(gameData, this.getActor()).numberOfSuboptions() > 0) {
			at.add(stealAction);
		}
	}
	
}
