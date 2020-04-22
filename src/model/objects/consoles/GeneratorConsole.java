package model.objects.consoles;

import java.util.*;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtPowerConsoleAction;
import model.actions.general.Action;
import model.actions.objectactions.PowerConsoleAction;
import model.map.GameMap;
import model.map.rooms.Room;

public class GeneratorConsole extends Console {


    private PowerSource powerSource;

	public GeneratorConsole(Room r, GameData gameData) {
		super("Power Console", r);
        powerSource = new PowerSource(45.0, r, gameData) {
            @Override
            protected List<Room> getAffectedRooms(GameData gameData) {
                List<Room> rooms = new ArrayList<>();
                rooms.addAll(gameData.getRooms());
                rooms.add(gameData.getMap().getSpaceRoomForLevel(GameMap.STATION_LEVEL_NAME));
                return rooms;
            }
        };
        r.addObject(powerSource);
	}

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("powerconsole", "computer2.png", 16, 10, this);
    }


	@Override
	protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		at.add(new PowerConsoleAction(this));
		if (cl instanceof Player) {
            at.add(new SitDownAtPowerConsoleAction(gameData, this));
        }
	}


    public PowerSource getSource() {
        return powerSource;
    }
}
