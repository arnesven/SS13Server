package model.map.builders;

import model.GameData;
import model.events.Event;
import model.events.ambient.ColdEvent;
import model.events.NoPressureEverEvent;
import model.map.GameMap;
import model.map.rooms.Room;


/**
 * @author erini02
 * Class which builds the game map.
 * TODO: Make this into a class hierarchy and use polymorphism
 * to enable easy extension of new maps.
 */
public abstract class MapBuilder {

    protected static String ss13 = GameMap.STATION_LEVEL_NAME;

    protected abstract void buildPart(GameData gameData, GameMap gm);

	/**
	 * Creates the map of the game and returns it.
	 * TODO: The armory should not be connected to anything from the start. 
	 * Only after using the keycard should it open.
	 * @return the game's map.
	 */
	public static GameMap createMap(GameData gameData) {
		//ArrayList<Room> result = new ArrayList<>();

        GameMap gm = new GameMap(ss13);

        //MapBuilder station = new DonutSS13Builder();
        MapBuilder station = new ValleyForgeSS13Builder();
        MapBuilder derelict = new DerelictBuilder();
        MapBuilder otherPlaces = new OtherPlacesBuilder();


        otherPlaces.buildPart(gameData, gm);
        station.buildPart(gameData, gm);
        derelict.buildPart(gameData, gm);
        gm.setMapReferenceForAllRooms();

		return gm;
	}



    protected static void addEventsToSpaceRoom(Room space, GameData gameData) {
        Event noPress = new NoPressureEverEvent(space);
        space.addEvent(noPress);
        Event cold = new ColdEvent(space);
        space.addEvent(cold);
        gameData.addEvent(cold);
        gameData.addEvent(noPress);
    }

}
