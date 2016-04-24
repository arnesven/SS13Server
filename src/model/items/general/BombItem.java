package model.items.general;

import java.util.ArrayList;

import graphics.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.Hazard;
import model.characters.general.GameCharacter;
import model.characters.crew.*;
import model.events.ambient.ElectricalFire;
import model.events.ambient.HullBreach;
import model.events.damage.ExplosiveDamage;
import model.items.NoSuchThingException;
import model.map.Room;
import model.events.Explosion;
import util.Logger;
import util.MyRandom;

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
	
	public void explode(final GameData gameData, final Actor performingClient) {
        Logger.log(Logger.INTERESTING,
                    "Exploding bomb.");
        Room bombRoom = null;
        try {
            bombRoom = gameData.findRoomForItem(this);
        } catch (NoSuchThingException e) {
            Logger.log(Logger.INTERESTING,
                    "Bomb was not found in a room.");
           // bombRoom continues to be null;
        }

        Actor currentCarrier = null;
		if (bombRoom == null) {
            try {
                currentCarrier = gameData.findActorForItem(this);
            } catch (NoSuchThingException e) {
                Logger.log(Logger.CRITICAL,
                        " COULD NOT FIND BOMB, WHERE DID IT GO?!");
                return;
            }
            currentCarrier.getItems().remove(this);
			currentCarrier.getCharacter().beExposedTo(performingClient, new ExplosiveDamage(3.0));
			bombRoom = currentCarrier.getPosition();
		} else {
			bombRoom.removeFromRoom(this);
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
        possiblyAddHazards(gameData, bombRoom);

        bombRoom.addToEventsHappened(new Explosion());

	}

    private void possiblyAddHazards(GameData gameData, final Room bombRoom) {
        new Hazard(gameData) {

            @Override
            public void doHazard(GameData gameData) {
                // breach the hull?
                if (MyRandom.nextDouble() < 0.75) {
                    HullBreach hull = ((HullBreach) gameData.getGameMode().getEvents().get("hull breaches"));
                    hull.startNewEvent(bombRoom);
                    Logger.log(Logger.INTERESTING,
                            "breached the hull with bomb!");
                }

                // start a fire?
                if (MyRandom.nextDouble() < 0.25) {
                    ElectricalFire fire = ((ElectricalFire) gameData.getGameMode().getEvents().get("fires"));
                    fire.startNewEvent(bombRoom);
                    Logger.log(Logger.INTERESTING,
                            "started a fire with bomb!");
                }
            }

        };
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("bombitem", "assemblies.png", 43);
    }
}
