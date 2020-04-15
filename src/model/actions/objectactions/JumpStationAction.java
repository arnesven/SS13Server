package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.BaseStarRoom;
import model.map.rooms.Room;
import model.objects.consoles.AIConsole;
import model.objects.consoles.FTLControl;
import util.Logger;
import util.MyRandom;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JumpStationAction extends Action {

    private FTLControl ftlControl;

    public JumpStationAction(FTLControl ftlControl) {
        super("FTL-Jump Station", SensoryLevel.OPERATE_DEVICE);
        this.ftlControl = ftlControl;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with FT console";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        ftlControl.setSpunUp(false);
        Collection<Integer[]> emptyPlaces = gameData.getMap().getEmptyQuandrants();
        if (emptyPlaces.isEmpty()) {
            try {
                gameData.findObjectOfType(AIConsole.class).informOnStation("FTL jump failed.", gameData);
                performingClient.addTolastTurnInfo("FTL jump failed.");
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
            return;
        }

        Integer[] newCoordinates = MyRandom.sample(emptyPlaces);
        Integer[] oldCoordinates = gameData.getMap().getPositionForLevel(GameMap.STATION_LEVEL_NAME);
        gameData.getMap().swapLevels(GameMap.STATION_LEVEL_NAME, gameData.getMap().getLevelForCoordinates(newCoordinates, gameData));

        String message = "Jump complete. New coordinates for SS13 are " +
                (newCoordinates[0]*100 + MyRandom.nextInt(100)) + "-" +
                (newCoordinates[1]*100 + MyRandom.nextInt(100)) + "-" +
                (newCoordinates[2]*100 + MyRandom.nextInt(100)) + ".";
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation(message, gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        performingClient.addTolastTurnInfo(message);

        Set<Room> baseStarRooms = new HashSet<>();
        for (Room r : gameData.getAllRooms()) {
            if (r instanceof BaseStarRoom) {
                baseStarRooms.add(r);
            }
        }

        for (Room r : baseStarRooms) {
            try {
                gameData.getMap().removeRoom(r);
                gameData.getMap().addRoom(r, gameData.getMap().getLevelForCoordinates(oldCoordinates, gameData), "space");
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
        Logger.log("Old coords were: " + oldCoordinates);

    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
