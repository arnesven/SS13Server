package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.RoomParts;
import model.items.general.Tools;
import model.map.GameMap;
import model.map.Room;
import model.map.RoomType;
import model.objects.consoles.KeyCardLock;

import java.util.List;

/**
 * Created by erini02 on 20/11/16.
 */
public class BuildNewRoomAction extends Action {

    private String selected;

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
        opts.addOption("Aftwards");
        opts.addOption("Forwards");
        opts.addOption("Port");
        opts.addOption("Starboard");
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        RoomParts parts = null;
        try {
            parts = GameItem.getItemFromActor(performingClient, new RoomParts());
        } catch (NoSuchThingException e) {
            performingClient.addTolastTurnInfo("What, no construction parts? " + Action.FAILED_STRING);
            return;
        }

        if (GameItem.hasAnItem(performingClient, new Tools())) {
            String name = buildNewRoom(gameData, performingClient, selected);
            performingClient.addTolastTurnInfo("You built a new room: " + name + "!");
            performingClient.getItems().remove(parts);
        } else {
            performingClient.addTolastTurnInfo("What, no tools? " + Action.FAILED_STRING);
            return;
        }
    }

    private String buildNewRoom(GameData gameData, Actor performingClient, String selected) {
        Room current = performingClient.getPosition();
        int width = 2;
        int height = 1;
        if (selected.equals("Port") || selected.equals("Starboard")) {
            width = 1;
            height = 2;
        }

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
        GameMap.joinRooms(newRoom, current);

        gameData.getMap().addRoom(newRoom);


        return "Annex #" + newRoom.getID();
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        selected = args.get(0);
    }
}
