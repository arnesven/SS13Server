package model.actions.characteractions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.events.Event;
import model.items.GameItem;
import model.items.NuclearDisc;
import model.map.Room;
import model.modes.InfiltrationGameMode;

public class EscapeAndSetNukeAction extends Action {


	public EscapeAndSetNukeAction() {
		super("Leave SS13", SensoryLevel.PHYSICAL_ACTIVITY);
	}
		
	
	@Override
	public void setArguments(List<String> args, Actor performingClient) { }

	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		final Room nukieShip = gameData.getRoom("Nuclear Ship");
		((Player)performingClient).moveIntoRoom(nukieShip);
		((Player)performingClient).setNextMove(nukieShip.getID());
		if (hasTheDisk(performingClient) != null) {
			performingClient.getItems().remove(hasTheDisk(performingClient));
			
			gameData.addEvent(new Event() {
				
				private int roundsLeft = 2;
				
				@Override
				public String howYouAppear(Actor performingClient) {
					return "";
				}
				
				@Override
				public SensoryLevel getSense() {
					return SensoryLevel.NO_SENSE;
				}
				
				@Override
				public void apply(GameData gameData) {
					String tellString = "Detonation in " + roundsLeft + " minutes.";
					
					if (roundsLeft == 2) {
						tellString = "Nuclear warhead detected. " + tellString;
						gameData.setNumberOfRounds(gameData.getNoOfRounds() + 2);
					}
					
					for (Player p : gameData.getPlayersAsList()) {
						p.addTolastTurnInfo("AI; " + tellString);
					}
					
					if (roundsLeft == 0) {
						for (Actor a : gameData.getActors()) {
							if (a.getPosition() != nukieShip) {
								a.getCharacter().beExposedTo(null, new NuclearExplosiveDamage());
							}
						}
						((InfiltrationGameMode)gameData.getGameMode()).setNuked(true);
						gameData.setNumberOfRounds(gameData.getNoOfRounds() - 2);
					}
					
					roundsLeft--;
				}
				
				@Override
				public boolean shouldBeRemoved(GameData gameData) {
					return roundsLeft == -1;
				}
			});
			
			
		} else {
			performingClient.addTolastTurnInfo("What? The nuclear disk is gone! Your action failed.");
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
