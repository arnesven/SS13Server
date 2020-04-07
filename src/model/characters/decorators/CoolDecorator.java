package model.characters.decorators;

import model.characters.general.GameCharacter;
import model.items.suits.SuitItem;

public class CoolDecorator extends CharacterDecorator {

	private SuitItem suit;

	public CoolDecorator(GameCharacter chara, SuitItem sunGlasses) {
		super(chara, "Cool");
		this.suit = sunGlasses;
	}
	
	@Override
	public String getPublicName() {
		String res = "Cool ";
		res += getInner().getPublicName();
		return res;
	}

}
