package model.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.characters.general.CatCharacter;
import model.characters.general.GameCharacter;
import model.characters.general.ParasiteCharacter;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.InstanceChecker;
import model.map.Room;

public class BananaPeelEvent extends Event {

	private Room room;
	private Actor leftBy;
	private boolean remove = false;
	
	public BananaPeelEvent(Actor leftBy) {
		this.leftBy = leftBy;
		this.room = leftBy.getPosition();
	}

	@Override
	public void apply(GameData gameData) {
		List<Actor> acts = new ArrayList<>();
		acts.addAll(room.getActors());
		Collections.shuffle(acts);
		for (Actor c : acts) {
			if (canSlip(c) && c != leftBy && MyRandom.nextDouble() < 0.75) {
				slipsOnPeel(c, gameData);
				break;
			}
		}
	}

	private boolean canSlip(Actor c) {
		GameCharacter a = c.getCharacter();
		if (!(a instanceof CatCharacter) && 
				!(a instanceof ParasiteCharacter)) {
			return !c.isDead();
		}
		return false;
	}

	private void slipsOnPeel(Actor victim, GameData gameData) {
		victim.setCharacter(new AlterMovement(victim.getCharacter(), "slipped", true, 0));
		for (Actor a : room.getActors()) {
			if (a == victim) {
				victim.addTolastTurnInfo("You slipped on the banana peel!");
			} else {
				a.addTolastTurnInfo(victim.getPublicName() + " slipped on the banana peel!");
			}
		}
		
		gameData.addEvent(new RemoveInstanceLaterEvent(victim, gameData.getRound()+1, 
										0, new InstanceChecker() {
											
											@Override
											public boolean checkInstanceOf(GameCharacter ch) {
												// TODO Auto-generated method stub
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
		return "Peel";
	}
	
	@Override
	public String addYourselfToRoomInfo(Player whosAsking) {
		return "bPeel";
	}
	
	
	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.PHYSICAL_ACTIVITY;
	}

	
}
