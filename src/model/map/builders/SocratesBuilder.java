package model.map.builders;

import model.GameData;
import model.map.GameMap;
import model.map.rooms.*;
import model.objects.consoles.KeyCardLock;

public class SocratesBuilder extends MapBuilder {
    @Override
    protected void buildPart(GameData gameData, GameMap gm) {
        LabRoom labRoom = new LabRoom(1, 7, 0, 4, 1, new int[]{24, 19}, new double[]{});
        gm.addRoom(labRoom, ss13, "port");

        Room sickbay = new SickbayRoom(24, 6,  1, 3, 1, new int[]{1, 16} ,         new double[]{7.5, 1.0, 7.5, 2.0});
        gm.addRoom(sickbay, ss13, "port");

        Room gate = new ShuttleGateRoom(19, 10, 1, 2, 3, new int[]{1, 16, 23}, new double[]{10.5, 1.0, 10.0, 2.5});
        gm.addRoom(gate, ss13, "port");
        gm.addRoom(gate, ss13, "front");

        Room frontHall = new HallwayRoom(23, "Port Hall Aft" ,"FRONT"  ,10,4,3, 2, new int[]{19, 21, 20, 13} , new double[]{11.5, 4.0});
        gm.addRoom(frontHall, ss13, "port");
        gm.addRoom(frontHall, ss13, "front");

        Room portHallFront = new HallwayRoom(16, "Port Hall", "PORT",4,  2, 6, 1, new int[]{19, 24, 25, 27},  new double[]{4.0, 2.5});
        gm.addRoom(portHallFront, ss13, "port");

        gm.addRoom(new AirLockRoom(25, 3    , 3,  2, 1, 1, new int[]{16, 27}, new double[]{} ), ss13, "port");

        Room panorama = new PanoramaRoom(27, 3,  3, 2, 1, new int[]{25, 16, 8},  new double[]{3.5, 3.0, 4.5, 3.0});
        gm.addRoom(panorama, ss13, "aft");
        gm.addRoom(panorama, ss13, "port");

        KitchenRoom kitch = new KitchenRoom(8,  2, 4, 2, 1, new int[]{27, 12, 2}, new double[]{3.5, 4.0, 3.5, 5.0} );
        gm.addRoom(kitch, ss13, "aft");
        Room dorms = new DormsRoom(12, 0, 5, 3, 2, new int[]{8, 5},  new double[]{2.5, 5.0});
        gm.addRoom(dorms, ss13, "aft");

        Room chapel = new ChapelRoom(2, 3, 5, 2, 1, new int[]{8, 5}, new double[]{3.5, 5.0});
        gm.addRoom(chapel, ss13, "aft");

        Room office = new OfficeRoom(gameData, 14, 1, 7, 2, 1, new int[]{5}, new double[]{} );
        gm.addRoom(office, ss13, "aft");

        gm.addRoom(new HallwayRoom( 5, "Aft Hall" , "AFT", 3,  6, 1, 3, new int[]{12, 2, 14, 4, 26}, new double[]{3.5, 6.0, 3.0, 6.5, 3.0, 7.5}), ss13, "aft");
        gm.addRoom(new HallwayRoom( 4, "Airtunnel"           , "", 3,  9, 2, 1, new int[]{5, 6},  new double[]{3.5, 9.0}), ss13, "starboard");

        Room green = new GreenhouseRoom(gameData, 3,  6,  10, 5, 2, new int[]{6, 7, 9}  ,  new double[]{6.0, 10.5}  );
        gm.addRoom(green, ss13, "starboard");

        Room aftWalk = new HallwayRoom(6, "Starboard Walkway", "", 4, 10, 2, 1, new int[]{3, 4}, new double[]{4.5, 10.0});
        gm.addRoom(aftWalk, ss13, "starboard");

        Room airLock1 = new AirLockRoom( 7,  1, 11, 11, 1, 1, new int[]{3, 9}, new double[]{11.0, 11.5, 11.5, 11.0} );
        gm.addRoom(airLock1, ss13, "front");
        gm.addRoom(airLock1, ss13, "starboard");


        Room sHallAft = new HallwayRoom( 9, "Starboard Hall"  , "S T A R B O A R D" , 11,  9, 2, 2, new int[]{7, 3, 10, 18}, new double[]{11.0, 10.5, 11.5, 9.0, 12.5, 9.0});
        gm.addRoom(sHallAft, ss13, "starboard");
        //gm.addRoom(new Room(11, "Starboard Hall (2)", "B O A R D"       , 13,  9, 2, 1, new int[]{} , new double[]{}, RoomType.hall ), ss13, "starboard");


        Room bar = new BarRoom(gameData, 10, 10, 7, 2, 2, new int[]{9, 18, 13}, new double[]{10.5, 7.0});
        gm.addRoom(bar, ss13, "center");

        Room mainHall = new HallwayRoom(13, "Main Hall", "MAIN",7,  6, 4, 1, new int[]{10, 23, 15, 17, 26},     new double[]{7.5, 6.0, 7.0, 6.5, 10.5, 6.0} );
        gm.addRoom(mainHall, ss13, "center");


        gm.addRoom(new GeneratorRoom(26, 4,  6, 3, 3, new int[]{5, 13}, new double[]{4.0, 7.5}, gameData ), ss13, "center");


        Room aiCore = new AIRoom(gameData, 15, 8,  7, 2, 1, new int[]{13} , new double[]{9.0, 7.0} );
        gm.addRoom(aiCore, ss13, "center");

        Room bridge = new BridgeRoom(17, 6,  4, 3, 2, new int[]{13}, new double[]{}  );
        gm.addRoom(bridge, ss13, "center");


        Room CQ = new CaptainsQuartersRoom(gameData, 20, 13,  5, 2, 1, new int[]{23}, new double[]{13.0, 5.5});
        gm.addRoom(CQ, ss13, "front");

        Room ss = new SecurityStationRoom(gameData, 18, 12,  7, 2, 2, new int[]{10, 9}, new double[]{12.0, 8.0}, frontHall);
        gm.addRoom(ss, ss13, "front");

        Room brig = new BrigRoom(29, 12, 6, 1, 1, new int[]{29}, new double[]{12.5, 6.0});
        gm.addRoom(brig, ss13, "center");
        {
            KeyCardLock l1 = new KeyCardLock(brig, frontHall, true, 3.0);
            brig.addObject(l1);
            frontHall.addObject(l1);
        }


        Room airLock2 = new AirLockRoom(21, 2   ,13, 4, 1, 1, new int[]{23} , new double[]{13.0, 4.5} );
        gm.addRoom(airLock2, ss13, "front");

        Room army = new ArmoryRoom(22,      14,  6, 2, 2, new int[]{22}, new double[]{14.0, 7.5});
        {
            KeyCardLock l1 = new KeyCardLock(army, ss, true, 4.0);
            gate.addObject(l1);
            army.addObject(l1);
        }
        gm.addRoom(army, ss13, "front");



        Room nukieShip = new NukieShipRoom(28, new int[]{7, 21, 25}, new double[]{-1.0, -1.0});
        gm.addRoom(nukieShip, ss13, "nuke");

        Room space = new SpaceRoom(30, 0, 0, 1, 1);

        addEventsToSpaceRoom(space, gameData);

        gm.addRoom(space, ss13, "space");
    }
}
