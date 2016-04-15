package model.characters.decorators;

import model.characters.general.GameCharacter;

public class AlterMovement extends AttributeChangeDecorator {

	private int newmovement;

	public AlterMovement(GameCharacter chara, String name, 
			boolean visible, int i) {
		super(chara, name, visible);
		this.newmovement = i;	
	}
	
	@Override
	public int getMovementSteps() {
		return newmovement;
	}
	

}
