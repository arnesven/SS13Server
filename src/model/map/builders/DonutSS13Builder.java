package model.map.builders;

import model.GameData;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.doors.*;
import model.map.rooms.*;
import model.objects.BarSign;
import model.objects.consoles.SolarArrayControl;
import model.objects.general.*;
import model.objects.power.AreaPowerControl;

import java.awt.*;

/**
 * Created by erini02 on 15/12/16.
 */
public class DonutSS13Builder extends MapBuilder {
    @Override
    protected void buildPart(GameData gameData, GameMap gm) {
        LabRoom labRoom = new LabRoom(1, 2, 1, 4, 3, new int[]{27, 24},
                new Door[]{new ScienceDoor(5.0, 4.0, 1, 5, true),
                            new NormalDoor(2.0, 3.5, 0.0, 1, 27, false),
                            new ScienceDoor(6.0, 1.5, 1, 24, false)}, gameData);
        gm.addRoom(labRoom, ss13, "aft");
        gm.addRoom(labRoom, ss13, "port");

        Room chapel = new ChapelRoom(2, 2, 4, 2, 2, new int[]{5},
                new Door[]{new NormalDoor(4.0, 5.0, 2, 5)});
        gm.addRoom(chapel, ss13, "aft");

        Room green = new GreenhouseRoom(gameData, 3,  0,  6, 3, 4, new int[]{4, 6}  ,
                new Door[]{new ScienceDoor(1.5, 6.0, 3, 27, true)}  );
        gm.addRoom(green, ss13, "aft");

        AirtunnelRoom airtunnelRoom = new AirtunnelRoom( 4, "Airtunnel"           , ""       , 3,  7, 1, 1, new int[]{3, 5}      ,
                new Door[]{new NormalDoor(3.0, 7.5, 4, 3),
                        new NormalDoor(4.0, 7.5, 4, 5)});
        gm.addRoom(airtunnelRoom, ss13, "aft");

        HallwayRoom aftHall = new HallwayRoom( 5, "Aft Hall"            , "AFT"    , 4,  4, 2, 4, new int[]{2, 4, 9, 23},
                new Door[]{new NormalDoor(6.0, 4.5, 5, 23),
                        new NormalDoor(5.5, 8.0, 5, 9)});
        {
            AreaPowerControl apc = new AreaPowerControl(aftHall, chapel);
            apc.setAbsolutePosition(4, 5.5);
        }
        gm.addRoom(aftHall, ss13, "aft");

        Room aftWalk = new HallwayRoom(6, "Aft Walkway", "", 2, 10, 2, 1, new int[]{3, 7, 8},
                new Door[]{new NormalDoor(2.5, 10.0, 6, 3),
                        new NormalDoor(4.0, 10.5, 6, 8)});
        gm.addRoom(aftWalk, ss13, "aft");
        gm.addRoom(aftWalk, ss13, "starboard");

        AirLockRoom airLock1 = new AirLockRoom( 7,  1, 2, 11, 1, 1, new int[]{6}         ,
                new Door[]{new FullyOpenAirLockDoor(2.5, 11.0, 0.0, 7, 6),
                new AirLockDoor(2.0, 11.5, 7, 30 ),
                new AirLockDoor(2.5, 12.0, 7, 30)} );
        airLock1.addDockingPoint(new DockingPoint("Airlock 1 - Starboard", "Auxiliary", new Point(0, -1), new Point(0, 1), airLock1));
        airLock1.addDockingPoint(new DockingPoint("Airlock 1 - Aft", "Auxiliary", new Point(0, -1), new Point(-1, 0), airLock1));
        gm.addRoom(airLock1, ss13, "aft");
        gm.addRoom(airLock1, ss13, "starboard");

        {
            AirlockPanel ap = new AirlockPanel(airLock1, aftWalk);
            ap.setAbsolutePosition(3.0, 11.0);
            aftWalk.addObject(ap);
        }

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
        {
            GameObject apc = new AreaPowerControl(bar, kitch);
            apc.setAbsolutePosition(6, 11);
        }
        gm.addRoom(bar, ss13, "starboard");

        Room shallFront = new HallwayRoom(11, "Starboard Hall Front", "B O A R D"       , 9,  9, 3, 2, new int[]{9, 12, 13, 446} ,
                new Door[]{new NormalDoor(12.0, 9.5, 11, 13)} );
        shallFront.addObject(new DowngoingStairsDoor(shallFront));
        gm.addRoom(shallFront, ss13, "starboard");

        Room underpass = new UnderpassRoom(445, 10, 4, 1, 6, new int[]{446, 450}, new Door[]{});
        gm.addRoom(underpass, ss13, "center");

        Room janitorial = new JanitorialRoom(450, 10, 2, 2, 2, new int[]{19, 445},
                new Door[]{new NormalDoor(10.5, 4.0, -1.0, 450, 445)});
        gm.addRoom(janitorial, ss13, "starboard");

        Room cargoBay = new CargoBayRoom(446, 8, 10, 4, 3, new int[]{445, 11, 587},
                new Door[]{new NormalDoor(10.5, 10.0, -1.0, 446, 445)});
        gm.addRoom(cargoBay, ss13, "starboard");

        AirLockRoom airLock5 = new AirLockRoom(587, 5, 12, 12, 1, 1, new int[]{446},
                new Door[]{new FullyOpenAirLockDoor(12.0, 12.5, -1, 587, 446),
                new AirLockDoor(12.5, 13.0, -1, 587, 30, false),
                new AirLockDoor(13.0, 12.5, -1, 587, 30, false)});
        airLock5.setZ(-1);
        airLock5.addDockingPoint(new DockingPoint("Airlock 5 - Front", "Cargo",
                new Point(-1, 0), new Point(1, 0), airLock5));
        airLock5.addDockingPoint(new DockingPoint("Airlock 5 - Starboard", "Cargo",
                new Point(-1, 0), new Point(0, 1), airLock5));
        gm.addRoom(airLock5, ss13, "starboard");


        Room loungeRoom = new LoungeRoom(447, 9, 2, 2, 2, new int[]{19}, new Door[]{});
        gm.addRoom(loungeRoom, ss13, "port");

        Room dorms = new DormsRoom(12, 9, 11, 4, 3, new int[]{10, 11, 14},
                new Door[]{new NormalDoor(10.5, 11.0, 12, 11),
                        new NormalDoor(13.0, 11.5, 12, 14)} );
        gm.addRoom(dorms, ss13, "starboard");

        Room office = new OfficeRoom(gameData, 14, 13, 10, 2, 2, new int[]{12, 13}    ,         new Door[]{} );
        gm.addRoom(office, ss13, "front");
        gm.addRoom(office, ss13, "starboard");
        {
            AreaPowerControl apc = new AreaPowerControl(office, dorms);
            apc.setAbsolutePosition(13, 12);
        }

        Room frontHall = new HallwayRoom(13, "Front Hall"          , "FRONT"     ,12,  6, 2, 4, new int[]{11, 14, 16},
                new Door[]{new NormalDoor(13.5, 10.0, 13, 14),
                        new NormalDoor(13.5, 6.0, 13, 16)});
        gm.addRoom(frontHall, ss13, "front");


        Room aiCore = new AIRoom(gameData, 15, 10,  7, 2, 2, new int[]{448, 539} , new Door[]{} );
        aiCore.addObject(new SolarArrayControl(aiCore, 14324, gameData));

        gm.addRoom(aiCore, ss13, "center");

        AirLockRoom airLock4 = new AirLockRoom(539, 4    , 9,  7, 1, 1, new int[]{15}        ,
                new Door[]{new FullyOpenAirLockDoor(10.0, 7.5, 1.0, 539, 15),
                        new AirLockDoor(9.5, 7.0, 1.0, 539, 30, false),
                        new AirLockDoor(9.0, 7.5, 1.0, 539, 30,false),
                        new AirLockDoor(9.5, 8.0, 1.0, 539, 30, false)});
        airLock4.setZ(+1);
        airLock4.addDockingPoint(new DockingPoint("Airlock 4 - Port", "Auxiliary", new Point(1, 0), new Point(0, -1), airLock4));
        airLock4.addDockingPoint(new DockingPoint("Airlock 4 - Aft", "Auxiliary", new Point(1, 0), new Point(-1, 0), airLock4));
        airLock4.addDockingPoint(new DockingPoint("Airlock 4 - Starboard", "Auxiliary", new Point(1, 0), new Point(0, 1), airLock4));
        gm.addRoom(airLock4, ss13, "center");

        Room robotics = new RoboticsRoom(448, 10, 7, 2, 2, new int[]{15},
                new Door[]{new EngineeringDoor(12.0, 8.0, 448, 13, true)});
        {
            AreaPowerControl apc = new AreaPowerControl(robotics, frontHall);
            apc.setAbsolutePosition(12, 7);
        }
        gm.addRoom(robotics, ss13, "center");

        Room portHallFront = new HallwayRoom(16, "Port Hall Front"     , ""       ,13,  3, 2, 3, new int[]{13, 17, 18, 19},
                new Door[]{new NormalDoor(15.0, 5.5, 16, 17)});
        gm.addRoom(portHallFront, ss13, "front");
        gm.addRoom(portHallFront, ss13, "port");

        Room bridge = new BridgeRoom(17, 15,  5, 3, 3, new int[]{16, 20}    ,
                new Door[]{new CommandDoor(16.0, 8.0, 17, 20, false)} );
        gm.addRoom(bridge, ss13, "front");

        Room ss = new SecurityStationRoom(gameData, 18, 15,  1, 2, 3, new int[]{16}        ,
                new Door[]{new SecurityDoor(15.0, 3.5, 18, 16, false) }, portHallFront );
        ss.addObject(new SolarArrayControl(ss, 14325, gameData), RelativePositions.UPPER_LEFT_CORNER);
        gm.addRoom(ss, ss13, "front");
        gm.addRoom(ss, ss13, "port");

        Room gate = new HallwayRoom(19, "Shuttle Gate"        , "Gate"   ,10,  2, 3, 2, new int[]{16, 21, 23, 447, 450},
                new Door[]{new NormalDoor(10.0, 3.5, 19, 23),
                        new NormalDoor(13.0, 3.5, 19, 16)});
        gate.addObject(new UpgoingStairsDoor(gate));
        gate.addObject(new DowngoingStairsDoor(gate));
        gate.addObject(new ATM(gameData, gate));
        gm.addRoom(gate, ss13, "port");


        Room CQ = new CaptainsQuartersRoom(gameData, 20, 15,  8, 2, 2, new int[]{17}        ,
                new Door[]{} );
        {
            AreaPowerControl apc = new AreaPowerControl(bridge, CQ);
            apc.setAbsolutePosition(17, 8);
        }
        gm.addRoom(CQ, ss13, "front");

        AirLockRoom airLock2 = new AirLockRoom(21, 2   ,13,  2, 1, 1, new int[]{19}        ,
                new Door[]{new FullyOpenAirLockDoor(13.0, 2.5, 0.0, 21, 19),
                new AirLockDoor(13.5, 2.0, 21, 30)} );
        airLock2.addDockingPoint(new DockingPoint("Airlock 2", "Personnel", new Point(-1, 0), new Point(0, -1), airLock2));
        gm.addRoom(airLock2, ss13, "port");
        gm.addRoom(airLock2, ss13, "front");

        {
            AirlockPanel ap = new AirlockPanel(airLock2, gate);
            ap.setAbsolutePosition(13.0, 2.0);
            gate.addObject(ap);
        }

        Room army = new ArmoryRoom(22,                             10,  4, 3, 2, new int[]{}        ,         new Door[]{});
        army.setDoors(new Door[]{new SecurityDoor(11.0, 4.0, 22, 19, true)});

        {
            AreaPowerControl apc = new AreaPowerControl(army, gate);
            apc.setAbsolutePosition(12, 4);
        }

        gm.addRoom(army, ss13, "center");

        Room portHallAft = new HallwayRoom(23, "Port Hall Aft"       , "P O R T"       , 6,  3, 4, 2, new int[]{19, 24, 5} ,
                new Door[]{new NormalDoor(7.5, 3.0, 23, 24)} );
        Noticeboard noticeBoard = new Noticeboard(portHallAft);
        noticeBoard.setAbsolutePosition(8.5, 3);
        portHallAft.addObject(noticeBoard);
        gm.addRoom(portHallAft, ss13, "port");

        Room sickbay = new SickbayRoom(24, 6,  0, 3, 3, new int[]{23, 25, 1} ,         new Door[]{});
        GameObject apcSick = new AreaPowerControl(sickbay, labRoom);
        apcSick.setAbsolutePosition(6, 2);
        gm.addRoom(sickbay, ss13, "port");
        AirLockRoom airLock3 = new AirLockRoom(25, 3    , 5,  0, 1, 1, new int[]{24}        ,
                new Door[]{new FullyOpenAirLockDoor(6.0, 0.5, 0.0, 25, 24),
                        new AirLockDoor(5.0, 0.5, 25, 30),
                        new AirLockDoor(5.5, 0.0, 25, 30)});
        airLock3.addDockingPoint(new DockingPoint("Airlock 3 - Aft", "Auxiliary", new Point(1, 0), new Point(-1, 0), airLock3));
        airLock3.addDockingPoint(new DockingPoint("Airlock 3 - Port", "Auxiliary", new Point(1, 0), new Point(0, -1), airLock3));
        {
            AirlockPanel ap = new AirlockPanel(airLock3, sickbay);
            ap.setAbsolutePosition(6.0, 1.0);
            sickbay.addObject(ap);
        }

        gm.addRoom(airLock3, ss13, "port");

        Room r = new GeneratorRoom(26, 6,  5, 3, 3, new int[]{}         ,
                new Door[]{new EngineeringDoor(6.0, 6.5, 26, 5, true)}, gameData );
        {
            AreaPowerControl apc = new AreaPowerControl(r, portHallAft);
            apc.setAbsolutePosition(8, 5);
        }
        gm.addRoom(r, ss13, "center");

        Room panorama = new PanoramaRoom(27, 1,  3, 1, 3, new int[]{1}      ,         new Door[]{} );
        panorama.addObject(new SolarArrayControl(panorama, 14323, gameData));
        gm.addRoom(panorama, ss13, "aft");

        Room nukieShip = new NukieShipRoom(28, 16, 13, 2, 1, new int[]{7, 21, 25, 128},
                new Door[]{new NormalDoor(18.0, 13.5, 28, 128)});
        gm.addRoom(nukieShip, ss13, "nuke");
        Room nukieShip2 = new NukieShipStorageRoom(128, 18, 12, 1, 2, new int[]{28, 7, 21, 25}, new Door[]{});
        gm.addRoom(nukieShip2, ss13, "nuke");

        Room brig = new BrigRoom(29, 15, 4, 1, 1, new int[]{}, new Door[]{});
        brig.setDoors(new Door[]{new NormalDoor(15.0, 4.5, 0.0, 29, 16, true)});
        {
            AreaPowerControl apc = new AreaPowerControl(brig, ss);
            apc.setAbsolutePosition(16, 4);
        }
        gm.addRoom(brig, ss13, "center");

        SpaceRoom space = new SpaceRoom(30, 0, 0, 0, 0);
        addEventsToSpaceRoom(space, gameData);
        gm.addRoom(space, ss13, "space");

        {
            AirlockPanel ap = new AirlockPanel(airLock2, space);
            ap.setAbsolutePosition(13.0, 2.0);
            space.addObject(ap);
        }
        {
            AirlockPanel ap = new AirlockPanel(airLock1, space);
            ap.setAbsolutePosition(2.0, 12.0);
            space.addObject(ap);
        }
        {
            AirlockPanel ap = new AirlockPanel(airLock3, space);
            ap.setAbsolutePosition(5.0, 0.0);
            space.addObject(ap);
        }
        {
            AirlockPanel ap = new AirlockPanel(airLock4, space);
            ap.setAbsolutePosition(9.0, 8.0, 1.0);
            space.addObject(ap);
        }
        {
            AirlockPanel ap = new AirlockPanel(airLock4, aiCore);
            ap.setAbsolutePosition(10.0, 8.0, 1.0);
            aiCore.addObject(ap);
        }
        {
            AirlockPanel ap = new AirlockPanel(airLock5, space);
            ap.setAbsolutePosition(12.0, 13.0, -1.0);
            space.addObject(ap);
        }
        {
            AirlockPanel ap = new AirlockPanel(airLock5, cargoBay);
            ap.setAbsolutePosition(12.0, 12.0, -1.0);
            cargoBay.addObject(ap);
        }



        Room ventilationShaft = new AirDuctRoom(499, 3, 2, 1, 5, new int[]{500, 501, 502, 503}, new Door[]{});
        gm.addRoom(ventilationShaft, ss13, "aft");
        Room vent2 = new AirDuctRoom(500, 2, 7, 13, 1, new int[]{499, 501, 502, 503}, new Door[]{});
        gm.addRoom(vent2, ss13, "center");
        Room vent3 = new AirDuctRoom(501, 7, 8, 1, 4, new int[]{499, 500, 502, 503}, new Door[]{});
        vent3.addObject(new SolarArrayControl(vent3, 14326, gameData));
        gm.addRoom(vent3, ss13, "starboard");
        Room vent4 = new AirDuctRoom(502, 15, 3, 1, 7, new int[]{499, 500, 501, 503}, new Door[]{});
        gm.addRoom(vent4, ss13, "front");
        Room vent5 = new AirDuctRoom(503, 9, 4, 1, 3, new int[]{499, 500, 501, 502}, new Door[]{});
        gm.addRoom(vent5, ss13, "port");


        SolarPanelRoom spr = new SolarPanelRoom(14323, -3, 2, 0, 5, 1, 6);
        gm.addRoom(spr, ss13, "aft");
        gm.addRoom(spr, ss13, "port");


        SolarPanelRoom spr2 = new SolarPanelRoom(14324, 12, 1, 1, 1, 7, 9);
        gm.addRoom(spr2, ss13, "center");
        gm.addRoom(spr2, ss13, "port");

        SolarPanelRoom spr3 = new SolarPanelRoom(14325, 13, 1, 1, 4, 1, 5);
        gm.addRoom(spr3, ss13, "front");
        gm.addRoom(spr3, ss13, "port");

        SolarPanelRoom spr4 = new SolarPanelRoom(14326, 8, 6, -1, 2, 4, 11);
        gm.addRoom(spr4, ss13, "starboard");


    }

}
