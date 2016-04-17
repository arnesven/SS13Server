package model.objects.general;

import java.util.ArrayList;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.Room;
import model.objects.consoles.Console;
import model.actions.objectactions.AirlockOverrideAction;

public class AirLockControl extends Console {

	public AirLockControl(Room pos) {
		super("Airlock Override", pos);
	}

	@Override
	protected void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		at.add(new AirlockOverrideAction(gameData));
	}
	

}
