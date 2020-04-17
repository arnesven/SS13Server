package model.objects.consoles;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.SecurityConsoleAction;
import model.actions.objectactions.SitDownatSecurityConsoleAction;
import model.map.rooms.Room;

public class SecurityCameraConsole extends Console {
	
	private String chosen;

	public SecurityCameraConsole(Room pos) {
		super("Security Cameras", pos);
	}
	
	@Override
	public void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		at.add(new SecurityConsoleAction(this));
		if (cl instanceof Player) {
			at.add(new SitDownatSecurityConsoleAction(gameData, this));
		}
	}

	public void setChosen(String chosen) {
		this.chosen = chosen;
	}

	public String getChosen() {
		return chosen;
	}

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("securitycamerconsole", "computer2.png", 17, 12, this);
    }

}
