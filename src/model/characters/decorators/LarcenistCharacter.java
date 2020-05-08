package model.characters.decorators;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.characteractions.StealAction;
import model.actions.itemactions.ShowStealFancyFrameAction;
import model.characters.general.GameCharacter;

public class LarcenistCharacter extends CharacterDecorator {

	public LarcenistCharacter(GameCharacter chara) {
		super(chara, "Larcenist");
	}

	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		addLarcenyActions(gameData, at, getActor());
	}

	public static void addLarcenyActions(GameData gameData, ArrayList<Action> at, Actor actor) {
		Action stealAction = new StealAction(actor);
		if (stealAction.getOptions(gameData, actor).numberOfSuboptions() > 0) {
			at.add(stealAction);
		}
		Action showStealFancyFrameAction = new ShowStealFancyFrameAction(gameData, at, actor);
		if (showStealFancyFrameAction.getOptions(gameData, actor).numberOfSuboptions() > 0) {
			at.add(showStealFancyFrameAction);
		}
	}

}
