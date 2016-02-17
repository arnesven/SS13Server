package model.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Client;
import model.GameData;
import model.actions.MeowingAction;
import model.characters.CatCharacter;
import model.characters.GameCharacter;
import model.map.Room;
import model.npcs.MeanderingMovement;
import model.npcs.NPC;
import model.npcs.SpontaneousAct;

/**
 * @author erini02
 * Class for representing a GameMode, i.e. what a round of this game should
 * be about, what antagonists are there, and what are the objectives for the
 * teams.
 * Ideas for GameModes (extending classes):
 * 
 * Host - A hive has spawned somewhere on the station and is spreading parasites
 * and disease. One player (the host) has already been infected, and will protect
 * the hive at all costs. The player will do this by attacking the crew, or infecting
 * them, turning them over to his/her side. The humans must find the hive and destroy it
 * before the time runs out.
 * 
 * Operation - A small team (2-3) of operatives starts the game off-station and must try to
 * infiltrate the station through the airlocks and obtain the captain's security disk. 
 * This is difficult however, since a crewmember will immediately recognize the operatives'
 * spacesuits as different from the station's. Will the operatives stay together or will they
 * spread out? Will they try to dispose of their spacesuit and get som SS13 clothes, or will 
 * they try to make a quick smash and grab? The crew must prevent the operatives from
 * obtaining the disk. If they can escape through one of the airlocks with the disk, they will
 * be able to nuke the station! The operatives are assigned contacts at the beginning of the
 * game, i.e. characters which they know are NPCs, but who they can pretend that they are playing
 * in order to maintain the false pretence of who they are.
 * 
 * Traitors - Two of the crew's members are disgruntled and have contacted the
 * evil syndicate. They have now been assigned special traitorous missions to be carried out
 * on the station. E.g. assassinations or bombings. They can secretly order some traitor
 * equipment, and need only wait until the right moment to strike. The crew's only objective
 * is to survive the round, but who can be trusted and who is a traitor?
 * 
 * Xenomorph - A strange alien life form has gotten aboard the station. At the start of the game
 * it is simply a small parasite, but once it attacks and sucs the life force out of a living
 * creature it can take the form of that creature. Be aware! There is now a shape shifting
 * alien on SS13. The objective of the Xenomorph is to kill as many humans as possible, via
 * stealth or by ultimately turning in to the shambling abomination, a super-form of the
 * xenomorph which is more powerful the more creatures it has sucked. The crew must kill the 
 * xeno and survive the round to win the game.
 */
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
	protected abstract void addStartingMessages(GameData gameData);
	

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
		
		addNPCs(gameData);
		
		setUpOtherStuff(gameData);
		addStartingMessages(gameData);
	}

	protected void addNPCs(GameData gameData) {
		NPC cat = new NPC(new CatCharacter(), new MeanderingMovement(0.5),
						  new SpontaneousAct(0.5, new MeowingAction()), gameData.getRoomForId(20));
		gameData.addNPC(cat);
	}







}
