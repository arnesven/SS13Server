package model.items.general;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.characters.general.GameCharacter;
import model.characters.crew.*;
import model.events.ExplosiveDamage;
import model.items.NoSuchItemException;
import model.map.Room;
import model.events.Explosion;

public abstract class BombItem extends HidableItem {

	public static final String FOUND_A_BOMB_STRING = "What's this... a bomb!?";

	public BombItem(String string) {
		super(string, 2.0);
	}

	public static String getOperationString() {
		return "fiddled with bomb";
	}
	
	
	
	@Override
	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		if (!this.isHidden()) {
			if (isDemolitionsExpert(whosAsking)) {
				super.addYourselfToRoomInfo(info, whosAsking);
			} else {
				info.add("i" + "Bomb");
			}
		}
	}
	
	@Override
	public String getPublicName(Actor whosAsking) {
		if (isDemolitionsExpert(whosAsking)) {
			return getFullName(whosAsking);
		}
		return "Bomb";
	}

	private boolean isDemolitionsExpert(Actor whosAsking) {
		if (whosAsking != null) { 
			GameCharacter chara = whosAsking.getCharacter();
			return chara instanceof DetectiveCharacter ||
					chara instanceof EngineerCharacter;
		}
		return false;
	}
	
	public void explode(GameData gameData, Actor performingClient) {
		System.out.println("Exploding bomb.");
        Room bombRoom = null;
        try {
            bombRoom = gameData.findRoomForItem(this);
        } catch (NoSuchItemException e) {
           // bombRoom continues to be null;
        }

        Actor currentCarrier = null;
		if (bombRoom == null) {
            try {
                currentCarrier = gameData.findActorForItem(this);
            } catch (NoSuchItemException e) {
                System.out.println(" COULD NOT FIND BOMB, WHERE DID IT GO?!");
                return;
            }
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
		
		for (Object o : bombRoom.getObjects()) {
			if (o instanceof Target) {
				((Target)o).beExposedTo(performingClient, 
						new ExplosiveDamage(2.0));
			}
		}
		
		bombRoom.addToEventsHappened(new Explosion());

	}
	
	@Override
	protected char getIcon() {
		return 'N';
	}
	
	
}
