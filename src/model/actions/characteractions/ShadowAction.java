package model.actions.characteractions;

import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.SensoryLevel;
import model.actions.WatchAction;
import model.characters.GameCharacter;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.InstanceRemover;
import model.events.Event;
import model.events.RemoveInstanceLaterEvent;
import model.items.GameItem;
import model.map.Room;

public class ShadowAction extends WatchAction {

	private Room shadowedInRoom;

	public ShadowAction(Player player) {
		super(player);
		super.setName("Shadow");
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "Shadowed";
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			final Actor performingClient, final Target target, 
			GameItem item) {
		super.applyTargetingAction(gameData, performingClient, target, item);
		performingClient.addTolastTurnInfo("You are shadowing " + target.getName());
		this.shadowedInRoom = performingClient.getPosition();
				
		gameData.addMovementEvent(new Event() {
			
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
				if (shadowedInRoom == performingClient.getPosition()) {
					Player pl = (Player)performingClient;
					pl.setNextMove(target.getPosition().getID());
					pl.moveIntoRoom(target.getPosition());
					performingClient.addTolastTurnInfo("You followed " + target.getName() + " to " +target.getPosition().getName() + ".");
				} else {
					performingClient.addTolastTurnInfo("You stopped shadowing " + target.getName() + ".");
					
				}
			}
			
			@Override
			public boolean shouldBeRemoved(GameData gameData) {
				return true;
			}
		});
	}



}
