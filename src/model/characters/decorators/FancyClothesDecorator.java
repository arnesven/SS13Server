package model.characters.decorators;

import model.characters.general.GameCharacter;

public class FancyClothesDecorator extends NameAddDecorator {

	public FancyClothesDecorator(GameCharacter chara) {
		super(chara, "Fancy");
	}

}
