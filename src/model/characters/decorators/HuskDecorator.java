package model.characters.decorators;

import model.Actor;
import model.Player;
import model.characters.general.GameCharacter;

public class HuskDecorator extends CharacterDecorator {

	public HuskDecorator(GameCharacter chara) {
		super(chara, "Husk");
	}

	@Override
	public char getIcon(Player whosAsking) {
		return 'h';
	}
	
	@Override
	public String getPublicName() {
		return "Husk";
	}
	
	@Override
	public String getWatchString(Actor whosAsking) {
		return "Ugh! A shriveled up husk.";
	}
	
}
