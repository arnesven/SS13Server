package model.items;

import java.util.ArrayList;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.itemactions.SermonAction;
import model.characters.GameCharacter;
import model.characters.crew.ChaplainCharacter;
import model.characters.decorators.InstanceChecker;

public class Bible extends GameItem {

	public Bible() {
		super("Holy Bible", 1.0);
	}

	@Override
	public Bible clone() {
		return new Bible();
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		if (isAGodlyMan(cl.getCharacter())) {
			at.add(new SermonAction());
		}
	}

	private boolean isAGodlyMan(GameCharacter character) {
		InstanceChecker check = new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof ChaplainCharacter;
			}
		};
		return character.checkInstance(check);
	}
	
	@Override
	protected char getIcon() {
		return '+';
	}
	

}
