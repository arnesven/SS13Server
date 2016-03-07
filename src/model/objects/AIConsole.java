package model.objects;

import java.util.ArrayList;

import model.Player;
import model.actions.AIConsoleAction;
import model.actions.Action;

public class AIConsole extends ElectricalMachinery {

	public AIConsole() {
		super("AI Console");
	}
	
	@Override
	public void addActions(Player cl, ArrayList<Action> at) {
		at.add(new AIConsoleAction());
	}

}
