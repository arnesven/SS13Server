package model.characters.decorators;

import model.characters.GameCharacter;

public class DrunkDecorator extends AttributeChangeDecorator {

	public DrunkDecorator(GameCharacter chara) {
		super(chara, "Drunk", false);
	}

}
