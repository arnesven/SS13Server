package model.events;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.InstanceChecker;
import model.map.Room;

public class FallDownEvent extends Event {

	Actor target;
	Room room;
	boolean remove = false;
	
	public FallDownEvent(Actor target) {
		this.target = target;
		this.room = target.getPosition();
	}
	
	@Override
	public void apply(GameData gameData) {
		if(target.isDead()) {
			this.remove = true;
			room.removeEvent(this);
			return;
		}
		target.setCharacter(new AlterMovement(target.getCharacter(), "fallen", true, 0));
		for (Actor a : room.getActors()) {
			if (a == target) {
				target.addTolastTurnInfo("You stumbled and fell down!");
			} else {
				target.addTolastTurnInfo(target.getPublicName() + " stumbled and fell down!");
			}
		}
		
		gameData.addEvent(new RemoveInstanceLaterEvent(target, gameData.getRound()+1, 
				0, new InstanceChecker() {
					
					@Override
					public boolean checkInstanceOf(GameCharacter ch) {
						return ch instanceof AlterMovement;
					}
				}));
		room.removeEvent(this);
		this.remove = true;
		
	}
	
	@Override
	public boolean shouldBeRemoved(GameData gameData) {
		return remove;
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "Fallen";
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.PHYSICAL_ACTIVITY;
	}

}
