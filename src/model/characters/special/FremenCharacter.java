package model.characters.special;

import graphics.sprites.Sprite;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class FremenCharacter extends HumanCharacter {
    public FremenCharacter(int startRoom) {
        super("Fremen", startRoom, 27.0);
    }

    @Override
    public List<GameItem> getStartingItems() {
        List<GameItem> its = new ArrayList<>();
        // TODO: add Cryssknife, thumper
        return its;
    }

    @Override
    public GameCharacter clone() {
        return new FremenCharacter(getStartingRoom());
    }

    @Override
    public boolean isCrew() {
        return false;
    }
}
