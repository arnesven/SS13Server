package model.characters.general;


import model.characters.special.MartialArtist;
import model.items.general.GameItem;
import model.items.general.PirateNuclearDisc;

import java.util.List;

/**
 * Created by erini02 on 11/11/16.
 */
public class PirateCaptainCharacter extends PirateCharacter implements MartialArtist {

    public PirateCaptainCharacter(int startRoom) {
        super(0, startRoom);
    }

    @Override
    public List<GameItem> getStartingItems() {
        List<GameItem> list = super.getStartingItems();
        list.add(new PirateNuclearDisc());
        return list;
    }

    @Override
    public String getFullName() {
        return "Pirate Captain";
    }

    @Override
    public String getPublicName() {
        return "Pirate Captain";
    }

}
