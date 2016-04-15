package model.modes;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.Player;
import model.GameData;
import model.characters.general.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.HostCharacter;
import model.map.Room;
import model.npcs.NPC;
import model.npcs.ParasiteNPC;
import model.objects.general.HiveObject;

public class HostGameMode extends GameMode {
	

	private Player hostClient;
	private String hiveString;
	private HiveObject hive;
	private Room hiveRoom;


	@Override
	protected void assignOtherRoles(ArrayList<GameCharacter> remainingCharacters, GameData gameData) {
		assignHost(gameData);
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
		hive = new HiveObject("Hive", hiveRoom);
		hiveRoom.addObject(hive);
	}






	private void addHostStartingMessage(Player cl) {
		hiveString = "The hive is in " + hiveRoom.getName() + ".";
		hostClient.addTolastTurnInfo("You are the host! (Only you know this, so keep it a secret.) " + 
									 hiveString + 
									 " Protect it by killing humans or infecting them, turning them over to your side.");
	}

	
	private void addCrewStartingMessage(Player c) {
		c.addTolastTurnInfo("There is a hive somewhere on the station. You must search the rooms to find and destroy it. Beware of the host, it will protect its hive by attacking and infecting the crew.");
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
	protected void spawnParasites(GameData gameData) {
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





	public HiveObject getHive() {
		return hive;
	}


	@Override
	protected void addAntagonistStartingMessage(Player c) {
		addHostStartingMessage(c);
	}


	@Override
	protected void addProtagonistStartingMessage(Player c) {
		addCrewStartingMessage(c);
	}


	@Override
	protected boolean isAntagonist(Player c) {
		return c == hostClient;
	}






}
