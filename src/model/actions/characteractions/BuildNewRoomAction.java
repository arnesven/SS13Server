package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.ItemStackDepletedException;
import model.items.general.RoomPartsStack;
import model.items.general.Tools;
import model.map.GameMap;
import model.map.Room;
import model.map.RoomType;

import java.util.List;
import java.util.Scanner;

/**
 * Created by erini02 on 20/11/16.
 */
public class BuildNewRoomAction extends Action {

    private String selected;
    private int height;
    private int width;

    public BuildNewRoomAction() {
        super("Build Room", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "built a new room";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        RoomPartsStack roomParts;
        try {
            roomParts = GameItem.getItemFromActor(whosAsking, new RoomPartsStack(1));
        } catch (NoSuchThingException e) {
            return opts;
        }


        addOptions(opts, "Aftwards", roomParts.getAmount());
        addOptions(opts, "Forwards", roomParts.getAmount());
        addOptions(opts, "Port",     roomParts.getAmount());
        addOptions(opts, "Starboard", roomParts.getAmount());

        return opts;
    }

    private void addOptions(ActionOption opts, String aftwards, int amount) {
        ActionOption opt = new ActionOption(aftwards);

        for (int w = 1; w <= amount; w++) {
            for (int h = 1; h <= amount; h++) {
                if (w*h <= amount) {
                    opt.addOption("size " + w + "x" + h);
                }
            }
        }
        opts.addOption(opt);

    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        RoomPartsStack parts = null;
        int costOfBuild = width * height;
        try {
            parts = GameItem.getItemFromActor(performingClient, new RoomPartsStack(1));
        } catch (NoSuchThingException e) {
            performingClient.addTolastTurnInfo("What, no construction parts? " + Action.FAILED_STRING);
            return;
        }

        if (GameItem.hasAnItem(performingClient, new Tools())) {
            String name = buildNewRoom(gameData, performingClient, selected, width, height);
            performingClient.addTolastTurnInfo("You built a new room: " + name + "!");
            try {
                parts.subtractFrom(costOfBuild);
            } catch (ItemStackDepletedException e) {
                performingClient.getItems().remove(parts);
            }
        } else {
            performingClient.addTolastTurnInfo("What, no tools? " + Action.FAILED_STRING);
            return;
        }
    }

    private String buildNewRoom(GameData gameData, Actor performingClient, String selected, int width, int height) {
        Room current = performingClient.getPosition();

        int x = current.getX();
        int y = current.getY();
        double doorx = x;
        double doory = y;

        if (selected.equals("Port")) {
            y -= height;
            doorx += 0.5;
        } else if (selected.equals("Starboard")) {
            y += current.getHeight();
            doorx += 0.5;
            doory += current.getHeight();
        } else if (selected.equals("Forwards")) {
            x += current.getWidth();
            doorx += current.getWidth();
            doory += 0.5;
        } else if (selected.equals("Aftwards")) {
            x -= width;
            doory += 0.5;
        }

        int id = gameData.getMap().getMaxID()+1;
        int[] neighs = new int[]{};
        double[] doors = new double[]{doorx, doory};
        Room newRoom = new Room(id, "Annex #" + id, "", x, y, width, height, neighs, doors, RoomType.hall);
        newRoom.setMap(gameData.getMap());
        GameMap.joinRooms(newRoom, current);

        gameData.getMap().addRoom(newRoom);


        return "Annex #" + newRoom.getID();
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        selected = args.get(0);
        String[] rest = args.get(1).replace("size ", "").split("x");
        width = Integer.parseInt(rest[0]);
        height = Integer.parseInt(rest[1]);
    }
}
