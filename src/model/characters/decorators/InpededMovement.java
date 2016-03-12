package model.characters.decorators;

import model.characters.GameCharacter;

public class InpededMovement extends CharacterDecorator {

	private String reason;
	private int newmovement;

	public InpededMovement(GameCharacter chara, String name, int i) {
		super(chara, name);
		this.reason = name;
		this.newmovement = i;	
	}
	
	@Override
	public int getMovementSteps() {
		return newmovement;
	}
	
	@Override
	public String getPublicName() {
		return super.getPublicName() + " (" + reason +")";
	}
	
	@Override
	public String getFullName() {
		return super.getFullName() + " (" + reason +")";
	}
}
