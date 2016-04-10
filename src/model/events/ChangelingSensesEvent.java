package model.events;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.SensoryLevel;
import model.characters.ChangelingCharacter;
import model.map.Room;

public class ChangelingSensesEvent extends Event {

	private Player ling;

	public ChangelingSensesEvent(Player ling) {
		this.ling = ling;
	}

	@Override
	public void apply(GameData gameData) {
		for (Room neig : ling.getPosition().getNeighborList()) {
			String str = "In " + neig.getName() + "; ";
			boolean added = false;
			for (Actor a : neig.getActors()) {
				if (ChangelingCharacter.isDetectable(a.getAsTarget())) {
					str += a.getBaseName() + " ";
					added = true;
				}
			}
			if (added) {
				ling.addTolastTurnInfo(str);
			}
		}
		
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
