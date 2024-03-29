package model.actions.characteractions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.events.NukeSetByOperativesEvent;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.NuclearDisc;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import model.objects.general.NuclearBomb;
import util.MyRandom;

@Deprecated
public class EscapeAndSetNukeAction extends Action {


	private EscapeAndSetNukeAction() {
		super("Return to Your Ship and Activate Nuke", SensoryLevel.PHYSICAL_ACTIVITY);
	}
		
	
	@Override
	public void setArguments(List<String> args, Actor performingClient) { }

	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
        try {
            final Room nukieShip = gameData.getRoom("Nuclear Ship");
            performingClient.moveIntoRoom(nukieShip);
            if (performingClient instanceof Player) {
                ((Player) performingClient).setNextMove(nukieShip.getID());
            }
            if (hasTheDisk(performingClient) != null) {
                performingClient.getItems().remove(hasTheDisk(performingClient));
                NuclearBomb nuke = null;
                for (GameObject ob : nukieShip.getObjects()) {
                    if (ob instanceof NuclearBomb) {
                        nuke = (NuclearBomb) ob;
                        gameData.addEvent(new NukeSetByOperativesEvent(nuke));
                        break;
                    }
                }
                performingClient.addTolastTurnInfo("You activated the nuke and sent it to SS13.");
                nukieShip.getObjects().remove(nuke);
                MyRandom.sample(gameData.getNonHiddenStationRooms()).addObject(nuke);


            } else {
                performingClient.addTolastTurnInfo("What? The nuclear disk is gone! Your action failed.");
            }
        } catch (NoSuchThingException nste) {
            nste.printStackTrace();
        }
	}

	private NuclearDisc hasTheDisk(Actor performingClient) {
		for (GameItem it : performingClient.getItems()) {
			if (it instanceof NuclearDisc) {
				return (NuclearDisc) it;
			}
		}
		return null;
	}


	@Override
	protected String getVerb(Actor whosAsking) {
		return "Left the station!";
	}



}
