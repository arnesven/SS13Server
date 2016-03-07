package model.objects;

import java.util.ArrayList;

import model.Player;
import model.actions.Action;
import model.actions.SecurityConsoleAction;

public class SecurityCameraConsole extends BreakableObject {

	public SecurityCameraConsole() {
		super("Security Console", 1.5);
	}
	
	@Override
	public void addSpecificActionsFor(Player cl, ArrayList<Action> at) {
		at.add(new SecurityConsoleAction());
	}

}
