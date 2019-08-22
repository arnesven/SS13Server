package model.events.ambient;

import model.events.Event;
import util.Logger;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.general.NobodyCharacter;
import model.characters.decorators.HuskDecorator;
import model.map.rooms.Room;
import model.npcs.HumanNPC;

public class RandomHuskEvent extends Event {

	@Override
	public void apply(GameData gameData) {
		if (gameData.getRound() < 3) {
			if (MyRandom.nextDouble() < 0.33) {
				 addAHusk(gameData);
			}	
		} else if (MyRandom.nextDouble() < 0.025) {
			 addAHusk(gameData);
		} 
		
	}

	private void addAHusk(GameData gameData) {
		Room roomToAddAHuskIn;
		do {
			roomToAddAHuskIn = MyRandom.sample(gameData.getRooms());
		} while (!isOk(roomToAddAHuskIn));
		HumanNPC npc = new HumanNPC(new NobodyCharacter(roomToAddAHuskIn.getID()), 
				roomToAddAHuskIn);
		npc.setHealth(0.0);
		npc.moveIntoRoom(roomToAddAHuskIn);
		npc.setCharacter(new HuskDecorator(npc.getCharacter()));
        Logger.log(Logger.INTERESTING,
                "Added a husk in " + roomToAddAHuskIn.getName());
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
