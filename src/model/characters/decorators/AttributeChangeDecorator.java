package model.characters.decorators;

import model.characters.GameCharacter;

public class AttributeChangeDecorator extends CharacterDecorator {

	private String reason;
	private boolean visible;

	public AttributeChangeDecorator(GameCharacter chara, String reason, 
			boolean visible) {
		super(chara, "Alter Strength");
		this.reason = reason;
		this.visible = visible;
	}
	
	@Override
	public String getPublicName() {
		if (visible) {
			return super.getPublicName() + " (" + reason +")";
		}
		return super.getPublicName();
	}
	
	@Override
	public String getFullName() {
		return super.getFullName() + " (" + reason +")";
	}
	
}
