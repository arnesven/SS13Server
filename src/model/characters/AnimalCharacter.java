package model.characters;

import java.util.ArrayList;
import java.util.List;

import model.items.GameItem;
import model.npcs.Trainable;

public abstract class AnimalCharacter extends GameCharacter implements Trainable {

	public AnimalCharacter(String name, int startRoom, double speed) {
		super(name, startRoom, speed);
		this.removeSuit();
	}
	
	@Override
	public String getPublicName() {
		if (isDead()) {
			return getBaseName() + " (dead)";
		}
		return getBaseName();
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<>();
	}
	
	@Override
	public boolean isCrew() {
		return false;
	}

}
