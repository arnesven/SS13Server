package model.map;

import model.GameData;
import model.characters.general.AbandonedBotCharacter;
import model.events.Event;
import model.events.ambient.ColdEvent;
import model.items.general.FireExtinguisher;
import model.items.general.MoneyStack;
import model.items.general.NuclearDisc;
import model.items.general.Tools;
import model.items.suits.SpaceSuit;
import model.modes.NoPressureEverEvent;
import model.npcs.*;
import model.npcs.animals.CatNPC;
import model.npcs.animals.ChimpNPC;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.RandomSpeechBehavior;
import model.npcs.robots.RobotNPC;
import model.objects.AITurret;
import model.objects.StasisPod;
import model.objects.christmas.Snowman;
import model.objects.consoles.*;
import model.objects.general.*;


/**
 * @author erini02
 * Class which builds the game map.
 * TODO: Make this into a class hierarchy and use polymorphism
 * to enable easy extension of new maps.
 */
public class MapBuilder {



	/**
	 * Creates the map of the game and returns it.
	 * TODO: The armory should not be connected to anything from the start. 
	 * Only after using the keycard should it open.
	 * @return the game's map.
	 */
	public static GameMap createMap(GameData gameData) {
		//ArrayList<Room> result = new ArrayList<>();

        GameMap gm = new GameMap("ss13");

        //                   ID  Name                   shortname  x   y  w  h   neighbors
        LabRoom labRoom = new LabRoom(1, 2, 1, 4, 3, new int[]{27, 24, 5}, new double[]{5.0, 4.0, 2.0, 3.5, 6.0, 1.5});
        gm.addRoom(labRoom, "ss13", "aft");
        gm.addRoom(labRoom, "ss13", "port");

        Room chapel = new Room(2, "Chapel", "Chap", 2, 4, 2, 2, new int[]{5}, new double[]{4.0, 5.0}, RoomType.support);
        MailBox mail = new MailBox(chapel);
        chapel.addObject(mail);
        gm.addRoom(chapel, "ss13", "aft");

        Room green = new GreenhouseRoom( 3,  0,  6, 3, 4, new int[]{4, 6, 27}  ,         new double[]{1.5, 6.0}  );
        NPC chimp = new ChimpNPC(green);
        gameData.addNPC(chimp);
        gm.addRoom(green, "ss13", "aft");



		gm.addRoom(new Room( 4, "Airtunnel"           , ""       , 3,  7, 1, 1, new int[]{3, 5}      ,         new double[]{3.0, 7.5, 4.0, 7.5}, RoomType.hall), "ss13", "aft");
		gm.addRoom(new Room( 5, "Aft Hall"            , "AFT"    , 4,  4, 2, 4, new int[]{1, 2, 4, 9, 26, 23}, new double[]{6.0, 4.5, 6.0, 6.5, 5.5, 8.0}, RoomType.hall), "ss13", "aft");
        Room aftWalk = new Room(6, "Aft Walkway", "", 2, 10, 2, 1, new int[]{3, 7, 8}, new double[]{2.5, 10.0, 4.0, 10.5}, RoomType.hall);
        gm.addRoom(aftWalk, "ss13", "aft");
        gm.addRoom(aftWalk, "ss13", "starboard");
        Room airLock1 = new AirLockRoom( 7,  1, 2, 11, 1, 1, new int[]{6}         ,         new double[]{2.5, 11.0} );
		gm.addRoom(airLock1, "ss13", "aft");
        gm.addRoom(airLock1, "ss13", "starboard");

		KitchenRoom kitch = new KitchenRoom(8,  4, 10, 2, 3, new int[]{6, 9, 10}  ,         new double[]{6.0, 11.5, 5.5, 10.0} );
		gm.addRoom(kitch, "ss13", "starboard");
        Room sHallAft =new Room( 9, "Starboard Hall Aft"  , "S T A R -" , 5,  8, 4, 2, new int[]{5, 8, 10, 11},       new double[]{9.0, 9.5}, RoomType.hall );
		gm.addRoom(sHallAft, "ss13", "starboard");


        Room bar = new BarRoom(10, 6, 10, 3, 2, new int[]{8, 9, 12}  ,         new double[]{7.5, 10.0, 9.0, 11.5}, RoomType.support);
        RobotNPC bar2d2 = new BAR2D2Robot(bar.getID(), bar);
        gameData.addNPC(bar2d2);
        gm.addRoom(bar, "ss13", "starboard");
		gm.addRoom(new Room(11, "Starboard Hall Front", "B O A R D"       , 9,  9, 3, 2, new int[]{9, 12, 13} ,         new double[]{12.0, 9.5}, RoomType.hall ), "ss13", "starboard");
		Room dorms = new Room(12, "Dorms"               , "Dorm"   , 9, 11, 4, 3, new int[]{10, 11, 14},         new double[]{10.5, 11.0, 13.0, 11.5} , RoomType.support);
		dorms.addObject(new Lockers(dorms));
        dorms.addObject((new StasisPod(dorms)));
		gm.addRoom(dorms, "ss13", "starboard");
		gm.addRoom(new Room(13, "Front Hall"          , "FRONT"     ,12,  6, 2, 4, new int[]{11, 14, 15, 16},     new double[]{13.5, 10.0, 12.0, 8.0, 13.5, 6.0}, RoomType.hall ), "ss13", "front");
		Room office = new Room(14, "Office"              , "Offc"   ,13, 10, 2, 2, new int[]{12, 13}    ,         new double[]{}, RoomType.command );
		gm.addRoom(office, "ss13", "front");
		gm.addRoom(office, "ss13", "starboard");

        Room aiCore = new Room(15, "AI Core"             , "AI"     ,10,  7, 2, 2, new int[]{13}        ,         new double[]{}, RoomType.tech );
		AIConsole aiCons = new AIConsole(aiCore);
        aiCore.addObject(aiCons);
        aiCore.addObject(new BotConsole(aiCore));
        aiCore.addObject(new AITurret(aiCore, aiCons, gameData));
        gm.addRoom(aiCore, "ss13", "center");
		Room portHallFront = new Room(16, "Port Hall Front"     , ""       ,13,  3, 2, 3, new int[]{13, 17, 18, 19},     new double[]{15.0, 5.5, 15.0, 3.5}, RoomType.hall );
		gm.addRoom(portHallFront, "ss13", "front");
        gm.addRoom(portHallFront, "ss13", "port");
		
		Room bridge = new Room(17, "Bridge"              , "Brdg"   ,15,  5, 3, 3, new int[]{16, 20}    ,         new double[]{16.0, 8.0} , RoomType.command );
		bridge.addItem(new SpaceSuit());
        bridge.addItem(new FireExtinguisher());
		bridge.addObject(new AirLockControl(bridge));
		bridge.addObject(new SecurityCameraConsole(bridge));
		bridge.addObject(new Snowman(bridge));
		gm.addRoom(bridge, "ss13", "front");
	
		Room ss = new Room(18, "Security Station"    , "SS"     ,15,  2, 2, 2, new int[]{16}        ,         new double[]{} , RoomType.security );
		ss.addObject(new CrimeRecordsConsole(ss, gameData));
		ss.addObject(new EvidenceBox(ss));

        gm.addRoom(ss, "ss13", "front");
        gm.addRoom(ss, "ss13", "port");
		
		Room gate =new Room(19, "Shuttle Gate"        , "Gate"   ,10,  2, 3, 2, new int[]{16, 21, 23},         new double[]{10.0, 3.5, 13.0, 3.5}, RoomType.hall );
		gm.addRoom(gate, "ss13", "port");


		Room CQ = new Room(20, "Captain's Quarters"  , "CQ"     ,15,  8, 2, 2, new int[]{17}        ,         new double[]{16.0, 8.0} , RoomType.command);
        NPC cat = new CatNPC(CQ);
        CQ.addItem(new NuclearDisc(gameData, true));
        CQ.addItem(new MoneyStack(300));
        gameData.addNPC(cat);

        gm.addRoom(CQ, "ss13", "front");

        Room airLock2 = new AirLockRoom(21, 2   ,13,  2, 1, 1, new int[]{19}        ,         new double[]{13.0, 2.5} );
		gm.addRoom(airLock2, "ss13", "port");
        gm.addRoom(airLock2, "ss13", "front");
		Room army = new ArmoryRoom(22,                             10,  4, 3, 2, new int[]{22}        ,         new double[]{11.0, 4.0});
		{
			KeyCardLock l1 = new KeyCardLock(army, gate, true, 4.0);
			gate.addObject(l1);
			army.addObject(l1);
		}

		gm.addRoom(army, "ss13", "center");
		gm.addRoom(new Room(23, "Port Hall Aft"       , "P O R T"       , 6,  3, 4, 2, new int[]{19, 24, 5} ,         new double[]{7.5, 3.0, }, RoomType.hall ), "ss13", "port");
		Room sickbay = new Room(24, "Sickbay"             , "Sick"   , 6,  0, 3, 3, new int[]{23, 25, 1} ,         new double[]{}, RoomType.science );
		sickbay.addObject(new MedkitDispenser(3, sickbay));
        sickbay.addObject(new StasisPod(sickbay));
		gm.addRoom(sickbay, "ss13", "port");
		gm.addRoom(new AirLockRoom(25, 3    , 5,  0, 1, 1, new int[]{24}        ,         new double[]{6.0, 0.5}  ), "ss13", "port");

		gm.addRoom(new GeneratorRoom(26, 6,  5, 3, 3, new int[]{5}         ,         new double[]{}, gameData ), "ss13", "center");

        Room panorama = new Room(27, "Panorama Walkway"    , ""       , 1,  3, 1, 3, new int[]{1, 3}      ,         new double[]{}, RoomType.hall );
        panorama.addObject(new SeedVendingMachine(panorama));
        gm.addRoom(panorama, "ss13", "aft");
		
		Room nukieShip = new NukieShipRoom(28, new int[]{7, 21, 25}, new double[]{-1.0, -1.0});
		gm.addRoom(nukieShip, "ss13", "nuke");
		
		Room brig = new Room(29, "Brig", "", 15, 4, 1, 1, new int[]{29}, new double[]{15.0, 4.5}, RoomType.security);
		gm.addRoom(brig, "ss13", "center");
		{
			KeyCardLock l1 = new KeyCardLock(brig, portHallFront, true, 3.0);
			brig.addObject(l1);
			portHallFront.addObject(l1);
		}

        Room space = new Room(30, "Space", "", 0, 0, 1, 1, new int[]{}, new double[]{}, RoomType.space);

        addEventsToSpaceRoom(space, gameData);

        gm.addRoom(space, "ss13", "space");

        Room dummy = new Room(31, "Dummy", "", 18, 1, 0, 0,
                                new int[]{28}, new double[]{-1.0, -1.0}, RoomType.hidden);
        gm.addRoom(dummy, "ss13", "dummy");
        Room otherDim = new OtherDimension(32, new int[]{30}, new double[]{-1.0, -1.0});
        Room prisonPlanet = new Room(33, "Prison Planet", "P R I S O N P L A N E T", 0, 0, 1, 1, new int[]{30}, new double[]{-1.0, -1.0}, RoomType.outer);

        gm.addRoom(otherDim, "other dimension", "other dimension");
        gm.addRoom(prisonPlanet, "prison planet", "prison planet");

        Room derelictBridge = new Room(34, "Derelict Bridge", "", 40, 40, 3, 1, new int[]{35}, new double[]{}, RoomType.derelict);
        ShipsLogsConsole capsLog = new ShipsLogsConsole(derelictBridge);
        derelictBridge.addItem(new SpaceSuit());
        derelictBridge.addObject(capsLog);
        gm.addRoom(derelictBridge, "derelict", "derelict");
        Room derelictHall =  new Room(35, "Derelict Hall", "", 41, 41, 1, 5, new int[]{34, 36, 37, 38}, new double[]{41.5, 41.0}, RoomType.derelict);
        gm.addRoom(derelictHall, "derelict", "derelict");
        Room derelictLab =  new Room(36, "Derelict Lab", "", 39, 43, 2, 2, new int[]{35}, new double[]{41.0, 43.5}, RoomType.derelict);
        derelictLab.addObject(new TeleportConsole(derelictLab));
        gm.addRoom(derelictLab, "derelict", "derelict");
        Room derelictGen =  new Room(37, "Derelict Generator", "", 40, 46, 3, 3, new int[]{35}, new double[]{41.5, 46.0}, RoomType.derelict);

        NPC abandoned = new RobotNPC(new AbandonedBotCharacter(derelictGen), new MeanderingMovement(0),
                new RandomSpeechBehavior("resources/ABANDON.TXT"), derelictGen);
        gameData.addNPC(abandoned);
        derelictGen.addObject(new DerelictPowerSource(derelictGen, gameData));
        derelictGen.addItem(new Tools());

        gm.addRoom(derelictGen, "derelict", "derelict");
        Room derelictAirLock =  new Room(38, "Derelict Air Lock", "", 42, 44, 1, 1, new int[]{35}, new double[]{42.0, 44.5}, RoomType.derelict);
        derelictAirLock.addObject(new AirlockPanel(derelictAirLock));
        gm.addRoom(derelictAirLock, "derelict", "derelict");

        Room derelictUnreachableRoom =  new Room(39, "Escape Pod", "", 52, 52, 2, 1, new int[]{}, new double[]{}, RoomType.derelict);
        derelictUnreachableRoom.addObject(new AirlockPanel(derelictUnreachableRoom));
        derelictUnreachableRoom.addItem(new SpaceSuit());
        gm.addRoom(derelictUnreachableRoom, "derelict", "derelict");
        Room derelictUnreachableRoom2 =  new Room(40, "Broken Room", "", 35, 35, 1, 2, new int[]{}, new double[]{}, RoomType.derelict);
        gm.addRoom(derelictUnreachableRoom2, "derelict", "derelict");



        Room deepspace = new Room(41, "Deep Space", "D E E P   S P A C E", 6, 8, 3, 3, new int[]{}, new double[]{}, RoomType.space);
        deepspace.addEvent(new NoPressureEverEvent(space));
        deepspace.addEvent(new ColdEvent(space));
        gm.addRoom(deepspace, "deep space", "deep space");


        gm.setMapReferenceForAllRooms();

		return gm;
	}

    private static void addEventsToSpaceRoom(Room space, GameData gameData) {
        Event noPress = new NoPressureEverEvent(space);
        space.addEvent(noPress);
        Event cold = new ColdEvent(space);
        space.addEvent(cold);
        gameData.addEvent(cold);
        gameData.addEvent(noPress);
    }

}
