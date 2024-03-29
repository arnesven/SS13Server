package model.characters.decorators;

import model.characters.general.GameCharacter;

public class TraitorCharacter extends CharacterDecorator {

	public TraitorCharacter(GameCharacter chara) {
		super(chara, "Traitor");
	}
	
	@Override
	public String getFullName() {
		return super.getFullName() + " (Traitor)";
	}

}
