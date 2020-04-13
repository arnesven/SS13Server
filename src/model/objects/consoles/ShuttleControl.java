package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtPowerConsoleAction;
import model.actions.general.Action;
import model.actions.objectactions.MiningShuttleAction;
import model.actions.objectactions.SitDownAtShuttleConsoleAction;
import model.map.doors.Door;
import model.map.rooms.BridgeRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.util.ArrayList;

/**
 * Created by erini02 on 17/09/17.
 */
public class ShuttleControl extends Console {
    private Door oldDoor = null;

    public ShuttleControl(Room room) {
        super("Shuttle Control", room);
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new MiningShuttleAction(gameData, this));
        at.add(new SitDownAtShuttleConsoleAction(gameData, this));
    }

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("shuttlecontrol", "computer2.png", 0, 23, this);
    }

    public void setOldDoor(Door oldDoor) {
        this.oldDoor = oldDoor;
    }

    public Door getOldDoor() {
        return oldDoor;
    }
}
