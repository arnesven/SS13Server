package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.items.suits.SunGlasses;
import util.MyRandom;
import model.GameData;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.suits.FancyClothes;
import model.map.NukieShipRoom;
import model.map.Room;

public class TouristCharacter extends CrewCharacter {

	public TouristCharacter() {
		super("Tourist", 0, 1.0);
	}

	@Override
	public List<GameItem> getStartingItems() {
		List<GameItem> l = new ArrayList<GameItem>();
		l.add(new FancyClothes());
        l.add(new SunGlasses());
		return l;
	}
	
	@Override
	public Room getStartingRoom(GameData gameData) {
		Room room;
		do {
			room = MyRandom.sample(gameData.getRooms());
		} while (room instanceof NukieShipRoom);
		
		return room;
	}

	@Override
	public GameCharacter clone() {
		return new TouristCharacter();
	}
	
}
