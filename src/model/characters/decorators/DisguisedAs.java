package model.characters.decorators;

import model.characters.GameCharacter;

public class DisguisedAs extends CharacterDecorator {

	private String type;

	public DisguisedAs(GameCharacter chara, String type) {
		super(chara, "Disguise");
		this.type = type;
	}
	
	@Override
	public String getPublicName() {
		if (isDead()) {
			return type + " (dead)";
		}
		return type;
	}
	
	

}
