package model.items;

import java.util.ArrayList;


import model.Actor;
import model.GameData;
import model.Player;
import model.characters.GameCharacter;
import model.characters.crew.*;
import model.events.ExplosiveDamage;
import model.map.Room;
import model.events.Explosion;

public class BombItem extends GameItem {

	public BombItem(String string, double weight) {
		super(string, weight);
	}

	public static String getOperationString() {
		return "fiddled with bomb";
	}
	
	@Override
	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		if (isDemolitionsExpert(whosAsking)) {
			super.addYourselfToRoomInfo(info, whosAsking);
		} else {
			info.add("i" + "Bomb");
		}
	}

	private boolean isDemolitionsExpert(Player whosAsking) {
		GameCharacter chara = whosAsking.getCharacter();
		return chara instanceof DetectiveCharacter ||
				chara instanceof EngineerCharacter;
	}
	
	public void explode(GameData gameData, Actor performingClient) {
		System.out.println("Exploding bomb.");
		Room bombRoom = gameData.findRoomForItem(this);
		Actor currentCarrier = null;
		if (bombRoom == null) {
			currentCarrier = gameData.findActorForItem(this);
			currentCarrier.getItems().remove(this);
			currentCarrier.getCharacter().beExposedTo(performingClient, new ExplosiveDamage(3.0));
			bombRoom = currentCarrier.getPosition();
			
		} else {
			bombRoom.getItems().remove(this);
		}
		
		for (Actor a : bombRoom.getActors()) {
			if (a != currentCarrier) {
				a.getCharacter().beExposedTo(performingClient, 
						new ExplosiveDamage(2.0));
			}
		}
		
		bombRoom.addToEventsHappened(new Explosion());

	}
	
	
}
