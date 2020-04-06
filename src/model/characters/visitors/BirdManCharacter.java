package model.characters.visitors;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.suits.BirdManSuit;
import model.items.suits.ClownSuit;
import model.items.suits.SuitItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 08/09/17.
 */
public class BirdManCharacter extends VisitorCharacter {
    public BirdManCharacter() {
        super("Bird Man", 1, 1.0);
        getEquipment().removeEverything();
        SuitItem outfit = new BirdManSuit(this);
        outfit.putYourselfOn(getEquipment());

    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public GameCharacter clone() {
        return new BirdManCharacter();
    }

}
