package model.characters.decorators;

import model.characters.general.GameCharacter;

public class DisguisedAs extends CharacterDecorator {

	private String type;

	public DisguisedAs(GameCharacter chara, String type) {
		super(chara, "Disguise");
		this.type = type;
	}
	
	@Override
	public String getPublicName() {
		return type;
	}
	
	

}
