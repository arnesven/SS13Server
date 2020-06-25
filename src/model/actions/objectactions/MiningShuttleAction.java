package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.rooms.Room;
import model.map.rooms.MiningShuttle;
import model.objects.consoles.MiningShuttleControl;
import util.Logger;

import java.util.List;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningShuttleAction extends MoveShuttleAction {

    public MiningShuttleAction(GameData gameData, MiningShuttleControl shuttleControl) {
        super("Move Mining Shuttle", gameData, shuttleControl);
    }

}
