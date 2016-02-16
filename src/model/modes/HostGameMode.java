package model.modes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import model.Client;
import model.GameData;
import model.actions.Weapon;
import model.characters.CharacterDecorator;
import model.characters.GameCharacter;
import model.characters.HostCharacter;
import model.map.Room;
import model.objects.HiveObject;

public class HostGameMode extends GameMode {
	
	private Client hostClient;

	@Override
	protected void assignCharactersToPlayers(GameData gameData) {
		ArrayList<Client> listOfClients = new ArrayList<Client>();
		listOfClients.addAll(gameData.getClients());
		
		ArrayList<GameCharacter> listOfCharacters = new ArrayList<>();
		listOfCharacters.addAll(getAllCharacters());
		
		Random random = new Random();
		
		Client capCl = listOfClients.remove(random.nextInt(listOfClients.size()));
		GameCharacter gc = null;
		for (GameCharacter ch : listOfCharacters) {
			if (ch.getName().equals("Captain")) {
				capCl.setCharacter(ch, capCl);
				gc = ch;
				break;
			}
		}
		listOfCharacters.remove(gc);
		
		while (listOfClients.size() > 0) {
			Client cl = listOfClients.remove(0);
			cl.setCharacter(listOfCharacters.remove(random.nextInt(listOfCharacters.size())), cl);
		}
	
		ArrayList<Client> newList = new ArrayList<>();
		newList.addAll(gameData.getClients());
		
		hostClient = newList.remove(random.nextInt(newList.size()));
		
		
		GameCharacter hostInner = hostClient.getCharacter();
		CharacterDecorator host = new HostCharacter(hostInner);
		hostClient.setCharacter(host, hostClient);
	}

	@Override
	protected void setUpOtherStuff(GameData gameData) {
		Random random = new Random();
		
		Room hiveRoom = null;
		boolean hiveInStartingRoom;
		do {
			hiveRoom = gameData.getRooms().get(random.nextInt(gameData.getRooms().size()));
			hiveInStartingRoom = false;
			for (Client c : gameData.getClients()) {
				if (c.getCurrentPositionID() == hiveRoom.getID()) {
					hiveInStartingRoom = true;
					break;
				}
			}
			
		} while (hiveInStartingRoom);
	
		hiveRoom.addObject(new HiveObject("Hive"));
		hostClient.addTolastTurnInfo("You are the host! (Only you know this, so keep it a secret.) The hive is in " + hiveRoom.getName() + "." + " Protect it by killing humans or infecting them, turning them over to your side.");

		for (Client c : gameData.getClients()) {
			c.addItem(new Weapon("Laser pistol", 0.90, 1.0, false));
			c.addItem(new Weapon("Shotgun", 0.90, 1.0, true));
			c.addItem(new Weapon("Revolver", 0.75, 1.0, true));
			c.addItem(new Weapon("Knife", 0.75, 1.0, false));
			c.addItem(new Weapon("Flamer", 0.75, 0.5, false));
			c.addItem(new MedKit("MedKit"));
		}
		
	}

	@Override
	public boolean gameOver(GameData gameData) {
		// TODO Implement this shit
		return false;
	}



}
