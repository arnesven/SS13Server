package model.map.builders;

import model.GameData;
import model.map.GameMap;
import model.map.rooms.*;
import model.objects.consoles.KeyCardLock;

/**
 * Created by erini02 on 15/12/16.
 */
public class ValleyForgeSS13Builder extends MapBuilder {








    @Override
    protected void buildPart(GameData gameData, GameMap gm) {
        gm.setSS13AreaNames(new String[]{"A-DECK", "C-DECK", "front", "aft", "center"});

        // B DECK
        gm.addRoom(new GeneratorRoom(26, 9,  1, 5, 2, new int[]{16}, new double[]{}, gameData), ss13, "aft");
        Room portHallFront = new Room(16, "Aft Hall", "",11,  3, 1, 3, new int[]{26, 23, 29, 21}, new double[]{11.5, 3, 11.0, 3.5, 12, 3.5}, RoomType.hall );
        gm.addRoom(portHallFront, ss13, "aft");
        gm.addRoom(new Room(23, "Main Hall", "" , 10,  5, 1, 5, new int[]{16, 15, 9, 11}, new double[]{11, 5.5, 11, 7, 11, 8.5, 11, 9.5}, RoomType.hall ), ss13, "center");

        gm.addRoom(new Room(11, "Front Hall", "", 11,  9, 1, 4, new int[]{17, 18, 9, 23} ,         new double[]{}, RoomType.hall ), ss13, "front");
        gm.addRoom(new BridgeRoom(17, 10, 13, 3, 2, new int[]{11, 20}, new double[]{10.5, 13, 11.5, 13} , RoomType.command ), ss13, "front");
        gm.addRoom(new CaptainsQuartersRoom(gameData, 20, 9,  11, 2, 2, new int[]{17}, new double[]{} , RoomType.command), ss13, "front");
        gm.addRoom(new SecurityRoom(gameData, 18, 12, 11, 2, 2, new int[]{11}, new double[]{12, 12} , RoomType.security ), ss13, "front");
        gm.addRoom(new AIRoom(gameData, 15, 11,  6, 2, 2, new int[]{23}        ,         new double[]{}, RoomType.tech ), ss13, "center");
        gm.addRoom(new Room( 9, "Stairs B"  , "B" , 11,  8, 1, 1, new int[]{23, 11, 6, 4}, new double[]{11.5, 9}, RoomType.hall ), ss13, "center");
        Room brig = new BrigRoom(29, 10, 3, 1, 1, new int[]{16}, new double[]{});
        gm.addRoom(brig, ss13, "center");
        {
            KeyCardLock l1 = new KeyCardLock(brig, portHallFront, true, 3.0);
            brig.addObject(l1);
            portHallFront.addObject(l1);
        }
        gm.addRoom(new AirLockRoom(21, 2, 12,  3, 1, 1, new int[]{16}, new double[]{} ), ss13, "aft");


        // A DECK
        gm.addRoom(new GreenhouseRoom(gameData, 3,  16,  6, 4, 3, new int[]{5, 4, 27}, new double[]{16, 7.5}), ss13, "A-DECK");
        gm.addRoom(new SickbayRoom(24, 16,  9, 2, 2, new int[]{5, 4, 7} , new double[]{16, 9.5}, RoomType.science ), ss13, "A-DECK");
        gm.addRoom(new AirLockRoom( 7,  1,  18, 9, 1, 1, new int[]{24} , new double[]{18, 9.5} ), ss13, "A-DECK");
        gm.addRoom(new Room( 4, "Stairs A", "A", 17,  8, 1, 1, new int[]{3, 24, 9}, new double[]{17.5, 8, 17.5, 9}, RoomType.hall), ss13, "center");
        gm.addRoom(new PanoramaRoom(27, 20,  5, 1, 3, new int[]{3, 1}, new double[]{20, 5.5, 20, 7.5}, RoomType.hall ), ss13, "A-DECK");
        gm.addRoom(new LabRoom(1, 16, 4, 4, 2, new int[]{27}, new double[]{}), ss13, "A-DECK");
        Room deck = new Room( 5, "Viewing Deck", "", 15,  7, 1, 3, new int[]{2, 3, 24}, new double[]{}, RoomType.hall);
        gm.addRoom(deck, ss13, "A-DECK");
        gm.addRoom(new ChapelRoom(2, 14, 5, 2, 2, new int[]{5}, new double[]{15.5, 7}, RoomType.support), ss13, "A-DECK");


        // C DECK
        Room gate = new Room(13, "Shuttle Gate", "Gate", 2,  6, 3, 3, new int[]{14, 25, 6}, new double[]{}, RoomType.hall );
        gm.addRoom(gate, ss13, "C-DECK");
        gm.addRoom(new KitchenRoom(8,  5, 4, 3, 2, new int[]{25, 12}, new double[]{}), ss13, "C-DECK");
        Room army = new ArmoryRoom(22, 3, 9, 3, 2, new int[]{22}, new double[]{4, 9});
        gm.addRoom(army, ss13, "C-DECK");
        {
            KeyCardLock l1 = new KeyCardLock(army, gate, true, 4.0);
            gate.addObject(l1);
            army.addObject(l1);
        }
        gm.addRoom(new BarRoom(gameData, 10, 6, 9, 2, 2, new int[]{6, 12}, new double[]{7.5, 9}, RoomType.support), ss13, "C-DECK");
        gm.addRoom(new DormsRoom(12, 7, 6, 2, 3, new int[]{8, 6, 10}, new double[]{7.5, 6} , RoomType.support), ss13, "C-DECK");
        gm.addRoom(new Room(14, "Office", "Offc"   , 3, 4, 2, 2, new int[]{13}, new double[]{4.5, 6}, RoomType.command ), ss13, "C-DECK");
        gm.addRoom(new Room(6, "Stairs C", "C", 5, 8, 2, 1, new int[]{13, 12, 10, 9}, new double[]{5, 8.5, 7, 8.5, 6.5, 9}, RoomType.hall), ss13, "center");
        gm.addRoom(new AirLockRoom(25, 3    , 5, 6, 1, 1, new int[]{8, 13}, new double[]{5.5, 6, 5, 6.5}  ), ss13, "C-DECK");


        gm.addRoom(new ElevatorRoom(gameData, 19, "Elevator", "E", new int[]{2,  8, 14, 8}, new Room[]{gate, deck}, new String[]{"C-DECK", "A-DECK"}, new double[]{}, RoomType.hall ), ss13, "front");

        // HIDDEN
        gm.addRoom(new NukieShipRoom(28, new int[]{7, 21, 25}, new double[]{}), ss13, "space");
        Room space = new Room(30, "Space", "", 2, 1, 2, 1, new int[]{}, new double[]{}, RoomType.space);
        gm.addRoom(space, ss13, "space");
        addEventsToSpaceRoom(space, gameData);
    }
}
