package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.MiningShuttleAction;
import model.map.rooms.BridgeRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.util.ArrayList;

/**
 * Created by erini02 on 17/09/17.
 */
public class ShuttleControl extends Console {
    public ShuttleControl(Room room) {
        super("Shuttle Control", room);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new MiningShuttleAction(gameData));
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("shuttlecontrol", "computer2.png", 0, 23);
    }
}
