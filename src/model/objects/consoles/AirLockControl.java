package model.objects.consoles;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.map.Room;
import model.actions.objectactions.AirlockOverrideAction;

public class AirLockControl extends Console {

	public AirLockControl(Room pos) {
		super("Airlock Override", pos);
	}

	@Override
	protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		at.add(new AirlockOverrideAction(gameData));
	}
	

}
