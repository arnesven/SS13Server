package model.events;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.characters.decorators.SenseActorsInOtherRoomsDecorator;
import model.characters.general.ChangelingCharacter;
import model.characters.general.GameCharacter;
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
        if (ling.getCharacter().checkInstance((GameCharacter cha) ->  cha instanceof SenseActorsInOtherRoomsDecorator)) {
            ling.removeInstance((GameCharacter cha) -> cha instanceof SenseActorsInOtherRoomsDecorator);
        }
        ling.setCharacter(new SenseActorsInOtherRoomsDecorator(ling.getCharacter(), gameData));
		
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
