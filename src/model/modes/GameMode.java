package model.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Client;
import model.GameData;
import model.characters.GameCharacter;
import model.map.Room;

public abstract class GameMode {
	
	private static String[] charNames = 
		{"Captain", "Head of Staff", "Security Officer", "Detective", "Doctor",
		 "Biologist", "Engineer", "Chemist", "Geneticist", "Roboticist",
		 "Janitor",   "Chef", "Bartender", "Mechanic", "Chaplain"};
	
	private static int[] startingLocations =
		{ 20, 14, 18, 12, 24,
		  3,  26, 1, 1, 1,
		  23, 8, 10, 26, 2};
	
	private HashMap<String, GameCharacter> availableChars;
	
	public GameMode() {
		availableChars = new HashMap<>();
		for (int i = 0; i < charNames.length; ++i ) {
			availableChars.put(charNames[i], new GameCharacter(charNames[i], startingLocations[i]));
		}
		
	}

	protected abstract void setUpOtherStuff(GameData gameData);
	protected abstract void assignCharactersToPlayers(GameData gameData);
	public abstract boolean gameOver(GameData gameData);
	public abstract void setStartingLastTurnInfo();

	

	protected GameCharacter getCharacterForString(String string) {
		return availableChars.get(string);
	}
	
	protected List<GameCharacter> getAllCharacters() {
		List<GameCharacter> list = new ArrayList<>();
		list.addAll(availableChars.values());
		return list;
	}

	public void setup(GameData gameData) {
		assignCharactersToPlayers(gameData);
	
		
		for (Client c : gameData.getClients()) {
			Room startRoom = gameData.getRoomForId(c.getCharacter().getStartingRoom());
			c.moveIntoRoom(startRoom);
			c.setNextMove(c.getPosition().getID());
		}
		
		setUpOtherStuff(gameData);
	}






}
