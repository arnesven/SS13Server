package model.modes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.MyRandom;
import model.Actor;
import model.Player;
import model.GameData;
import model.actions.SpeedComparator;
import model.actions.characteractions.MeowingAction;
import model.characters.CatCharacter;
import model.characters.CharacterSpeedComparator;
import model.characters.GameCharacter;
import model.characters.TarsCharacter;
import model.characters.crew.BartenderCharacter;
import model.characters.crew.BiologistCharacter;
import model.characters.crew.CaptainCharacter;
import model.characters.crew.ChaplainCharacter;
import model.characters.crew.ChefCharacter;
import model.characters.crew.ChemistCharacter;
import model.characters.crew.DetectiveCharacter;
import model.characters.crew.DoctorCharacter;
import model.characters.crew.EngineerCharacter;
import model.characters.crew.GeneticistCharacter;
import model.characters.crew.HeadOfStaffCharacter;
import model.characters.crew.JanitorCharacter;
import model.characters.crew.MechanicCharacter;
import model.characters.crew.RoboticistCharacter;
import model.characters.crew.SecurityOfficerCharacter;
import model.characters.crew.TouristCharacter;
import model.events.ElectricalFire;
import model.events.Event;
import model.events.Explosion;
import model.events.HullBreach;
import model.items.GameItem;
import model.items.KeyCard;
import model.items.MedKit;
import model.items.weapons.Weapon;
import model.map.Room;
import model.npcs.CatNPC;
import model.npcs.HumanNPC;
import model.npcs.MeanderingMovement;
import model.npcs.NPC;
import model.npcs.ParasiteNPC;
import model.npcs.SpontaneousAct;
import model.npcs.TARSNPC;

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
	
	//private HashMap<String, GameCharacter> availableChars;
	private static String[] knownModes = {"Host", "Secret"};
	private Map<String,Event> events = new HashMap<>();
	
	public GameMode() {
		events.put("fires", new ElectricalFire());
		events.put("hull breaches", new HullBreach());
		events.put("explosion", new Explosion());
		//events.add(new HullBreach());
	//	events.add()
	}

	private static HashMap<String, GameCharacter> availableChars() {
		HashMap<String, GameCharacter> availableChars = new HashMap<>();
		availableChars.put("Captain", new CaptainCharacter());
		availableChars.put("Head of Staff", new HeadOfStaffCharacter());
		availableChars.put("Security Officer", new SecurityOfficerCharacter());
		availableChars.put("Detective", new DetectiveCharacter());
		availableChars.put("Doctor", new DoctorCharacter());
		availableChars.put("Biologist", new BiologistCharacter());
		availableChars.put("Engineer", new EngineerCharacter());
		availableChars.put("Chemist", new ChemistCharacter());
		availableChars.put("Geneticist", new GeneticistCharacter());
		availableChars.put("Roboticist", new RoboticistCharacter());
		availableChars.put("Janitor", new JanitorCharacter());
		availableChars.put("Chef", new ChefCharacter());
		availableChars.put("Bartender", new BartenderCharacter());
		availableChars.put("Mechanic", new MechanicCharacter());
		availableChars.put("Chaplain", new ChaplainCharacter());
		availableChars.put("Tourist", new TouristCharacter());
		return availableChars;
	}
	
	public static List<String> getAllCharsAsStrings() {
		List<String> gcs = new ArrayList<>();
		gcs.addAll(availableChars().keySet());
		Collections.sort(gcs);
		gcs.add("TARS");
		gcs.add("Cat");
		return gcs;
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
	protected abstract List<GameCharacter> assignCharactersToPlayers(GameData gameData);
	
	
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


	public abstract String getSummary(GameData gameData);
	
	
	protected List<GameCharacter> getAllCharacters() {
		List<GameCharacter> list = new ArrayList<>();
		list.addAll(availableChars().values());
		return list;
	}

	public void setup(GameData gameData) {
		List<GameCharacter> remainingChars = assignCharactersToPlayers(gameData);
			
		moveCharactersIntoStartingRooms(gameData);
		
		addNPCs(gameData, remainingChars);
		giveCharactersStartingItems(gameData);
		
		setUpOtherStuff(gameData);
		addStartingMessages(gameData);
	}

	private void moveCharactersIntoStartingRooms(GameData gameData) {
		for (Player c : gameData.getPlayersAsList()) {
			Room startRoom = c.getCharacter().getStartingRoom(gameData);
			c.moveIntoRoom(startRoom);
			c.setNextMove(c.getPosition().getID());
		}
	}



	private void giveCharactersStartingItems(GameData gameData) {
		List<Actor> actors = new ArrayList<Actor>();
		actors.addAll(gameData.getPlayersAsList());
		actors.addAll(gameData.getNPCs());
		
		for (Actor c : actors) {
			List<GameItem> startingItems = c.getCharacter().getStartingItems();
			System.out.println("Giving starting items to " + c.getPublicName());
			for (GameItem it : startingItems) {
				c.addItem(it);
			}		
		}
		
		
	}



	protected void addNPCs(GameData gameData, List<GameCharacter> remainingChars) {
		NPC cat = new CatNPC(gameData.getRoomForId(20));
		gameData.addNPC(cat);
		
		NPC tars = new TARSNPC(gameData.getRooms().get(MyRandom.nextInt(gameData.getRooms().size())));
		gameData.addNPC(tars);
		
		int noOfNPCs = Math.min(MyRandom.nextInt(3) + 4, remainingChars.size());
		for ( ; noOfNPCs > 0 ; noOfNPCs--) {
			GameCharacter gc;
			if (remainingChars.size() == 0) {
				gc = new TouristCharacter();
			} else {
				gc = remainingChars.remove(MyRandom.nextInt(remainingChars.size()));
			}
			NPC human = new HumanNPC(gc, gc.getStartingRoom(gameData));
			gameData.addNPC(human);
			System.out.println("Adding npc " + gc.getBaseName());
			
		}
		
	}



	public static String getAvailableJobs() {
		StringBuffer res = new StringBuffer();
		List<GameCharacter> list = new ArrayList<>();
		list.addAll(availableChars().values());
		SpeedComparator s;
		Collections.sort(list, new CharacterSpeedComparator());
		
		for (GameCharacter gc : list) {
			res.append("p" + gc.getBaseName() + ":");
		}
		res.append("aTraitor:");
		res.append("aHost:");
		res.append("aChangeling:");
		res.append("aOperative");
		
		return res.toString();
	}


	public static Set<String> getAvailCharsAsStrings() {
		return availableChars().keySet();
	}

	public static String getAvailableModesAsString() {
		StringBuffer res = new StringBuffer();
		for (String s : knownModes) {
			if (!s.equals(knownModes[0])) {
				res.append(":");
			}
			res.append(s);
			
		}
		return res.toString();
	}
	

	public void triggerEvents(GameData gameData) {
		spawnParasites(gameData);

		for (Event e : events.values()) {
			e.apply(gameData);
		}

		triggerModeSpecificEvents(gameData);
	}

	protected void triggerModeSpecificEvents(GameData gameData) { }

	
	protected void spawnParasites(GameData gameData) { 
		//possibly spawn some parasites
		double PARASITE_SPAWN_CHANCE = 0.33;

		if (MyRandom.nextDouble() < PARASITE_SPAWN_CHANCE) {
			Room randomRoom = gameData.getRooms().get(MyRandom.nextInt(gameData.getRooms().size()));
			NPC parasite = new ParasiteNPC(randomRoom);
			gameData.addNPC(parasite);
		}
		
	}

	public Map<String, Event> getEvents() {
		return events;
	}


	



}
