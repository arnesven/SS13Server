package model.characters.visitors;

import model.GameData;
import model.characters.crew.CrewCharacter;
import model.characters.crew.JobDescriptionMaker;
import model.characters.crew.TouristCharacter;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.MoneyStack;
import model.items.keycard.VisitorsBadge;
import model.map.rooms.ArmoryRoom;
import model.map.rooms.BrigRoom;
import model.map.rooms.NukieShipRoom;
import model.map.rooms.Room;
import util.MyRandom;
import util.MyStrings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/10/16.
 */
public abstract class VisitorCharacter extends CrewCharacter {

    public VisitorCharacter(String s, int i, double v) {
        super(s, CIVILIAN_TYPE, i, v);
    }

    public VisitorCharacter(String str) {
        this(str, 0, 1.0);
    }


    @Override
    public List<GameItem> getCrewSpecificItems() {
        return new ArrayList<>();
    }

    @Override
    public List<GameItem> getStartingItems() {
        List<GameItem> items = new ArrayList<>();
        items.add(new VisitorsBadge());
        items.add(new MoneyStack(getStartingMoney()));
        items.addAll(getCrewSpecificItems());
        return items;
    }

    @Override
    public Room getStartingRoom(GameData gameData) {
        Room room;
        do {
            room = MyRandom.sample(gameData.getStationSpawnRooms());
        } while (room instanceof NukieShipRoom || room instanceof ArmoryRoom || room instanceof BrigRoom);

        return room;
    }

    public static List<GameCharacter> getSubtypes() {
        List<GameCharacter> chars = new ArrayList<>();
        chars.add(new TouristCharacter());
        chars.add(new CaptainsDaughter());
        chars.add(new ClownCharacter());
        chars.add(new GeishaCharacter());
        chars.add(new LawyerCharacter());
        chars.add(new AdventurerCharacter());
        chars.add(new BirdManCharacter());
        chars.add(new VendingMachineCharacter());
        return chars;
    }

    private static List<String> getSubtypeNames() {
        List<String> lst = new ArrayList<>();
        for (GameCharacter gc : getSubtypes()) {
            lst.add(gc.getBaseName());
        }
        return lst;
    }

    @Override
    public boolean isCrew() {
        return true;
    }

    @Override
    public String getJobDescription() {
        return new JobDescriptionMaker(this, "You are one of the following visitors on the station: " +
                MyStrings.join(getSubtypeNames(), ", "), "Varies").makeString();
    }

    @Override
    public String getMugshotName() {
        return "Visitor";
    }
}
