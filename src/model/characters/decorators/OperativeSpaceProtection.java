package model.characters.decorators;

import model.Player;
import model.characters.general.GameCharacter;

public class OperativeSpaceProtection extends SpaceProtection {

	public OperativeSpaceProtection(GameCharacter chara) {
		super(chara);
	}
	
	@Override
	public char getIcon(Player whosAsking) {
		return '0';
	}

}
