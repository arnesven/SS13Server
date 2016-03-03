package model.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Player;
import model.GameData;
import model.actions.MeowingAction;
import model.characters.BartenderCharacter;
import model.characters.BiologistCharacter;
import model.characters.CaptainCharacter;
import model.characters.CatCharacter;
import model.characters.ChaplainCharacter;
import model.characters.ChefCharacter;
import model.characters.ChemistCharacter;
import model.characters.DetectiveCharacter;
import model.characters.DoctorCharacter;
import model.characters.EngineerCharacter;
import model.characters.GameCharacter;
import model.characters.GenetecistCharacter;
import model.characters.HeadOfStaffCharacter;
import model.characters.JanitorCharacter;
import model.characters.MechanicCharacter;
import model.characters.RoboticistCharacter;
import model.characters.SecurityOfficerCharacter;
import model.items.GameItem;
import model.items.KeyCard;
import model.items.MedKit;
import model.items.Weapon;
import model.map.Room;
import model.npcs.CatNPC;
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
/**
 * @author erini02
 *
 */
public abstract class GameMode {
	
//	private static String[] charNames = 
//		{"Captain", "Head of Staff", "Security Officer", "Detective", "Doctor",
//		 "Biologist", "Engineer", "Chemist", "Geneticist", "Roboticist",
//		 "Janitor",   "Chef", "Bartender", "Mechanic", "Chaplain"};
//	
//	private static int[] startingLocations =
//		{ 20, 14, 18, 12, 24,
//		  3,  26, 1, 1, 1,
//		  23, 8, 10, 26, 2};
//	
//	private static double[] speeds = 
//		{16.0, 15.0, 14.0, 13.0, 12.0,
//		 11.0, 10.0,  9.0,  8.0,  7.0,
//		  6.0,  5.0,  4.0,  3.0,  2.0};
	
	private HashMap<String, GameCharacter> availableChars;
	
	public GameMode() {
		availableChars = new HashMap<>();
		availableChars.put("Captain", new CaptainCharacter());
		availableChars.put("Head of Staff", new HeadOfStaffCharacter());
		availableChars.put("Security Officer", new SecurityOfficerCharacter());
		availableChars.put("Detective", new DetectiveCharacter());
		availableChars.put("Doctor", new DoctorCharacter());
		availableChars.put("Biologist", new BiologistCharacter());
		availableChars.put("Engineer", new EngineerCharacter());
		availableChars.put("Chemist", new ChemistCharacter());
		availableChars.put("Geneticist", new GenetecistCharacter());
		availableChars.put("Roboticist", new RoboticistCharacter());
		availableChars.put("Janitor", new JanitorCharacter());
		availableChars.put("Chef", new ChefCharacter());
		availableChars.put("Bartender", new BartenderCharacter());
		availableChars.put("Mechanic", new MechanicCharacter());
		availableChars.put("Chaplain", new ChaplainCharacter());

	
	}

	

	/**
	 * This method is called as the last part of the setup.
	 * In this step, the game mode can set up other things
	 * which pertains to this game mode.
	 * @param gameData
	 */
	protected abstract void setUpOtherStuff(GameData gameData);
	
	/**
	 * this method is called as the first step of the setup
	 * at the beginning of the game. Players should receive
	 * their characters in this step.
	 * @param gameData
	 */
	protected abstract void assignCharactersToPlayers(GameData gameData);
	
	
	/**
	 * Checks wether or not the game is over or not.
	 * @param gameData
	 * @return true if the game is over, false otherwise.
	 */
	public abstract boolean gameOver(GameData gameData);
	
	
	
	/**
	 * This method will be called at the start of each turn,
	 * to set the info for players.
	 */
	public abstract void setStartingLastTurnInfo();
	
	/**
	 * This method sets the starting info for ALL the players
	 */
	protected abstract void addStartingMessages(GameData gameData);
	public abstract void triggerEvents(GameData gameData);

	public abstract String getSummary(GameData gameData);

	

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
		giveCharactersStartingItems(gameData);
		
		moveCharactersIntoStartingRooms(gameData);
		
		addNPCs(gameData);
		
		setUpOtherStuff(gameData);
		addStartingMessages(gameData);
	}

	private void moveCharactersIntoStartingRooms(GameData gameData) {
		for (Player c : gameData.getPlayersAsList()) {
			Room startRoom = gameData.getRoomForId(c.getCharacter().getStartingRoom());
			c.moveIntoRoom(startRoom);
			c.setNextMove(c.getPosition().getID());
		}
	}



	private void giveCharactersStartingItems(GameData gameData) {
		for (Player c : gameData.getPlayersAsList()) {
			List<GameItem> startingItems = c.getCharacter().getStartingItems();
			System.out.println("Giving starting items to " + c.getName());
			for (GameItem it : startingItems) {
				c.addItem(it);
			}
			
			
		}
		
	}



	protected void addNPCs(GameData gameData) {
		NPC cat = new CatNPC(gameData.getRoomForId(20));
		gameData.addNPC(cat);
	}





	





}
