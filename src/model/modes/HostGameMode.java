package model.modes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import util.MyRandom;
import model.Client;
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
	
	private Client hostClient;
	private String hiveString;
	private HiveObject hive;
	private Room hiveRoom;

	@Override
	protected void assignCharactersToPlayers(GameData gameData) {
		ArrayList<Client> listOfClients = new ArrayList<Client>();
		listOfClients.addAll(gameData.getClients());
		
		ArrayList<GameCharacter> listOfCharacters = new ArrayList<>();
		listOfCharacters.addAll(getAllCharacters());
		
		Client capCl = listOfClients.remove(MyRandom.nextInt(listOfClients.size()));
		GameCharacter gc = null;
		for (GameCharacter ch : listOfCharacters) {
			if (ch.getBaseName().equals("Captain")) {
				capCl.setCharacter(ch);
				gc = ch;
				break;
			}
		}
	
		listOfCharacters.remove(gc);
		
		while (listOfClients.size() > 0) {
			Client cl = listOfClients.remove(0);
			cl.setCharacter(listOfCharacters.remove(MyRandom.nextInt(listOfCharacters.size())));
		}
	
		ArrayList<Client> newList = new ArrayList<>();
		newList.addAll(gameData.getClients());
		
		hostClient = newList.remove(MyRandom.nextInt(newList.size()));
		
		
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
			for (Client c : gameData.getClients()) {
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

		Room genRoom = gameData.getRoom("Generator");
		genRoom.addItem(new Chemicals());
		genRoom.addItem(new Chemicals());
		
		Room labRoom = gameData.getRoom("Lab");
		labRoom.addItem(new Chemicals());
		genRoom.addItem(new Chemicals());
		
		Room sickRoom = gameData.getRoom("Sickbay");
		sickRoom.addItem(new MedKit());
		sickRoom.addItem(new MedKit());
		sickRoom.addItem(new MedKit());
		
		Room dormRoom = gameData.getRoom("Dorms");
		dormRoom.addItem(new MedKit());
	}



	private void addHostStartingMessage(Client cl) {
		hiveString = "The hive is in " + hiveRoom.getName() + ".";
		hostClient.addTolastTurnInfo("You are the host! (Only you know this, so keep it a secret.) " + 
									 hiveString + 
									 " Protect it by killing humans or infecting them, turning them over to your side.");
	}

	
	private void addCrewStartingMessage(Client c) {
		c.addTolastTurnInfo("There is a hive somewhere on the station. You must search the rooms to find and destory it. Beware of the host, it will protect its hive by attacking and infecting the crew.");
	}
	
	@Override
	public boolean gameOver(GameData gameData) {
		return hive.isBroken();
	}

	@Override
	public void setStartingLastTurnInfo() {
		hostClient.addTolastTurnInfo(hiveString);
	}

	

	@Override
	protected void addStartingMessages(GameData gameData) {
		for (Client c : gameData.getClients()) {
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
		}
	}






}
