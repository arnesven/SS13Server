package model.characters.visitors;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.suits.ClownSuit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/10/16.
 */
public class ClownCharacter extends VisitorCharacter {

    public ClownCharacter() {
        super("Clown", 0, 1.0);
        removeSuit();
        putOnSuit(new ClownSuit(this));
    }

    @Override
    public List<GameItem> getCrewSpecificItems() {
        return new ArrayList<>();
    }

    @Override
    public GameCharacter clone() {
        return new ClownCharacter();
    }
}
