package model.events;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.ChilledDecorator;
import model.map.Room;

public class ColdEvent extends Event {

	private Room room;

	public ColdEvent(Room r) {
		this.room = r;
	}

	@Override
	public void apply(GameData gameData) {
		for (Actor a : room.getActors()) {
			if (!room.hasFire()) {
				if (isChilled(a)) {
					a.getCharacter().beExposedTo(null, new ColdDamage());
				} else {
					makeChilled(a);
					a.addTolastTurnInfo("You are feeling very cold.");
				}
			}
		}

	}

	private boolean isChilled(Actor a) {
		InstanceChecker check = new InstanceChecker(){
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof ChilledDecorator;
			}
		};
		return a.getCharacter().checkInstance(check);
	}

	private void makeChilled(Actor a) {
		a.setCharacter(new ChilledDecorator(a.getCharacter()));	
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "Cold";
	}
	
	@Override
	public String addYourselfToRoomInfo(Player whosAsking) {
		return "cCold";
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}

}
