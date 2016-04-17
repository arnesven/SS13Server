package model.items.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.HazardAction;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.crew.*;
import model.characters.general.NobodyCharacter;
import model.events.ambient.ElectricalFire;
import model.events.ambient.HullBreach;
import model.events.damage.ExplosiveDamage;
import model.items.NoSuchItemException;
import model.map.Room;
import model.events.Explosion;
import model.npcs.HumanNPC;
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
		System.out.println("Exploding bomb.");
        Room bombRoom = null;
        try {
            bombRoom = gameData.findRoomForItem(this);
        } catch (NoSuchItemException e) {
            System.out.println("Bomb was not found in a room.");
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
        gameData.executeAtEndOfRound(new HazardAction() {

            @Override
            public void doHazard(GameData gameData) {
                // breach the hull?
                if (MyRandom.nextDouble() < 0.75) {
                    HullBreach hull = ((HullBreach) gameData.getGameMode().getEvents().get("hull breaches"));
                    hull.startNewEvent(bombRoom);
                    System.out.println("breached the hull with bomb!");
                }

                // start a fire?
                if (MyRandom.nextDouble() < 0.25) {
                    ElectricalFire fire = ((ElectricalFire) gameData.getGameMode().getEvents().get("fires"));
                    fire.startNewEvent(bombRoom);
                    System.out.println("started a fire with bomb!");
                }
            }

        });
    }

    @Override
	protected char getIcon() {
		return 'N';
	}
	
	
}
