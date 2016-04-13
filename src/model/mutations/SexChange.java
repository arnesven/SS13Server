package model.mutations;

import model.Actor;
import model.characters.decorators.CharacterDecorator;

public class SexChange extends Mutation {

	public SexChange() {
		super("Sex Change");
	}

	@Override
	public CharacterDecorator getDecorator(Actor forWhom) {
		return new CharacterDecorator(forWhom.getCharacter(), "Sex Change") {
			@Override
			public String getGender() {
				if (getInner().getGender().equals("man")) {
					return "woman";
				}
				return "man";
			}
		};
	}

}
