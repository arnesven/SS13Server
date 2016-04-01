package model.characters.decorators;

import model.characters.GameCharacter;
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
		if (suit.getUnder() == null) {
			res += "Naked " + getGender() ;
		} else {
			res += this.getBaseName();
		}
		if (isDead()) {
			return res + " (dead)";
		}
		return res;
	}

}
