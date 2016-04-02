package model.objects;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.map.Room;
import model.actions.SensoryLevel;
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
