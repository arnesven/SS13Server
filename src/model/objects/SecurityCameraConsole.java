package model.objects;

import java.util.ArrayList;

import model.Player;
import model.actions.Action;
import model.actions.SecurityConsoleAction;

public class SecurityCameraConsole extends ElectricalMachinery {
	
	private String chosen;

	public SecurityCameraConsole() {
		super("Security Console");
	}
	
	@Override
	public void addActions(Player cl, ArrayList<Action> at) {
		at.add(new SecurityConsoleAction(this));
	}

	public void setChosen(String chosen) {
		this.chosen = chosen;
	}

	public String getChosen() {
		return chosen;
	}

}
