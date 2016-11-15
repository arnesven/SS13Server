package model.characters.visitors;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.suits.AdventurersHat;
import model.items.suits.AdventurersOutfit;
import model.items.weapons.BullWhip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 18/10/16.
 */
public class AdventurerCharacter extends VisitorCharacter {
    public AdventurerCharacter() {
        super("Adventurer");
        removeSuit();
        putOnSuit(new AdventurersHat());
        putOnSuit(new AdventurersOutfit(this));
    }

    @Override
    public List<GameItem> getCrewSpecificItems() {
        List<GameItem> gis = new ArrayList<>();
        gis.add(new BullWhip());
        return gis;
    }

    @Override
    public GameCharacter clone() {
        return new AdventurerCharacter();
    }
}
