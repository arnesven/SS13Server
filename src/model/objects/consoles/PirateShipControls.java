package model.objects.consoles;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.actions.objectactions.MovePirateShipAction;
import model.actions.objectactions.MoveShuttleAction;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.ShuttleControlFancyFrame;
import model.map.GameMap;
import model.map.MapLevel;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class PirateShipControls extends ShuttleControlConsole {
    public PirateShipControls(Room r) {
        super("Ship Controls", r, r.getName(), "new algiers", GameMap.STATION_LEVEL_NAME);
    }

    @Override
    protected List<Action> getNormalActions(GameData gameData, Actor cl) {
        List<Action> at = new ArrayList<>();
        at.add(new MovePirateShipAction(gameData, this));
        return at;
    }

    @Override
    protected Action getSitDownAction(GameData gameData, ShuttleControlConsole shuttleControlConsole, Player cl) {
        return new SitDownAtConsoleAction(gameData, shuttleControlConsole, cl) {
            @Override
            protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
                return new ShuttleControlFancyFrame(shuttleControlConsole, gameData, cl, false);
            }
        };
    }

    @Override
    public MoveShuttleAction getCorrespondingAction(GameData gameData, Player player) {
        return new MovePirateShipAction(gameData, this);
    }
}
