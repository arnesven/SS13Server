package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.TeleportAction;
import model.actions.objectactions.ShowTheseCoordinates;
import model.actions.objectactions.TeleportToCoordinatesAction;
import model.items.general.GameItem;
import model.items.general.Teleporter;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/11/16.
 */
public class TeleportConsole extends Console {


    public TeleportConsole(Room r) {
        super("Teletronics", r);

    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("teletronics", "teleporter.png", 1, 2);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        List<Room> markedRooms = getMarkedRooms(gameData);
        if (markedRooms.size() > 0) {
            for (Room r : markedRooms) {
                Teleporter tele = new Teleporter();
                tele.setMarked(r);
                at.add(new TeleportAction(tele));
            }
        }
        at.add(new TeleportToCoordinatesAction());
        at.add(new ShowTheseCoordinates(this, gameData));
    }

    private void addIfSuitable(List<Room> markedRooms, GameItem it) {
        if (it instanceof Teleporter) {
            Room r = ((Teleporter) it).getMarked();
            if (r != null) {
                markedRooms.add(r);
            }
        }
    }

    public List<Room> getMarkedRooms(GameData gameData) {
        List<Room> markedRooms = new ArrayList<>();
        for (Room r : gameData.getAllRooms()) {
            for (GameItem it : r.getItems()) {
                addIfSuitable(markedRooms, it);
            }
            for (Actor a : r.getActors()) {
                for (GameItem it : a.getItems()) {
                    addIfSuitable(markedRooms, it);
                }
            }
        }

//        try {
//            markedRooms.add(gameData.getRoom("Derelict Lab"));
//        } catch (NoSuchThingException e) {
//            e.printStackTrace();
//        }

        return markedRooms;
    }

}
