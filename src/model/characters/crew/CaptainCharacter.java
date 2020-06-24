package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.characters.special.MartialArtist;
import model.items.general.*;
import model.items.suits.CaptainsOutfit;
import model.items.suits.Mankini;
import model.items.weapons.Revolver;

public class CaptainCharacter extends CrewCharacter implements MartialArtist {

    public CaptainCharacter() {
        super("Captain", COMMAND_TYPE, 20, 16.0);
        getEquipment().removeEverything();
        new CaptainsOutfit(this).putYourselfOn(getEquipment());
    }

    @Override
    public List<GameItem> getCrewSpecificItems() {
        ArrayList<GameItem> list = new ArrayList<GameItem>();
        list.add(new UniversalKeyCard());
        list.add(new Mankini());

        return list;
    }

    @Override
    public GameCharacter clone() {
        return new CaptainCharacter();
    }

    @Override
    public int getStartingMoney() {
        return 60;
    }


    @Override
    public String getJobDescription() {
        return new JobDescriptionMaker(this,
                "You're in charge. Keep your crew alive and the station in one piece." +
                        " Don't forget to guard the nuclear disk!", "Parent, Demotion").makeString();
    }

}