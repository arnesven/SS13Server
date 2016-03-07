package model.objects;

import java.util.ArrayList;

import model.Player;
import model.actions.Action;
import model.actions.SecurityConsoleAction;

public class SecurityCameraConsole extends ElectricalMachinery {

	public SecurityCameraConsole() {
		super("Security Console");
	}
	
	@Override
	public void addActions(Player cl, ArrayList<Action> at) {
		at.add(new SecurityConsoleAction());
	}

}
