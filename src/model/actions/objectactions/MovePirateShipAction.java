package model.actions.objectactions;

import model.GameData;
import model.map.GameMap;
import model.objects.consoles.ShuttleControlConsole;

public class MovePirateShipAction extends MoveShuttleAction {
    public MovePirateShipAction(GameData gameData, ShuttleControlConsole shuttleControl) {
        super("Move Pirate Ship", gameData, shuttleControl);
    }
}
