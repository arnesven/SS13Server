package model.map;
import java.util.ArrayList;

import model.GameData;
import model.characters.general.RobotCharacter;
import model.items.general.FireExtinguisher;
import model.items.general.MoneyStack;
import model.items.general.NuclearDisc;
import model.items.suits.SpaceSuit;
import model.npcs.*;
import model.npcs.animals.CatNPC;
import model.npcs.animals.ChimpNPC;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.TellRumorsBehavior;
import model.npcs.robots.RobotNPC;
import model.objects.AITurret;
import model.objects.ATM;
import model.objects.StasisPod;
import model.objects.consoles.*;
import model.objects.consoles.AirLockControl;
import model.objects.general.EvidenceBox;
import model.objects.general.Lockers;
import model.objects.general.MedkitDispenser;


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
		ArrayList<Room> result = new ArrayList<>();
		//                   ID  Name                   shortname  x   y  w  h   neighbors
		result.add(new LabRoom(1, 2,  1, 4, 3, new int[]{27, 24, 5} , new double[]{5.0, 4.0, 2.0, 3.5, 6.0, 1.5} ));
		result.add(new Room( 2, "Chapel"              , "Chap"   , 2,  4, 2, 2, new int[]{5}         ,         new double[]{4.0, 5.0}, RoomType.support ));

        Room green = new GreenhouseRoom( 3,  0,  6, 3, 4, new int[]{4, 6, 27}  ,         new double[]{1.5, 6.0}  );
        NPC chimp = new ChimpNPC(green);
        gameData.addNPC(chimp);

        result.add(green);
		result.add(new Room( 4, "Airtunnel"           , ""       , 3,  7, 1, 1, new int[]{3, 5}      ,         new double[]{3.0, 7.5, 4.0, 7.5}, RoomType.hall));
		result.add(new Room( 5, "Aft Hall"            , "AFT"    , 4,  4, 2, 4, new int[]{1, 2, 4, 9, 26, 23}, new double[]{6.0, 4.5, 6.0, 6.5, 5.5, 8.0}, RoomType.hall));
		result.add(new Room( 6, "Aft Walkway"         , ""       , 2, 10, 2, 1, new int[]{3, 7, 8}   ,         new double[]{2.5, 10.0, 4.0, 10.5}, RoomType.hall));
		result.add(new AirLockRoom( 7,  1, 2, 11, 1, 1, new int[]{6}         ,         new double[]{2.5, 11.0} ));
		
		KitchenRoom kitch = new KitchenRoom(8,  4, 10, 2, 3, new int[]{6, 9, 10}  ,         new double[]{6.0, 11.5, 5.5, 10.0} );
		result.add(kitch);
        Room sHallAft =new Room( 9, "Starboard Hall Aft"  , "S T A R -" , 5,  8, 4, 2, new int[]{5, 8, 10, 11},       new double[]{9.0, 9.5}, RoomType.hall );
		result.add(sHallAft);


        Room bar = new BarRoom(10, 6, 10, 3, 2, new int[]{8, 9, 12}  ,         new double[]{7.5, 10.0, 9.0, 11.5}, RoomType.support);
        RobotNPC bar2d2 = new BAR2D2Robot(bar.getID(), bar);
        gameData.addNPC(bar2d2);
        result.add(bar);
		result.add(new Room(11, "Starboard Hall Front", "B O A R D"       , 9,  9, 3, 2, new int[]{9, 12, 13} ,         new double[]{12.0, 9.5}, RoomType.hall ));
		Room dorms = new Room(12, "Dorms"               , "Dorm"   , 9, 11, 4, 3, new int[]{10, 11, 14},         new double[]{10.5, 11.0, 13.0, 11.5} , RoomType.support);
		dorms.addObject(new Lockers(dorms));
        dorms.addObject((new StasisPod(dorms)));
		result.add(dorms);
		result.add(new Room(13, "Front Hall"          , "FRONT"     ,12,  6, 2, 4, new int[]{11, 14, 15, 16},     new double[]{13.5, 10.0, 12.0, 8.0, 13.5, 6.0}, RoomType.hall ));
		Room office = new Room(14, "Office"              , "Offc"   ,13, 10, 2, 2, new int[]{12, 13}    ,         new double[]{}, RoomType.command );
		office.addObject(new AdministrationConsole(office, gameData));
		result.add(office);
		Room aiCore = new Room(15, "AI Core"             , "AI"     ,10,  7, 2, 2, new int[]{13}        ,         new double[]{}, RoomType.tech );
		AIConsole aiCons = new AIConsole(aiCore);
        aiCore.addObject(aiCons);
        aiCore.addObject(new BotConsole(aiCore));
        aiCore.addObject(new AITurret(aiCore, aiCons, gameData));
        result.add(aiCore);
		Room portHallFront = new Room(16, "Port Hall Front"     , ""       ,13,  3, 2, 3, new int[]{13, 17, 18, 19},     new double[]{15.0, 5.5, 15.0, 3.5}, RoomType.hall );
		result.add(portHallFront);
		
		Room bridge = new Room(17, "Bridge"              , "Brdg"   ,15,  5, 3, 3, new int[]{16, 20}    ,         new double[]{16.0, 8.0} , RoomType.command );
		bridge.addItem(new SpaceSuit());
        bridge.addItem(new FireExtinguisher());
		bridge.addObject(new AirLockControl(bridge));
		bridge.addObject(new SecurityCameraConsole(bridge));
		result.add(bridge);
	
		Room ss = new Room(18, "Security Station"    , "SS"     ,15,  2, 2, 2, new int[]{16}        ,         new double[]{} , RoomType.security );
		ss.addObject(new CrimeRecordsConsole(ss, gameData));
		ss.addObject(new EvidenceBox(ss));

        result.add(ss);
		
		Room gate =new Room(19, "Shuttle Gate"        , "Gate"   ,10,  2, 3, 2, new int[]{16, 21, 23},         new double[]{10.0, 3.5, 13.0, 3.5}, RoomType.hall );
		result.add(gate);


		Room CQ = new Room(20, "Captain's Quarters"  , "CQ"     ,15,  8, 2, 2, new int[]{17}        ,         new double[]{16.0, 8.0} , RoomType.command);
        NPC cat = new CatNPC(CQ);
        CQ.addItem(new NuclearDisc());
        CQ.addItem(new MoneyStack(300));
        gameData.addNPC(cat);



        result.add(CQ);
		result.add(new AirLockRoom(21, 2   ,13,  2, 1, 1, new int[]{19}        ,         new double[]{13.0, 2.5} ));
		Room army = new ArmoryRoom(22,                             10,  4, 3, 2, new int[]{22}        ,         new double[]{-11.0, 4.0});
		{
			KeyCardLock l1 = new KeyCardLock(army, gate, true, 4.0);
			gate.addObject(l1);
			army.addObject(l1);
		}

		result.add(army);
		result.add(new Room(23, "Port Hall Aft"       , "P O R T"       , 6,  3, 4, 2, new int[]{19, 24, 5} ,         new double[]{7.5, 3.0, }, RoomType.hall ));
		Room sickbay = new Room(24, "Sickbay"             , "Sick"   , 6,  0, 3, 3, new int[]{23, 25, 1} ,         new double[]{}, RoomType.science );
		sickbay.addObject(new MedkitDispenser(3, sickbay));
        sickbay.addObject(new StasisPod(sickbay));
		result.add(sickbay);
		result.add(new AirLockRoom(25, 3    , 5,  0, 1, 1, new int[]{24}        ,         new double[]{6.0, 0.5}  ));

		result.add(new GeneratorRoom(26, 6,  5, 3, 3, new int[]{5}         ,         new double[]{} ));
		result.add(new Room(27, "Panorama Walkway"    , ""       , 1,  3, 1, 3, new int[]{1, 3}      ,         new double[]{}, RoomType.hall ));
		
		Room nukieShip = new NukieShipRoom(28, new int[]{7, 21, 25}, new double[]{-1.0, -1.0});
		result.add(nukieShip);
		
		Room brig = new Room(29, "Brig", "", 15, 4, 1, 1, new int[]{29}, new double[]{-14.65, 4.5}, RoomType.security);
		result.add(brig);
		{
			KeyCardLock l1 = new KeyCardLock(brig, portHallFront, true, 3.0);
			brig.addObject(l1);
			portHallFront.addObject(l1);
		}

        Room dummy = new Room(30, "Dummy", "", 18, 1, 0, 0,
                                new int[]{28}, new double[]{-1.0, -1.0}, RoomType.other);
        result.add(dummy);
        Room otherDim = new OtherDimension(31, new int[]{30}, new double[]{-1.0, -1.0});
        Room prisonPlanet = new Room(32, "Prison Planet", "", 18, 1, 0, 0, new int[]{30}, new double[]{-1.0, -1.0}, RoomType.other);

        result.add(otherDim);
        result.add(prisonPlanet);
		GameMap gm = new GameMap(result);


		

		return gm;
	}

}
