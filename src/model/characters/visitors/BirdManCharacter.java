package model.characters.visitors;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.suits.BirdManSuit;
import model.items.suits.ClownSuit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 08/09/17.
 */
public class BirdManCharacter extends VisitorCharacter {
    public BirdManCharacter() {
        super("Bird Man", 1, 1.0);
        removeSuit();
        putOnSuit(new BirdManSuit(this));
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
