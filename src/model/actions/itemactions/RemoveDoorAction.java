package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.ItemStackDepletedException;
import model.items.general.RoomPartsStack;
import model.map.Architecture;
import model.map.GameMap;
import model.map.rooms.Room;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by erini02 on 24/11/16.
 */
public class RemoveDoorAction extends Action {

    private final Set<Room> doorRooms;
    private Room selected;

    public RemoveDoorAction(Actor actionPerformer) {
        super("Remove NormalDoor", SensoryLevel.NO_SENSE);

        this.doorRooms = findDoorRooms(actionPerformer);
    }

    private Set<Room> findDoorRooms(Actor actionPerformer) {
        Set<Room> set = new HashSet<>();
        for (Room r : actionPerformer.getPosition().getNeighborList()) {
            set.add(r);
        }
        return set;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "removed a door";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt =  super.getOptions(gameData, whosAsking);
        for (Room r : doorRooms) {
            opt.addOption("to " + r.getName());
        }
        return opt;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        RoomPartsStack roomParts;
        try {
            roomParts = GameItem.getItemFromActor(performingClient, new RoomPartsStack(0));
        } catch (NoSuchThingException e) {
            performingClient.addTolastTurnInfo("What, no construction parts? " + Action.FAILED_STRING);
            return;
        }

        try {
            roomParts.subtractFrom(1);
        } catch (ItemStackDepletedException e) {
            performingClient.getItems().remove(roomParts);
        }

        Architecture arc = null;
        try {
            arc = new Architecture(gameData.getMap(), gameData.getMap().getLevelForRoom(performingClient.getPosition()).getName(),
                    performingClient.getPosition().getZ());
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        Point2D doorPos = null;
        try {
            doorPos = arc.getDoorPositionBetween(performingClient.getPosition(), selected);

            performingClient.addTolastTurnInfo("You removed a door to " + selected.getName());
            GameMap.separateRooms(performingClient.getPosition(), selected);

            if (GameMap.hasDoor(performingClient.getPosition(), doorPos.getX(), doorPos.getY())) {
                GameMap.removeDoor(performingClient.getPosition(), doorPos.getX(), doorPos.getY());
            } else {
                GameMap.removeDoor(selected, doorPos.getX(), doorPos.getY());
            }

        } catch (Architecture.DoorNotFoundBetweenRooms doorNotFoundBetweenRooms) {
            doorNotFoundBetweenRooms.printStackTrace();

        }




    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Room r : doorRooms) {
            if (args.get(0).contains(r.getName())) {
                selected = r;
            }
        }
    }
}
