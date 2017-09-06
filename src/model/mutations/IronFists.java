package model.mutations;

import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.items.weapons.IronFistsWeapon;
import model.items.weapons.Weapon;
import model.items.weapons.BluntWeapon;

public class IronFists extends Mutation {

	public IronFists() {
		super("Iron Fists");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new CharacterDecorator(forWhom.getCharacter(), "Iron Fists") {
			
			private Weapon fists = new IronFistsWeapon();
			@Override
			public Weapon getDefaultWeapon() {
				return fists;
			}
		};
	}

}
