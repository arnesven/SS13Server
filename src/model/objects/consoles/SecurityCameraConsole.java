package model.objects.consoles;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.SecurityConsoleAction;
import model.actions.objectactions.SitDownatSecurityConsoleAction;
import model.map.GameMap;
import model.map.rooms.Room;
import model.objects.ai.SecurityCamera;
import model.objects.general.GameObject;

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


	public static List<Room> getConnectedCameraRooms(GameData gameData) {
		List<Room> result = new ArrayList<>();
		for (Room r : gameData.getMap().getRoomsForLevel(GameMap.STATION_LEVEL_NAME)) {
			for (GameObject obj : r.getObjects()) {
				if (obj instanceof SecurityCamera) {
					if (((SecurityCamera) obj).isPowered() && !((SecurityCamera) obj).isBroken()) {
						result.add(r);
						break;
					}
				}
			}
		}
		return result;
	}
}
