package model.characters.decorators;

import model.characters.GameCharacter;

public class FancyClothesDecorator extends NameAddDecorator {

	public FancyClothesDecorator(GameCharacter chara) {
		super(chara, "Fancy");
	}
	
	

}
