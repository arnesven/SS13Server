package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.SeveredArm;
import model.items.general.*;
import model.items.mining.MiningExplosives;
import model.items.suits.CaptainsOutfit;
import model.items.suits.PirateOutfit;
import model.items.weapons.Flamer;
import model.items.weapons.LaserPistol;

public class CaptainCharacter extends CrewCharacter {

    public CaptainCharacter() {
        super("Captain", COMMAND_TYPE, 20, 16.0);
        getEquipment().removeEverything();
        new CaptainsOutfit(this).putYourselfOn(getEquipment());
    }

    @Override
    public List<GameItem> getCrewSpecificItems() {
        ArrayList<GameItem> list = new ArrayList<GameItem>();
        list.add(new UniversalKeyCard());
        list.add(new RemoteBomb());
        return list;
    }

    @Override
    public GameCharacter clone() {
        return new CaptainCharacter();
    }

    @Override
    public int getStartingMoney() {
        return 50;
    }


    @Override
    public String getJobDescription() {
        return new JobDescriptionMaker(this,
                "You're in charge. Keep your crew alive and the station in one piece." +
                        " Don't forget to guard the nuclear disk!", "Parent, Demotion").makeString();
    }

}