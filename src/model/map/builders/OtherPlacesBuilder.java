package model.map.builders;

import model.GameData;
import model.map.GameMap;
import model.map.rooms.OtherDimension;
import model.map.rooms.Room;
import model.map.rooms.RoomType;
import model.map.rooms.SpectatorRoom;

/**
 * Created by erini02 on 15/12/16.
 */
public class OtherPlacesBuilder extends MapBuilder {
    @Override
    protected void buildPart(GameData gameData, GameMap gm) {
        Room dummy = new Room(31, "Dummy", "", 18, 1, 0, 0,
                new int[]{28}, new double[]{-1.0, -1.0}, RoomType.hidden);
        gm.addRoom(dummy, ss13, "dummy");
        Room otherDim = new OtherDimension(32, new int[]{30}, new double[]{-1.0, -1.0});
        Room prisonPlanet = new Room(33, "Prison Planet", "P R I S O N P L A N E T", 0, 0, 1, 1, new int[]{30}, new double[]{-1.0, -1.0}, RoomType.outer);

        gm.addRoom(otherDim, "other dimension", "other dimension");
        gm.addRoom(prisonPlanet, "prison planet", "prison planet");

        Room deepspace = new Room(41, "Deep Space", "D E E P   S P A C E", 6, 8, 3, 3, new int[]{}, new double[]{}, RoomType.space);
        addEventsToSpaceRoom(deepspace, gameData);
        gm.addRoom(deepspace, "deep space", "deep space");


        Room spectatorBench = new SpectatorRoom(gameData);
        gm.addRoom(spectatorBench, "ss13", "hidden");

    }
}
