package model.characters;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.GameData;
import model.items.GameItem;
import model.map.Room;

public class TouristCharacter extends GameCharacter {

	public TouristCharacter() {
		super("Tourist", 0, 1.0);
	}

	@Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<GameItem>();
	}
	
	@Override
	public Room getStartingRoom(GameData gameData) {
		return gameData.getRooms().get(MyRandom.nextInt(gameData.getRooms().size()));
	}
	
}
