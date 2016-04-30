package model.mutations;

import model.Actor;
import model.characters.decorators.CharacterDecorator;

import java.io.Serializable;

public abstract class Mutation implements Serializable {
	
	private String name;

	public Mutation (String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public abstract CharacterDecorator getDecorator(Actor forWhom);

}
