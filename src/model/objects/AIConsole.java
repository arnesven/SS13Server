package model.objects;

import java.util.ArrayList;

import model.Player;
import model.actions.AIConsoleAction;
import model.actions.Action;

public class AIConsole extends GameObject {

	public AIConsole() {
		super("AI Console");
	}
	
	@Override
	public void addSpecificActionsFor(Player cl, ArrayList<Action> at) {
		at.add(new AIConsoleAction());
	}

}
