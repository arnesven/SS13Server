package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.events.timed.TimedEvent;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.BaseStarRoom;
import model.map.rooms.OrbitalPlanetRoom;
import model.map.rooms.Room;
import model.objects.consoles.AIConsole;
import model.objects.consoles.FTLControl;
import sounds.Sound;
import util.Logger;
import util.MyRandom;

import java.util.*;

public class JumpStationAction extends Action {

    private FTLControl ftlControl;
    private Integer[] destination;

    public JumpStationAction(FTLControl ftlControl) {
        super("FTL-Jump Station", SensoryLevel.OPERATE_DEVICE);
        this.ftlControl = ftlControl;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with FT console";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (Integer[] destinations : gameData.getMap().getJumpableToLevels()) {
            opts.addOption(destinations[0]+ "??-" + destinations[1] + "??-" + destinations[2] + "??");
        }
        return opts;
    }


    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        String[] parts = args.get(0).split("-");
        this.destination = new Integer[]{Integer.parseInt(parts[0].replace("??","")),
                Integer.parseInt(parts[1].replace("??", "")),
                Integer.parseInt(parts[2].replace("??",""))};
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        ftlControl.setSpunUp(false);
        Integer[] newCoordinates = destination;
        Integer[] oldCoordinates = gameData.getMap().getPositionForLevel(GameMap.STATION_LEVEL_NAME);
        jumpToLevel(gameData, oldCoordinates, gameData.getMap().getLevelForCoordinates(newCoordinates, gameData));
        String oldBackground = gameData.getMap().getLevel(GameMap.STATION_LEVEL_NAME).getBackgroundType();
        gameData.addTimedEvent(new FTLTimedEvent(newCoordinates, oldBackground));
        setSwooshBackground(gameData);
        performingClient.addTolastTurnInfo("You FTL-jumped the station!");
        Logger.log("Old coords were: " + oldCoordinates);
        for (Player p : gameData.getPlayersAsList()) {
            p.getSoundQueue().add(new Sound("hyperspace_begin"));
        }
    }




    private void jumpToLevel(GameData gameData, Integer[] oldCoordinates, String newLevelName) {
        gameData.getMap().swapLevels(GameMap.STATION_LEVEL_NAME, newLevelName);
        String bgType = gameData.getMap().getLevel(newLevelName).getBackgroundType();
        Logger.log("Jumping! new background is + " + bgType + " (from level " + newLevelName);
        gameData.getMap().getLevel(GameMap.STATION_LEVEL_NAME).setBackgroundType(bgType);
        List<Room> movedRooms = movePlanetsFromOtherLevel(gameData, oldCoordinates);
        movePlanetsToOtherLevel(gameData, oldCoordinates, movedRooms);
        removeBaseStars(gameData, oldCoordinates);
    }

    private void setSwooshBackground(GameData gameData) {
       // if (MyRandom.nextBoolean()) {
       //     gameData.getMap().getLevel(GameMap.STATION_LEVEL_NAME).setBackgroundType("Swooshhorizontal");
       // } else {
            gameData.getMap().getLevel(GameMap.STATION_LEVEL_NAME).setBackgroundType("Swooshvertical");
       // }
    }

    private void movePlanetsToOtherLevel(GameData gameData, Integer[] oldCoordinates, List<Room> movedRooms) {
        List<Room> roomsToMove = new ArrayList<>();
        roomsToMove.addAll(gameData.getMap().getRoomsForLevel(GameMap.STATION_LEVEL_NAME));
        roomsToMove.removeIf((Room r) -> !(r instanceof OrbitalPlanetRoom) || movedRooms.contains(r));
        for (Room r : roomsToMove) {
            gameData.getMap().moveRoomToLevel(r, gameData.getMap().getLevelForCoordinates(oldCoordinates, gameData), "Space");
        }
    }

    private List<Room> movePlanetsFromOtherLevel(GameData gameData, Integer[] oldCoordinates) {
        List<Room> roomsToMove = new ArrayList<>();
        roomsToMove.addAll(gameData.getMap().getRoomsForLevel(gameData.getMap().getLevelForCoordinates(oldCoordinates, gameData)));
        for (Room r : roomsToMove) {
            gameData.getMap().moveRoomToLevel(r, GameMap.STATION_LEVEL_NAME, "Space");
        }
        return roomsToMove;
    }

    private void removeBaseStars(GameData gameData, Integer[] oldCoordinates) {
        Set<Room> baseStarRooms = new HashSet<>();
        for (Room r : gameData.getAllRooms()) {
            if (r instanceof BaseStarRoom) {
                baseStarRooms.add(r);
            }
        }

        for (Room r : baseStarRooms) {
            //gameData.getMap().removeRoom(r);
            r.destroy(gameData);
            gameData.getMap().addRoom(r, gameData.getMap().getLevelForCoordinates(oldCoordinates, gameData), "space");

        }
    }

    private class FTLTimedEvent extends TimedEvent {

        private final Integer[] newCoordinates;
        private final String oldBackground;
        private boolean triggered;

        public FTLTimedEvent(Integer[] newCoordinates, String oldBackground) {
            this.newCoordinates = newCoordinates;
            this.oldBackground = oldBackground;
        }

        @Override
        public void apply(GameData gameData, long timedPassedMS) {
            if (timedPassedMS > 10000) { // 10 seconds
                String message = "Jump complete. New coordinates for SS13 are " +
                        (newCoordinates[0] * 100 + MyRandom.nextInt(100)) + "-" +
                        (newCoordinates[1] * 100 + MyRandom.nextInt(100)) + "-" +
                        (newCoordinates[2] * 100 + MyRandom.nextInt(100)) + ".";
                gameData.getChat().serverInSay(message);
                this.triggered = true;
                gameData.getMap().getLevel(GameMap.STATION_LEVEL_NAME).setBackgroundType(oldBackground);
                for (Player p : gameData.getPlayersAsList()) {
                    p.refreshClientData();
                    p.getSoundQueue().add(new Sound("hyperspace_end"));
                }
            }
        }

        @Override
        public boolean shouldBeRemoved(GameData gameData, long timePassedMS) {
            return triggered;
        }

    }
}
