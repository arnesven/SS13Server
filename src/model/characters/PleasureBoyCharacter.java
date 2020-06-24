package model.characters;

import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.general.GameItem;
import model.items.suits.Mankini;

import java.util.ArrayList;
import java.util.List;

public class PleasureBoyCharacter extends HumanCharacter {
    public PleasureBoyCharacter(int startRoom) {
        super("Pleasure Boy", startRoom, 0.25);
        getPhysicalBody().setGender(true);
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public GameCharacter clone() {
        return new PleasureBoyCharacter(getStartingRoom());
    }
}
