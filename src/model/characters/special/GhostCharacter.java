package model.characters.special;

import model.Actor;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 20/12/16.
 */
public abstract class GhostCharacter extends GameCharacter {

    public GhostCharacter(String name, int startRoom, double speed) {
        super(name, startRoom, speed);
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    public boolean isInteractable() {
        return false;
    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon weapon) {
        return false;
    }

    public boolean isHealable() {
        return false;
    }

    public boolean hasInventory() {
        return false;
    }

    public boolean canUseObjects() {
        return false;
    }

    public boolean isVisible() {
        return false;
    }

    @Override
    public boolean isCrew() {
        return false;
    }



}
