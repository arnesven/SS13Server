package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.GameData;
import model.characters.GameCharacter;
import model.items.GameItem;
import model.items.suits.FancyClothes;
import model.map.Room;

public class TouristCharacter extends GameCharacter {

	public TouristCharacter() {
		super("Tourist", 0, 1.0);
	}

	@Override
	public List<GameItem> getStartingItems() {
		List<GameItem> l = new ArrayList<GameItem>();
		l.add(new FancyClothes());
		return l;
	}
	
	@Override
	public Room getStartingRoom(GameData gameData) {
		return gameData.getRooms().get(MyRandom.nextInt(gameData.getRooms().size()));
	}
	
}
