package model.characters.visitors;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.suits.CaptainsDaughtersOutfit;
import model.items.suits.CaptainsOutfit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/10/16.
 */
public class CaptainsDaughter extends VisitorCharacter {
    public CaptainsDaughter() {
        super("Captain's Daughter", 0, 1.0);
        // putOnSuit(new CaptainsDaughtersOutfit(this));
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public GameCharacter clone() {
        return new CaptainsDaughter();
    }
}
