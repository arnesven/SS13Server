package model.modes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.items.NoSuchThingException;
import model.items.suits.CaptainsHat;
import util.Logger;
import util.MyRandom;
import model.Actor;
import model.Player;
import model.GameData;
import model.actions.general.SpeedComparator;
import model.characters.general.CharacterSpeedComparator;
import model.characters.general.GameCharacter;
import model.characters.crew.BartenderCharacter;
import model.characters.crew.BiologistCharacter;
import model.characters.crew.CaptainCharacter;
import model.characters.crew.ChaplainCharacter;
import model.characters.crew.ChefCharacter;
import model.characters.crew.ScienceOfficerCharacter;
import model.characters.crew.DetectiveCharacter;
import model.characters.crew.DoctorCharacter;
import model.characters.crew.EngineerCharacter;
import model.characters.crew.GeneticistCharacter;
import model.characters.crew.HeadOfStaffCharacter;
import model.characters.crew.JanitorCharacter;
import model.characters.crew.TechnicianCharacter;
import model.characters.crew.RoboticistCharacter;
import model.characters.crew.SecurityOfficerCharacter;
import model.characters.crew.TouristCharacter;
import model.events.ambient.Crazyness;
import model.events.ambient.ElectricalFire;
import model.events.Event;
import model.events.Explosion;
import model.events.ambient.HullBreach;
import model.events.ambient.OngoingEvent;
import model.events.ambient.PowerFlux;
import model.events.ambient.RadiationStorm;
import model.events.ambient.RandomHuskEvent;
import model.events.ambient.SimulatePower;
import model.items.general.FireExtinguisher;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.items.suits.RadiationSuit;
import model.map.GameMap;
import model.map.NukieShipRoom;
import model.map.Room;
import model.npcs.CatNPC;
import model.npcs.ChimpNPC;
import model.npcs.HumanNPC;
import model.npcs.NPC;
import model.npcs.ParasiteNPC;
import model.npcs.SecuritronNPC;
import model.npcs.SnakeNPC;
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


	private static String[] knownModes = {"Host", "Traitor", "Operatives", "Changeling", "Secret"};
	private Map<String,Event> events = new HashMap<>();
	protected ArrayList<NPC> allParasites = new ArrayList<NPC>();

	public GameMode() {
		events.put("fires", new ElectricalFire());
		events.put("hull breaches", new HullBreach());
		events.put("explosion", new Explosion());
		events.put("crazyness", new Crazyness());
		events.put("radiation storms", new RadiationStorm());
		events.put("simulate power", new SimulatePower());
		events.put("Power flux", new PowerFlux());
		events.put("random husks", new RandomHuskEvent());
	}

    public abstract String getName();

	private static HashMap<String, GameCharacter> availableChars() {
		HashMap<String, GameCharacter> availableChars = new HashMap<>();
		availableChars.put("Captain",          new CaptainCharacter());
		availableChars.put("Head of Staff",    new HeadOfStaffCharacter());
		availableChars.put("Security Officer", new SecurityOfficerCharacter());
		availableChars.put("Detective",        new DetectiveCharacter());
		availableChars.put("Doctor",           new DoctorCharacter());
		availableChars.put("Biologist",        new BiologistCharacter());
		availableChars.put("Engineer",         new EngineerCharacter());
		availableChars.put("Science Officer",  new ScienceOfficerCharacter());
		availableChars.put("Geneticist",       new GeneticistCharacter());
		availableChars.put("Roboticist",       new RoboticistCharacter());
		availableChars.put("Janitor",          new JanitorCharacter());
		availableChars.put("Chef",             new ChefCharacter());
		availableChars.put("Bartender",        new BartenderCharacter());
		availableChars.put("Technician",       new TechnicianCharacter());
		availableChars.put("Chaplain",         new ChaplainCharacter());
		availableChars.put("Tourist",          new TouristCharacter());
		return availableChars;
	}

    public static List<GameCharacter> getAllCrew() {
        List<GameCharacter> gcs = new ArrayList<>();
        gcs.addAll(availableChars().values());
        return gcs;
    }

	public static List<String> getAllCrewAsStrings() {
		List<String> gcs = new ArrayList<>();
		gcs.addAll(availableChars().keySet());
		return gcs;
	}

	public static List<String> getAllCharsAsStrings() {
		List<String> gcs = new ArrayList<>();
		gcs.addAll(getAllCrewAsStrings());
		Collections.sort(gcs);
		gcs.add("TARS");
		gcs.add("Cat");
		gcs.add("Chimp");
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
	 * Overload this method to add roles "ontop" of existing ones.
	 * E.g. the host role is assigned in this method. 
	 * Or traitors in traitor-mode.
	 * @param listOfCharacters
	 * @param gameData
	 */
	protected abstract void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData);



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
	 * This method should send a starting message to the antagonist c
	 * at the start of the game
	 * @param c
	 */
	protected abstract void addAntagonistStartingMessage(Player c);

	/**
	 * This method should send a starting message to the protagonist c
	 * at the start of the game.
	 * @param c
	 */
	protected abstract void addProtagonistStartingMessage(Player c);

	/**
	 * This method checks if this player is a antagonist or not.
	 * @param c
	 * @return
	 */
	protected abstract boolean isAntagonist(Player c);


	/**
	 * This method gets the game summary which is displayed
	 * to the clients after the game is over. The summary
	 * can look very different depending on the game mode.
	 * @param gameData
	 * @return
	 */
	public abstract String getSummary(GameData gameData);


	protected List<GameCharacter> getAllCharacters() {
		List<GameCharacter> list = new ArrayList<>();
		list.addAll(availableChars().values());
		return list;
	}

	public void setup(GameData gameData) {
		Logger.log("Game Modes: Going to assign roles");
		List<GameCharacter> remainingChars = assignCharactersToPlayers(gameData);
		Logger.log(" Game Mode: Setup: Characters assigned");
		moveCharactersIntoStartingRooms(gameData);
		Logger.log(" Game Mode: Setup: Characters moved into starting rooms");
			
		addNPCs(gameData, remainingChars);
		Logger.log(" Game Mode: Setup: NPCs added");
		
		giveCharactersStartingItems(gameData);
		Logger.log(" Game Mode: Setup: Chars got starting items");
		
		setUpOtherStuff(gameData);
		Logger.log(" Game Mode: Other stuff setupped");
		
		addItemsToRooms(gameData);
		
		addRandomItemsToRooms(gameData);
		Logger.log(" Game Mode: Items added to rooms");
		
		addStartingMessages(gameData);
	}


	protected void addStartingMessages(GameData gameData) {
		for (Player c : gameData.getPlayersAsList()) {
			if (isAntagonist(c)) {
				addAntagonistStartingMessage(c);
			} else {
				addProtagonistStartingMessage(c);
			}
		}
	}




	/**
	 * this method is called as the first step of the setup
	 * at the beginning of the game. Players should receive
	 * their characters in this step.
	 * @param gameData
	 */
	protected List<GameCharacter> assignCharactersToPlayers(GameData gameData) {
		ArrayList<Player> listOfClients = new ArrayList<Player>();
		listOfClients.addAll(gameData.getPlayersAsList());
		Collections.shuffle(listOfClients);
		
		ArrayList<GameCharacter> listOfCharacters = new ArrayList<>();
		listOfCharacters.addAll(getAllCharacters());

		/// SELECT A CAPTAIN, SS13 MUST ALWAYS HAVE A CAPTAIN
		selectCaptain(listOfClients, listOfCharacters);

		/// ASSIGN ROLES RANDOMLY
		assignRestRoles(listOfClients, listOfCharacters, gameData);	
		assignOtherRoles(listOfCharacters, gameData);

		return listOfCharacters;
	}




	protected void selectCaptain(ArrayList<Player> clientsRemaining, 
			ArrayList<GameCharacter> listOfCharacters) {

		ArrayList<Player> playersWhoSelectedCaptain = new ArrayList<>();
		for (Player pl : clientsRemaining) {
			if (pl.checkedJob("Captain")) {
				playersWhoSelectedCaptain.add(pl);
			}
		}

		if (playersWhoSelectedCaptain.size() == 0) {
			playersWhoSelectedCaptain.addAll(clientsRemaining);
		}

		Player capCl = playersWhoSelectedCaptain.remove(MyRandom.nextInt(playersWhoSelectedCaptain.size()));
		
		clientsRemaining.remove(capCl);
		GameCharacter gc = null;
		for (GameCharacter ch : listOfCharacters) {
			if (ch.getBaseName().equals("Captain")) {
				capCl.setCharacter(ch);
				gc = ch;
				break;
			}
		}
		capCl.putOnSuit(new CaptainsHat());
		listOfCharacters.remove(gc);
	}

	protected void assignRestRoles(ArrayList<Player> remainingPlayers,
			ArrayList<GameCharacter> remainingCharacters, GameData gameData) {

		Collections.shuffle(remainingPlayers);

		while (remainingPlayers.size() > 0) {
			Player cl = remainingPlayers.remove(0);

			ArrayList<GameCharacter> candidates = new ArrayList<>();
			for (GameCharacter gc : remainingCharacters) {
				if (cl.checkedJob(gc.getBaseName())) {
					candidates.add(gc);
				}
			}
			if (candidates.size() == 0) {
				candidates.addAll(remainingCharacters);
			}

			GameCharacter selected = candidates.remove(MyRandom.nextInt(candidates.size()));

			cl.setCharacter(selected);
			remainingCharacters.remove(selected);
		}
	}

	private void moveCharactersIntoStartingRooms(GameData gameData) {
		for (Player c : gameData.getPlayersAsList()) {
			Room startRoom = c.getCharacter().getStartingRoom(gameData);
			c.moveIntoRoom(startRoom);
			c.setNextMove(c.getPosition().getID());
		}
	}

	private void addNPCs(GameData gameData, List<GameCharacter> remainingChars) {
		NPC cat = new CatNPC(gameData.getRoomForId(20));
		gameData.addNPC(cat);
		
		NPC chimp = new ChimpNPC(gameData.getRoom("Greenhouse"));
		gameData.addNPC(chimp);
		while (MyRandom.nextDouble() < 0.5) {
			gameData.addNPC(new SnakeNPC(gameData.getRoom("Greenhouse")));
		}

		Room TARSRoom;
		do {
			TARSRoom = MyRandom.sample(gameData.getRooms());
		} while (TARSRoom instanceof NukieShipRoom);
		
		NPC tars = new TARSNPC(TARSRoom);
		gameData.addNPC(tars);

        NPC securitron = null;
        try {
            securitron = new SecuritronNPC(gameData.getRoom("Security Station"), gameData);
            gameData.addNPC(securitron);
        } catch (NoSuchThingException e) {
            Logger.log("No crime console found, not adding securitron to station");
        }

	//	testShortestDistance(gameData);

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
			Logger.log("Adding npc " + gc.getBaseName());

		}
	}

	private void testShortestDistance(GameData gameData) {
		// TODO Auto-generated method stub
		List<String> places = new ArrayList<>();
		places.add("Greenhouse");
		places.add("Dorms");
		places.add("Office");
		places.add("Bridge");
		places.add("Generator");
		places.add("Air Lock #2");
		for (String place1 : places) {
			for (String place2 : places) {
			Logger.log("Shortest distance between " + place1 + 
								" and " + place2 + ": " + 
								GameMap.shortestDistance(gameData.getRoom(place1), gameData.getRoom(place2)));
			}
		}
	}

	private void giveCharactersStartingItems(GameData gameData) {
		List<Actor> actors = new ArrayList<Actor>();
		actors.addAll(gameData.getPlayersAsList());
		actors.addAll(gameData.getNPCs());

		for (Actor c : actors) {
			List<GameItem> startingItems = c.getCharacter().getStartingItems();
			Logger.log("Giving starting items to " + c.getPublicName());
			for (GameItem it : startingItems) {
				c.addItem(it, null);
			}		
		}
	}

	private void addItemsToRooms(GameData gameData) {

		Room genRoom = gameData.getRoom("Generator");
		genRoom.addItem(new FireExtinguisher());
		genRoom.addItem(new Tools());

		Room labRoom = gameData.getRoom("Lab");
		labRoom.addItem(new FireExtinguisher());
		labRoom.addItem(new RadiationSuit());

		Room bridge = gameData.getRoom("Bridge");
		bridge.addItem(new FireExtinguisher());


		Room kitchRoom = gameData.getRoom("Kitchen");
		kitchRoom.addItem(new FireExtinguisher());


	}

	private void addRandomItemsToRooms(GameData gameData) {
		while (MyRandom.nextDouble() < 0.5) {
			Room aRoom = MyRandom.sample(gameData.getRooms());
			aRoom.addItem(MyRandom.sample(MyRandom.getItemsWhichAppearRandomly()).clone());
			Logger.log("Added a suprise in " + aRoom.getName());
		}
	}


	public static String getAvailableJobs() {
		StringBuffer res = new StringBuffer();
		List<GameCharacter> list = new ArrayList<>();
		list.addAll(availableChars().values());
		Collections.sort(list, new CharacterSpeedComparator());

		for (GameCharacter gc : list) {
			res.append("p" + gc.getBaseName() + ":");
		}
		res.append("aTraitor:");
		res.append("aHost:");
		res.append("aOperative:");
		res.append("aChangeling");


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
			allParasites.add(parasite);
		}

	}

	public Map<String, Event> getEvents() {
		return events;
	}

	protected List<NPC> getAllParasites() {
		return allParasites;
	}

	public void addFire(Room position) {
		OngoingEvent fires = (OngoingEvent)events.get("fires");
		fires.startNewEvent(position);
	}


}
