package model.map;
import java.util.ArrayList;
import java.util.HashMap;

import model.objects.KeyCardLock;


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
	public static GameMap createMap() {
		ArrayList<Room> result = new ArrayList<>();
		//                   ID  Name                   shortname  x   y  w  h   neighbors
		result.add(new Room( 1, "Lab"                 , "Lab"    , 2,  1, 4, 3, new int[]{27, 24, 5} ,         new double[]{5.0, 4.0, 2.0, 3.5, 6.0, 1.5} ));
		result.add(new Room( 2, "Chapel"              , "Chap"   , 2,  4, 2, 2, new int[]{5}         ,         new double[]{4.0, 5.0} ));
		result.add(new Room( 3, "Greenhouse"          , "GH"     , 0,  6, 3, 4, new int[]{4, 6, 27}  ,         new double[]{1.5, 6.0}  ));
		result.add(new Room( 4, "Airtunnel"           , ""       , 3,  7, 1, 1, new int[]{3, 5}      ,         new double[]{3.0, 7.5, 4.0, 7.5}  ));
		result.add(new Room( 5, "Aft Hall"            , ""       , 4,  4, 2, 4, new int[]{1, 2, 4, 9, 26, 23}, new double[]{6.0, 4.5, 6.0, 6.5, 5.5, 8.0}));
		result.add(new Room( 6, "Aft Walkway"         , ""       , 2, 10, 2, 1, new int[]{3, 7, 8}   ,         new double[]{2.5, 10.0, 4.0, 10.5} ));
		result.add(new Room( 7, "Air Lock #1"         , "1"      , 2, 11, 1, 1, new int[]{6}         ,         new double[]{2.5, 11.0} ));
		result.add(new Room( 8, "Kitchen"             , "Cook"   , 4, 10, 2, 3, new int[]{6, 9, 10}  ,         new double[]{6.0, 11.5, 5.5, 10.0} ));
		result.add(new Room( 9, "Starboard Hall Aft"  , ""       , 5,  8, 4, 2, new int[]{5, 8, 10, 11},       new double[]{9.0, 9.5} ));
		result.add(new Room(10, "Bar"                 , "Bar"    , 6, 10, 3, 2, new int[]{8, 9, 12}  ,         new double[]{7.5, 10.0, 9.0, 11.5}));
		result.add(new Room(11, "Starboard Hall Front", ""       , 9,  9, 3, 2, new int[]{9, 12, 13} ,         new double[]{12.0, 9.5} ));
		result.add(new Room(12, "Dorms"               , "Dorm"   , 9, 11, 4, 3, new int[]{10, 11, 14},         new double[]{10.5, 11.0, 13.0, 11.5} ));
		result.add(new Room(13, "Front Hall"          , ""       ,12,  6, 2, 4, new int[]{11, 14, 15, 16},     new double[]{13.5, 10.0, 12.0, 8.0, 13.5, 6.0} ));
		result.add(new Room(14, "Office"              , "Offc"   ,13, 10, 2, 2, new int[]{12, 13}    ,         new double[]{} ));
		result.add(new Room(15, "AI Core"             , "AI"     ,10,  7, 2, 2, new int[]{13}        ,         new double[]{} ));
		result.add(new Room(16, "Port Hall Front"     , ""       ,13,  3, 2, 3, new int[]{13, 17, 18, 19},     new double[]{15.0, 5.5, 15.0, 3.5} ));
		result.add(new Room(17, "Bridge"              , "Brdg"   ,15,  5, 3, 3, new int[]{16, 20}    ,         new double[]{16.0, 8.0}  ));
		result.add(new Room(18, "Security Station"    , "SS"     ,15,  2, 2, 2, new int[]{16}        ,         new double[]{}  ));
		Room gate =new Room(19, "Shuttle Gate"        , "Gate"   ,10,  2, 3, 2, new int[]{16, 21, 23},         new double[]{10.0, 3.5, 13.0, 3.5} );
		result.add(gate);
		result.add(new Room(20, "Captain's Quarters"  , "CQ"     ,15,  8, 2, 2, new int[]{17}        ,         new double[]{16.0, 8.0}  ));
		result.add(new Room(21, "Air Lock #2"         , "2"      ,13,  2, 1, 1, new int[]{19}        ,         new double[]{13.0, 2.5} ));
		Room army =new Room(22, "Armory"              , "Army"   ,10,  4, 3, 2, new int[]{19}        ,         new double[]{-11.0, 4.0} );
		gate.addObject(new KeyCardLock(army, gate, true));
		result.add(army);
		result.add(new Room(23, "Port Hall Aft"       , ""       , 6,  3, 4, 2, new int[]{19, 24, 5} ,         new double[]{7.5, 3.0, } ));
		result.add(new Room(24, "Sickbay"             , "Sick"   , 6,  0, 3, 3, new int[]{23, 25, 1} ,         new double[]{} ));
		result.add(new Room(25, "Air Lock #3"         , "3"      , 5,  0, 1, 1, new int[]{24}        ,         new double[]{6.0, 0.5}  ));
		result.add(new Room(26, "Generator"           , "Gen"    , 6,  5, 3, 3, new int[]{5}         ,         new double[]{}  ));
		result.add(new Room(27, "Panorama Walkway"    , ""       , 1,  3, 1, 3, new int[]{1, 3}      ,         new double[]{} ));
		
		GameMap gm = new GameMap(result);
		

		return gm;
	}

}
