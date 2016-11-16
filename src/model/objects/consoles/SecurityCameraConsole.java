package model.objects.consoles;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.objectactions.SecurityConsoleAction;
import model.map.Room;

public class SecurityCameraConsole extends Console {
	
	private String chosen;

	public SecurityCameraConsole(Room pos) {
		super("Security Cameras", pos);
	}
	
	@Override
	public void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		at.add(new SecurityConsoleAction(this));
	}

	public void setChosen(String chosen) {
		this.chosen = chosen;
	}

	public String getChosen() {
		return chosen;
	}

}
