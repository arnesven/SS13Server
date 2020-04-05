package model.characters.visitors;

import model.GameData;
import model.characters.crew.CrewCharacter;
import model.characters.crew.TouristCharacter;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.map.rooms.ArmoryRoom;
import model.map.rooms.NukieShipRoom;
import model.map.rooms.Room;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/10/16.
 */
public abstract class VisitorCharacter extends CrewCharacter {

    public VisitorCharacter(String s, int i, double v) {
        super(s, i, v);
    }

    public VisitorCharacter(String str) {
        this(str, 0, 1.0);
    }


    @Override
    public List<GameItem> getCrewSpecificItems() {
        return new ArrayList<>();
    }


    @Override
    public Room getStartingRoom(GameData gameData) {
        Room room;
        do {
            room = MyRandom.sample(gameData.getRooms());
        } while (room instanceof NukieShipRoom || room instanceof ArmoryRoom);

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

    @Override
    public boolean isCrew() {
        return true;
    }
}
