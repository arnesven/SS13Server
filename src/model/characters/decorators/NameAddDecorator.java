package model.characters.decorators;

import model.characters.general.GameCharacter;

public class NameAddDecorator extends CharacterDecorator {
	
	private String thisname;

	public NameAddDecorator(GameCharacter chara, String name) {
		super(chara, name);
		this.thisname = name;
	}

	@Override
	public String getPublicName() {
        String res = thisname + " " + getInner().getGender();

		if (isDead()) {
			res += " (dead)";
		}
		return res;
	}

}
