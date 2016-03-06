package model.map;

import java.util.ArrayList;

import model.Player;
import model.actions.Action;
import model.actions.SecurityConsoleAction;
import model.objects.BreakableObject;
import model.objects.GameObject;

public class SecurityCameraConsole extends BreakableObject {

	public SecurityCameraConsole() {
		super("Security Consol", 1.5);
	}
	
	@Override
	public void addSpecificActionsFor(Player cl, ArrayList<Action> at) {
		at.add(new SecurityConsoleAction());
	}

}
