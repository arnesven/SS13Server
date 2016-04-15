package model.characters.decorators;

import model.characters.general.GameCharacter;

public class ChilledDecorator extends CharacterDecorator {

	public ChilledDecorator(GameCharacter chara) {
		super(chara, "Chilled");
	}
	
	@Override
	public String getFullName() {
		return super.getFullName() + " (Cold)";
	}

}
