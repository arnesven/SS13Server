package model.characters;

import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.general.GameItem;
import model.items.suits.Bikini;
import model.items.suits.Mankini;

import java.util.ArrayList;
import java.util.List;

public class PleasureGirlCharacter extends PleasureCharacter {
    public PleasureGirlCharacter(Integer id) {
        super("Pleasure Girl", id, 0.25);
        getPhysicalBody().setGender(false);
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
