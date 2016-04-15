package model.items.suits;

import model.characters.general.GameCharacter;
import model.characters.decorators.CharacterDecorator;

class PrisonerDecorator extends CharacterDecorator {
	private int num;

	public PrisonerDecorator(GameCharacter chara, int number) {
		super(chara, "Prisoner");
		this.num = number;
	}

	@Override
	public String getPublicName() {
		String res = "Prisoner #" + num;
		if (isDead()) {
			res += " (dead)";
		}
		return res;
	}
}