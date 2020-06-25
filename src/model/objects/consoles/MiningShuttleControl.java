package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtPowerConsoleAction;
import model.actions.general.Action;
import model.actions.objectactions.CallEscapeShuttleAction;
import model.actions.objectactions.MiningShuttleAction;
import model.actions.objectactions.SitDownAtShuttleConsoleAction;
import model.map.doors.Door;
import model.map.rooms.BridgeRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningShuttleControl extends ShuttleControlConsole {
    private final boolean hasAdvanced;

    public MiningShuttleControl(Room room, boolean hasAdvanced) {
        super("Shuttle Control", room);
        this.hasAdvanced = hasAdvanced;
    }

    @Override
    protected List<Action> getNormalActions(GameData gameData, Actor cl) {
        List<Action> at = new ArrayList<>();
        at.add(new MiningShuttleAction(gameData, this));
        if (CallEscapeShuttleAction.canCallEscapeShuttle(cl)) {
            at.add(new CallEscapeShuttleAction(gameData, this));
        }
        return at;
    }

    @Override
    protected Action getSitDownAction(GameData gameData, ShuttleControlConsole shuttleControlConsole, Player cl) {
        return new SitDownAtShuttleConsoleAction(gameData, this, (Player)cl, hasAdvanced);

    }
}
