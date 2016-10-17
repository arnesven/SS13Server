package model.characters.visitors;

import model.characters.general.GameCharacter;
import model.items.suits.ClownSuit;

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
    public GameCharacter clone() {
        return new ClownCharacter();
    }
}
