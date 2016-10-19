package model.characters.visitors;

import model.GameData;
import model.characters.crew.TouristCharacter;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.map.NukieShipRoom;
import model.map.Room;
import util.MyRandom;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/10/16.
 */
public abstract class VisitorCharacter extends GameCharacter {

    public VisitorCharacter(String s, int i, double v) {
        super(s, i, v);
    }

    public VisitorCharacter(String str) {
        this(str, 0, 1.0);
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }


    @Override
    public Room getStartingRoom(GameData gameData) {
        Room room;
        do {
            room = MyRandom.sample(gameData.getRooms());
        } while (room instanceof NukieShipRoom);

        return room;
    }

    public List<GameCharacter> getSubtypes() {
        List<GameCharacter> chars = new ArrayList<>();
        chars.add(new TouristCharacter());
        chars.add(new CaptainsDaughter());
        chars.add(new ClownCharacter());
        chars.add(new GeishaCharacter());
        chars.add(new LawyerCharacter());
        chars.add(new AdventurerCharacter());
        return chars;
    }
}
