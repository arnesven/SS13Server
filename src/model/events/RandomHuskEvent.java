package model.events;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.SensoryLevel;
import model.characters.GameCharacter;
import model.characters.NobodyCharacter;
import model.characters.decorators.HuskDecorator;
import model.map.Room;
import model.npcs.HumanNPC;

public class RandomHuskEvent extends Event {

	@Override
	public void apply(GameData gameData) {
		if (gameData.getRound() < 3) {
			if (MyRandom.nextDouble() < 0.33) {
				 addAHusk(gameData);
			}	
		} else if (MyRandom.nextDouble() < 0.05) {
			 addAHusk(gameData);
		} 
		
	}

	private void addAHusk(GameData gameData) {
		Room roomToAddAHuskIn = null;
		do {
			roomToAddAHuskIn = MyRandom.sample(gameData.getRooms());
		} while (!isOk(roomToAddAHuskIn));
		HumanNPC npc = new HumanNPC(new NobodyCharacter(roomToAddAHuskIn.getID()), 
				roomToAddAHuskIn);
		npc.moveIntoRoom(roomToAddAHuskIn);
		npc.setCharacter(new HuskDecorator(npc.getCharacter()));
		System.out.println("Added a husk in " + roomToAddAHuskIn.getName());
	}

	private boolean isOk(Room roomToAddAHuskIn) {
		for (Actor a : roomToAddAHuskIn.getActors()) {
			if (a instanceof Player) {
				return false;
			}
			if (a.getCharacter().checkInstance(new model.characters.decorators.InstanceChecker() {
				
				@Override
				public boolean checkInstanceOf(GameCharacter ch) {
					return ch instanceof HuskDecorator;
				}
			})) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "";
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}

}
