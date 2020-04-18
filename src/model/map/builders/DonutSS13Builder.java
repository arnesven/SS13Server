package model.map.builders;

import model.GameData;
import model.map.GameMap;
import model.map.doors.*;
import model.map.rooms.*;
import model.objects.BarSign;
import model.objects.consoles.KeyCardLock;
import model.objects.decorations.PosterObject;

/**
 * Created by erini02 on 15/12/16.
 */
public class DonutSS13Builder extends MapBuilder {
    @Override
    protected void buildPart(GameData gameData, GameMap gm) {
        LabRoom labRoom = new LabRoom(1, 2, 1, 4, 3, new int[]{27, 24, 5},
                new Door[]{new NormalDoor(5.0, 4.0, 1, 5),
                            new NormalDoor(2.0, 3.5, 1, 27),
                            new NormalDoor(6.0, 1.5, 1, 24)});
        gm.addRoom(labRoom, ss13, "aft");
        gm.addRoom(labRoom, ss13, "port");

        Room chapel = new ChapelRoom(2, 2, 4, 2, 2, new int[]{5},
                new Door[]{new NormalDoor(4.0, 5.0, 2, 5)});
        gm.addRoom(chapel, ss13, "aft");

        Room green = new GreenhouseRoom(gameData, 3,  0,  6, 3, 4, new int[]{4, 6, 27}  ,
                new Door[]{new NormalDoor(1.5, 6.0, 3, 27)}  );
        gm.addRoom(green, ss13, "aft");

        gm.addRoom(new HallwayRoom( 4, "Airtunnel"           , ""       , 3,  7, 1, 1, new int[]{3, 5}      ,
                new Door[]{new NormalDoor(3.0, 7.5, 4, 3),
                        new NormalDoor(4.0, 7.5, 4, 5)}),
                ss13, "aft");
        gm.addRoom(new HallwayRoom( 5, "Aft Hall"            , "AFT"    , 4,  4, 2, 4, new int[]{1, 2, 4, 9, 26, 23},
                new Door[]{new NormalDoor(6.0, 4.5, 5, 23),
                        new NormalDoor(6.0, 6.5, 5, 26),
                        new NormalDoor(5.5, 8.0, 5, 9)}),
                ss13, "aft");

        Room aftWalk = new HallwayRoom(6, "Aft Walkway", "", 2, 10, 2, 1, new int[]{3, 7, 8},
                new Door[]{new NormalDoor(2.5, 10.0, 6, 3),
                        new NormalDoor(4.0, 10.5, 6, 8)});
        gm.addRoom(aftWalk, ss13, "aft");
        gm.addRoom(aftWalk, ss13, "starboard");

        Room airLock1 = new AirLockRoom( 7,  1, 2, 11, 1, 1, new int[]{6}         ,
                new Door[]{new NormalDoor(2.5, 11.0, 7, 6)} );
        gm.addRoom(airLock1, ss13, "aft");
        gm.addRoom(airLock1, ss13, "starboard");

        KitchenRoom kitch = new KitchenRoom(gameData, 8,  4, 10, 2, 3, new int[]{6, 9, 10}  ,
                new Door[]{new NormalDoor(6.0, 11.5, 8, 10),
                        new NormalDoor(5.5, 10.0, 8, 9)} );
        gm.addRoom(kitch, ss13, "starboard");

        Room sHallAft = new HallwayRoom( 9, "Starboard Hall Aft"  , "S T A R -" , 5,  8, 4, 2, new int[]{5, 8, 10, 11},
                new Door[]{new NormalDoor(9.0, 9.5, 9, 11)} );
        BarSign barSign = new BarSign(sHallAft);
        barSign.setAbsolutePosition(6.5, 10);
        sHallAft.addObject(barSign);
        gm.addRoom(sHallAft, ss13, "starboard");

        Room bar = new BarRoom(gameData, 10, 6, 10, 3, 2, new int[]{8, 9, 12}  ,
                new Door[]{new NormalDoor(7.5, 10.0, 10, 9),
                        new NormalDoor(9.0, 11.5, 10, 12)});
        BarSign barSign2 = new BarSign(bar);
        barSign2.setAbsolutePosition(8, 10);
        bar.addObject(barSign2);
        gm.addRoom(bar, ss13, "starboard");

        Room shallFront = new HallwayRoom(11, "Starboard Hall Front", "B O A R D"       , 9,  9, 3, 2, new int[]{9, 12, 13, 446} ,
                new Door[]{new NormalDoor(12.0, 9.5, 11, 13)} );
        shallFront.addObject(new DowngoingStairsDoor(shallFront));
        gm.addRoom(shallFront, ss13, "starboard");

        Room underpass = new UnderpassRoom(445, 10, 4, 1, 6, new int[]{446, 450}, new Door[]{});
        gm.addRoom(underpass, ss13, "center");

        Room janitorial = new JanitorialRoom(450, 10, 2, 2, 2, new int[]{19, 445},
                new Door[]{new NormalDoor(10.5, 4.0, 450, 445)});
        gm.addRoom(janitorial, ss13, "starboard");

        Room cargoBay = new CargoBayRoom(446, 8, 10, 4, 3, new int[]{445, 11},
                new Door[]{new NormalDoor(10.5, 10.0, 446, 445)});
        gm.addRoom(cargoBay, ss13, "starboard");

        Room loungeRoom = new LoungeRoom(447, 9, 2, 2, 2, new int[]{19}, new Door[]{});
        gm.addRoom(loungeRoom, ss13, "port");

        Room dorms = new DormsRoom(12, 9, 11, 4, 3, new int[]{10, 11, 14},
                new Door[]{new NormalDoor(10.5, 11.0, 12, 10),
                        new NormalDoor(13.0, 11.5, 12, 14)} );
        gm.addRoom(dorms, ss13, "starboard");

        Room office = new OfficeRoom(gameData, 14, 13, 10, 2, 2, new int[]{12, 13}    ,         new Door[]{} );
        gm.addRoom(office, ss13, "front");
        gm.addRoom(office, ss13, "starboard");

        Room frontHall = new HallwayRoom(13, "Front Hall"          , "FRONT"     ,12,  6, 2, 4, new int[]{11, 14, 16, 448},
                new Door[]{new NormalDoor(13.5, 10.0, 13, 14),
                        new NormalDoor(12.0, 8.0, 13, 448),
                        new NormalDoor(13.5, 6.0, 13, 16)});
        gm.addRoom(frontHall, ss13, "front");


        Room aiCore = new AIRoom(gameData, 15, 10,  7, 2, 2, new int[]{448}        ,         new Door[]{} );
        gm.addRoom(aiCore, ss13, "center");

        Room robotics = new RoboticsRoom(448, 10, 7, 2, 2, new int[]{13, 15}, new Door[]{});
        gm.addRoom(robotics, ss13, "center");

        Room portHallFront = new HallwayRoom(16, "Port Hall Front"     , ""       ,13,  3, 2, 3, new int[]{13, 17, 18, 19},
                new Door[]{new NormalDoor(15.0, 5.5, 16, 17),
                        new NormalDoor(15.0, 3.5, 16, 18)} );
        gm.addRoom(portHallFront, ss13, "front");
        gm.addRoom(portHallFront, ss13, "port");

        Room bridge = new BridgeRoom(17, 15,  5, 3, 3, new int[]{16, 20}    ,
                new Door[]{new NormalDoor(16.0, 8.0, 17, 20)} );
        gm.addRoom(bridge, ss13, "front");

        Room ss = new SecurityStationRoom(gameData, 18, 15,  2, 2, 2, new int[]{16}        ,
                new Door[]{}, portHallFront );
        gm.addRoom(ss, ss13, "front");
        gm.addRoom(ss, ss13, "port");

        Room gate = new HallwayRoom(19, "Shuttle Gate"        , "Gate"   ,10,  2, 3, 2, new int[]{16, 21, 23, 447, 450},
                new Door[]{new NormalDoor(10.0, 3.5, 19, 23),
                        new NormalDoor(13.0, 3.5, 19, 16)});
        gate.addObject(new UpgoingStairsDoor(gate));
        gate.addObject(new DowngoingStairsDoor(gate));
        gm.addRoom(gate, ss13, "port");


        Room CQ = new CaptainsQuartersRoom(gameData, 20, 15,  8, 2, 2, new int[]{17}        ,
                new Door[]{} );
        gm.addRoom(CQ, ss13, "front");

        Room airLock2 = new AirLockRoom(21, 2   ,13,  2, 1, 1, new int[]{19}        ,
                new Door[]{new NormalDoor(13.0, 2.5, 21, 19)} );
        gm.addRoom(airLock2, ss13, "port");
        gm.addRoom(airLock2, ss13, "front");
        Room army = new ArmoryRoom(22,                             10,  4, 3, 2, new int[]{}        ,         new Door[]{});
        army.setDoors(new Door[]{new LockedDoor(11.0, 4.0, 22, 19)});
        {
            KeyCardLock l1 = new KeyCardLock(army, gate, true, 4.0);
            gate.addObject(l1);
            army.addObject(l1);
        }
        gm.addRoom(army, ss13, "center");

        gm.addRoom(new HallwayRoom(23, "Port Hall Aft"       , "P O R T"       , 6,  3, 4, 2, new int[]{19, 24, 5} ,
                new Door[]{new NormalDoor(7.5, 3.0, 23, 24)} ), ss13, "port");

        Room sickbay = new SickbayRoom(24, 6,  0, 3, 3, new int[]{23, 25, 1} ,         new Door[]{});
        gm.addRoom(sickbay, ss13, "port");
        gm.addRoom(new AirLockRoom(25, 3    , 5,  0, 1, 1, new int[]{24}        ,
                new Door[]{new NormalDoor(6.0, 0.5, 25, 24)}  ), ss13, "port");

        gm.addRoom(new GeneratorRoom(26, 6,  5, 3, 3, new int[]{5}         ,         new Door[]{}, gameData ), ss13, "center");

        Room panorama = new PanoramaRoom(27, 1,  3, 1, 3, new int[]{1, 3}      ,         new Door[]{} );
        gm.addRoom(panorama, ss13, "aft");

        Room nukieShip = new NukieShipRoom(28, 16, 13, 2, 1, new int[]{7, 21, 25, 128},
                new Door[]{new NormalDoor(18.0, 13.5, 28, 128)});
        gm.addRoom(nukieShip, ss13, "nuke");
        Room nukieShip2 = new NukieShipStorageRoom(128, 18, 12, 1, 2, new int[]{28, 7, 21, 25}, new Door[]{});
        gm.addRoom(nukieShip2, ss13, "nuke");

        Room brig = new BrigRoom(29, 15, 4, 1, 1, new int[]{}, new Door[]{});
        brig.setDoors(new Door[]{new LockedDoor(15.0, 4.5, 29, 16)});
        gm.addRoom(brig, ss13, "center");
        {
            KeyCardLock l1 = new KeyCardLock(brig, portHallFront, true, 3.0);
            brig.addObject(l1);
            portHallFront.addObject(l1);
        }

        Room space = new SpaceRoom(30, 0, 0, 1, 1);

        addEventsToSpaceRoom(space, gameData);
        gm.addRoom(space, ss13, "space");

        Room ventilationShaft = new AirDuctRoom(499, 3, 2, 1, 5, new int[]{500, 501, 502, 503}, new Door[]{});
        gm.addRoom(ventilationShaft, ss13, "aft");
        Room vent2 = new AirDuctRoom(500, 2, 7, 13, 1, new int[]{499, 501, 502, 503}, new Door[]{});
        gm.addRoom(vent2, ss13, "center");
        Room vent3 = new AirDuctRoom(501, 7, 8, 1, 4, new int[]{499, 500, 502, 503}, new Door[]{});
        gm.addRoom(vent3, ss13, "starboard");
        Room vent4 = new AirDuctRoom(502, 15, 3, 1, 7, new int[]{499, 500, 501, 503}, new Door[]{});
        gm.addRoom(vent4, ss13, "front");
        Room vent5 = new AirDuctRoom(503, 9, 4, 1, 3, new int[]{499, 500, 501, 502}, new Door[]{});
        gm.addRoom(vent5, ss13, "port");

    }
}
