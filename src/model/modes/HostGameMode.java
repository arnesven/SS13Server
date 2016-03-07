package model.modes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import util.MyRandom;
import model.Player;
import model.GameData;
import model.characters.CharacterDecorator;
import model.characters.GameCharacter;
import model.characters.HostCharacter;
import model.characters.ParasiteCharacter;
import model.items.Chemicals;
import model.items.KeyCard;
import model.items.MedKit;
import model.items.Weapon;
import model.map.Room;
import model.npcs.AttackIfPossibleBehavior;
import model.npcs.MeanderingMovement;
import model.npcs.NPC;
import model.npcs.ParasiteNPC;
import model.objects.HiveObject;

public class HostGameMode extends GameMode {
	
	public HostGameMode() {
	}



	//private static final int NO_OF_GAME_ROUNDS = 20;
	private Player hostClient;
	private String hiveString;
	private HiveObject hive;
	private Room hiveRoom;
	private ArrayList<NPC> allParasites = new ArrayList<NPC>();

	@Override
	protected List<GameCharacter> assignCharactersToPlayers(GameData gameData) {
		ArrayList<Player> listOfClients = new ArrayList<Player>();
		listOfClients.addAll(gameData.getPlayersAsList());
		ArrayList<GameCharacter> listOfCharacters = new ArrayList<>();
		listOfCharacters.addAll(getAllCharacters());
		
		/// SELECT A CAPTAIN, SS13 MUST ALWAYS HAVE A CAPTAIN
		selectCaptain(listOfClients, listOfCharacters);
				
		/// ASSIGN ROLES RANDOMLY
		assignRestRoles(listOfClients, listOfCharacters);	
		
		// ASSIGN HOST
		assignHost(gameData);
		
		return listOfCharacters;
		
	}


	private void selectCaptain(ArrayList<Player> clientsRemaining, 
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
		listOfCharacters.remove(gc);
	}

	private void assignRestRoles(ArrayList<Player> remainingPlayers,
			ArrayList<GameCharacter> remainingCharacters) {
		
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

	private void assignHost(GameData gameData) {
		ArrayList<Player> playersWhoSelectedHost = new ArrayList<>();
		for (Player pl : gameData.getPlayersAsList()) {
			if (pl.checkedJob("Host")) {
				playersWhoSelectedHost.add(pl);
			}
		}

		if (playersWhoSelectedHost.size() == 0) {
			playersWhoSelectedHost.addAll(gameData.getPlayersAsList());
		}
		
		hostClient = playersWhoSelectedHost.remove(MyRandom.nextInt(playersWhoSelectedHost.size()));
		GameCharacter hostInner = hostClient.getCharacter();
		CharacterDecorator host = new HostCharacter(hostInner);
		hostClient.setCharacter(host);
	}


	@Override
	protected void setUpOtherStuff(GameData gameData) {
		hiveRoom = null;
		boolean hiveInStartingRoom;
		do {
			hiveRoom = gameData.getRooms().get(MyRandom.nextInt(gameData.getRooms().size()));
			hiveInStartingRoom = false;
			for (Player c : gameData.getPlayersAsList()) {
				if (c.getPosition().getID() == hiveRoom.getID()) {
					hiveInStartingRoom = true;
					break;
				}
			}
			
		} while (hiveInStartingRoom);
		hive = new HiveObject("Hive");
		hiveRoom.addObject(hive);
		

		addItemsToRooms(gameData);
		

	}
	
	private void addItemsToRooms(GameData gameData) {

//		Room genRoom = gameData.getRoom("Generator");
//		genRoom.addItem(new Chemicals());
//		genRoom.addItem(new Chemicals());
		
//		Room labRoom = gameData.getRoom("Lab");
//		labRoom.addItem(new Chemicals());
//		genRoom.addItem(new Chemicals());
		
//		Room sickRoom = gameData.getRoom("Sickbay");
//		sickRoom.addItem(new MedKit());
//		sickRoom.addItem(new MedKit());
//		sickRoom.addItem(new MedKit());
		
		Room dormRoom = gameData.getRoom("Dorms");
		dormRoom.addItem(new MedKit());
	}



	private void addHostStartingMessage(Player cl) {
		hiveString = "The hive is in " + hiveRoom.getName() + ".";
		hostClient.addTolastTurnInfo("You are the host! (Only you know this, so keep it a secret.) " + 
									 hiveString + 
									 " Protect it by killing humans or infecting them, turning them over to your side.");
	}

	
	private void addCrewStartingMessage(Player c) {
		c.addTolastTurnInfo("There is a hive somewhere on the station. You must search the rooms to find and destory it. Beware of the host, it will protect its hive by attacking and infecting the crew.");
	}
	
	public enum GameOver {
		HIVE_BROKEN,
		ALL_INFECTED,
		TIME_IS_UP,
		ALL_DEAD
	}
	
	/**
	 * Gets the way the game ended as a GameOver enum.
	 * If the game is not over, null is returned.
	 * @param gameData
	 * @return
	 */
	public GameOver getGameResultType(GameData gameData) {
		if (gameData.isAllDead()) {
			return GameOver.ALL_DEAD;
		}
		if (hive.isBroken()) {
			return GameOver.HIVE_BROKEN;
		} 
		if (allInfected(gameData)) {
			return GameOver.ALL_INFECTED;
		}
		if (gameData.getRound() == gameData.getNoOfRounds()) {
			return GameOver.TIME_IS_UP;
		}
		return null;
	}
	
	@Override
	public boolean gameOver(GameData gameData) {
		return getGameResultType(gameData) != null;
	}

	private boolean allInfected(GameData gameData) {
		for (Player cl : gameData.getPlayersAsList()) {
			if (!cl.isDead() && !cl.isInfected()) {
				return false;
			}
		}
		return true;
	}



	@Override
	public void setStartingLastTurnInfo() {
		hostClient.addTolastTurnInfo(hiveString);
	}

	

	@Override
	protected void addStartingMessages(GameData gameData) {
		for (Player c : gameData.getPlayersAsList()) {
			if (c == hostClient) {
				addHostStartingMessage(c);
			} else {
				addCrewStartingMessage(c);
			}
		}
		
	}



	@Override
	public void triggerEvents(GameData gameData) {
		//possibly spawn some parasites
		
		double PARASITE_SPAWN_CHANCE = 0.75;
		
	
		if (MyRandom.nextDouble() < PARASITE_SPAWN_CHANCE) {
			List<Room> spawnPoints = hiveRoom.getNeighborList();
			spawnPoints.add(hiveRoom);
			
			Room randomRoom = spawnPoints.get(MyRandom.nextInt(spawnPoints.size()));
			
			NPC parasite = new ParasiteNPC(randomRoom);
			
			gameData.addNPC(parasite);
			allParasites.add(parasite);
		}
	}



	@Override
	public String getSummary(GameData gameData) {
		return (new HostModeStats(gameData, this)).toString();
	}



	public Player getHostPlayer() {
		return hostClient;
	}



	public Room getHiveRoom() {
		return hiveRoom;
	}



	public List<NPC> getAllParasites() {
		return allParasites;
	}



	public HiveObject getHive() {
		return hive;
	}






}
