package model.misc;

import java.util.Collection;

import util.Logger;
import model.GameData;
import model.events.SantaClauseEvent;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.map.rooms.RoomType;
import model.objects.christmas.Christmas_lights;
import model.objects.christmas.Snowman;

public class ChristmasBooster {

	public static void addStuff(GameData gameData) {

		gameData.getGameMode().getEvents().put("santa", new SantaClauseEvent());


		Room bridge = null;
		try {
			bridge = gameData.getRoom("Bridge");
			bridge.addObject(new Snowman(bridge));
		} catch (NoSuchThingException e) {
			Logger.log("No bridge found, no snowman");
		}
		
		Collection<Room> ss13Rooms = gameData.getMap().getRoomsForLevel("ss13");
		
		for (Room eachRoom : ss13Rooms){
			if (eachRoom.getType().equals(RoomType.hall)){
				eachRoom.addObject(new Christmas_lights(eachRoom));	
			}
			
		}
		
	}

}
