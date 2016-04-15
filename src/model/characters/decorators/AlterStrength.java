package model.characters.decorators;

import model.characters.general.GameCharacter;

public class AlterStrength extends AttributeChangeDecorator {

	private double newStrength;

	public AlterStrength(GameCharacter chara, String reason, boolean visible, double newStrength) {
		super(chara, reason, visible);
		this.newStrength = newStrength;
	}

	@Override
	public boolean isEncumbered() {
		return getTotalWeight() > newStrength;
	}
	
	@Override
	public int getMovementSteps() {
		if (super.getMovementSteps() == 1 && super.isEncumbered()) {
			if (getTotalWeight() > newStrength) {
				return 1;
			} else {
				return 2;
			}
		} else {
			return super.getMovementSteps();
		}
	}

}
