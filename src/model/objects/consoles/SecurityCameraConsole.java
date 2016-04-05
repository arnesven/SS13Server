package model.objects.consoles;

import java.util.ArrayList;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.objectactions.SecurityConsoleAction;
import model.map.Room;

public class SecurityCameraConsole extends Console {
	
	private String chosen;

	public SecurityCameraConsole(Room pos) {
		super("Security Cameras", pos);
	}
	
	@Override
	public void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		at.add(new SecurityConsoleAction(this));
	}

	public void setChosen(String chosen) {
		this.chosen = chosen;
	}

	public String getChosen() {
		return chosen;
	}

}
