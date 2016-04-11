package model.mutations;

import model.Actor;
import model.characters.decorators.CharacterDecorator;

public abstract class Mutation {
	
	private String name;

	public Mutation (String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public abstract CharacterDecorator getDecorator(Actor forWhom);

}
