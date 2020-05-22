package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.GrenadeItem;
import model.map.rooms.Room;

public class ThrowGrenadeAction extends Action {

	private final GrenadeItem grenade;
	private String location;
	private Actor thrower;

	public ThrowGrenadeAction(Actor thrower, GrenadeItem gren) {
		super("Throw " + gren.getPublicName(thrower), SensoryLevel.PHYSICAL_ACTIVITY);
		this.thrower = thrower;
		this.grenade = gren;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "threw a grenade";
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (grenade == null) {
			performingClient.addTolastTurnInfo("What? The grenade is gone! Your action failed.");
		} else {
			performingClient.getItems().remove(grenade);
			performingClient.addTolastTurnInfo("You threw the grenade into " + location + ".");
            Room targetRoom = null;
            try {
                targetRoom = gameData.getRoom(location);
                targetRoom.addItem(grenade);
                grenade.doExplosionAction(targetRoom, gameData, performingClient);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }

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