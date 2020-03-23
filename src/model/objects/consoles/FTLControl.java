package model.objects.consoles;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.BaseStarRoom;
import model.map.rooms.Room;
import util.Logger;
import util.MyRandom;

import java.util.*;

/**
 * Created by erini02 on 07/12/16.
 */
public class FTLControl extends Console {

    private boolean spunUp = false;

    public FTLControl(Room bridge) {
        super("FTL Control", bridge);
        setPowerPriority(1);
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (spunUp) {
            at.add(new JumpStationAction());
        } else {
            at.add(new SpinUpFTLAction());
        }
    }

    @Override
    public double getPowerConsumptionFactor() {
        if (spunUp) {
            return 10.0;
        }
        return super.getPowerConsumptionFactor();
    }

    private class SpinUpFTLAction extends Action {

        public SpinUpFTLAction() {
            super("Spin Up FTL", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Fiddled with FTL console";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            spunUp = true;
            performingClient.addTolastTurnInfo("You spun up the FTL.");
            try {
                gameData.findObjectOfType(AIConsole.class).informOnStation("FTL Spun up - prepare to jump.", gameData);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class JumpStationAction extends Action {

        public JumpStationAction() {
            super("FTL-Jump Station", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Fiddled with FT console";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            spunUp = false;
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
}
