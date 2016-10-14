package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import model.items.general.GameItem;
import model.npcs.animals.Trainable;

public abstract class AnimalCharacter extends GameCharacter implements Trainable {

	public AnimalCharacter(String name, int startRoom, double speed) {
		super(name, startRoom, speed);
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
	
	@Override
	public boolean hasInventory() {
		return false;
	}

    @Override
    public String getGender() {
        return getBaseName();
    }
}
