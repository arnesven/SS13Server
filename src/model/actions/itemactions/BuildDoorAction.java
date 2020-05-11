package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.DoorPartsStack;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.ItemStackDepletedException;
import model.map.Architecture;
import model.map.GameMap;
import model.map.doors.NormalDoor;
import model.map.rooms.Room;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

/**
 * Created by erini02 on 23/11/16.
 */
public class BuildDoorAction extends Action {

    private Architecture arch;
    private Map<Room, Point2D> arc;
    private Room selected;

    public BuildDoorAction(GameData gameData, Actor performingClient) {
        super("Build Door", SensoryLevel.PHYSICAL_ACTIVITY);


        this.arch = null;
        try {
            arch = new Architecture(gameData.getMap(),
                    gameData.getMap().getLevelForRoom(performingClient.getPosition()).getName(),
                    performingClient.getPosition().getZ());
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        arc = arch.getPossibleNewDoors(performingClient.getPosition());

    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        for (Map.Entry<Room, Point2D> pair : arc.entrySet()) {
            opts.addOption("to " + pair.getKey().getName());
        }
        return opts;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "built a new door";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        DoorPartsStack doorParts;
        try {
            doorParts = GameItem.getItemFromActor(performingClient, new DoorPartsStack(0));
        } catch (NoSuchThingException e) {
            performingClient.addTolastTurnInfo("What no NormalDoor Parts?" + failed(gameData, performingClient));
            return;
        }

        try {
            doorParts.subtractFrom(1);
        } catch (ItemStackDepletedException e) {
            performingClient.getItems().remove(doorParts);
        }

        arch.joinRoomsWithDoor(performingClient.getPosition(), selected,
                new NormalDoor(0.0, 0.0, performingClient.getPosition().getID(), selected.getID()));
        performingClient.addTolastTurnInfo("You built a new door to " + selected.getName());

    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Map.Entry<Room, Point2D> pair : arc.entrySet()) {
            if (args.get(0).contains(pair.getKey().getName())) {
                selected = pair.getKey();
            }
        }
    }
}
