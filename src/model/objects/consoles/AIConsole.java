package model.objects.consoles;

import java.util.ArrayList;

import model.GameData;
import model.Player;
import model.actions.objectactions.AIConsoleAction;
import model.actions.general.Action;
import model.map.Room;

public class AIConsole extends Console {

	public AIConsole(Room pos) {
		super("AI Console", pos);
	}
	
	@Override
	public void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		at.add(new AIConsoleAction(this));
	}

}
