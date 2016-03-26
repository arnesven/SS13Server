package model.characters.decorators;

import model.characters.GameCharacter;

public class FancyClothesDecorator extends CharacterDecorator {

	public FancyClothesDecorator(GameCharacter chara) {
		super(chara, "Fancy");
	}
	
	@Override
	public String getPublicName() {
		String res = "Fancy " + getInner().getGender();
		if (isDead()) {
			res += " (dead)";
		}
		return res;
	}

}
