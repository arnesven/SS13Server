package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.ActionOption;
import model.actions.SensoryLevel;
import model.items.GameItem;
import model.items.Grenade;
import model.map.Room;

public class ThrowGrenadeAction extends Action {

	private String location;
	private Actor thrower;

	public ThrowGrenadeAction(Actor thrower) {
		super("Throw Grenade", SensoryLevel.PHYSICAL_ACTIVITY);
		this.thrower = thrower;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "threw a grenade";
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {

		Grenade grenade = null;
		for (GameItem gi : performingClient.getItems()) {
			if (gi instanceof Grenade) {
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
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = new ActionOption(this.getName());
		
		for (Room r : thrower.getPosition().getNeighborList()) {
			opt.addOption(r.getName());
		}
		opt.addOption("This room");
		
		return opt;
	}

	@Override
	public void setArguments(List<String> args, Actor p) {
		if (args.get(0).equals("This room")) {
			location = p.getPosition().getName();
		} else {
			location = args.get(0);
		}
	}

}