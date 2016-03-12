package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.items.GameItem;
import model.items.weapons.Grenade;
import model.map.Room;

public class ThrowGrenadeAction extends Action {

	private String location;
	private Actor thrower;

	public ThrowGrenadeAction(Actor thrower) {
		super("Throw Grenade", SensoryLevel.PHYSICAL_ACTIVITY);
		this.thrower = thrower;
	}

	@Override
	protected String getVerb() {
		return "threw a grenade";
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {

		Grenade grenade = null;
		for (GameItem gi : performingClient.getItems()) {
			if (gi.getName().equals("Grenade")) {
				grenade = (Grenade)gi;
			}
		}
		if (grenade == null) {
			performingClient.addTolastTurnInfo("Your grenade is gone!");
		} else {
			performingClient.getItems().remove(grenade);
			performingClient.addTolastTurnInfo("You threw the grenade into " + location + ".");
			Room targetRoom = gameData.getRoom(location);
			targetRoom.addItem(grenade);
			Action a = new ExplosionAction(grenade, targetRoom);
			a.doTheAction(gameData, performingClient);
			
		}
	}

	@Override
	public String toString() {
		StringBuffer locs = new StringBuffer();
		locs.append(thrower.getPosition().getName() + "{}");
		for (Room r : thrower.getPosition().getNeighborList()) {
			locs.append(r.getName() + "{}");
		}
		
		return this.getName() + "{"+ locs.toString() + "}";
	}

	@Override
	public void setArguments(List<String> args) {	
		location = args.get(0);
	}

}