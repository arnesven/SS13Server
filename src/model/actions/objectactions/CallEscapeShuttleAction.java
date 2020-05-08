package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.items.NoSuchThingException;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.rooms.Room;
import model.map.rooms.ShuttleRoom;
import model.objects.consoles.AIConsole;
import model.objects.consoles.ShuttleControl;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class CallEscapeShuttleAction extends Action {
    private final ShuttleControl console;
    private final GameData gameData;
    private String preferredDockingPoint;

    public CallEscapeShuttleAction(GameData gameData, ShuttleControl console) {
        super("Call Escape Shuttle", SensoryLevel.OPERATE_DEVICE);
        this.console = console;
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "fiddled with " + console.getPublicName(whosAsking);
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        int randTurns = MyRandom.nextInt(4) + 2;
        performingClient.addTolastTurnInfo("You called the escape shuttle. ETA " + randTurns + " turns.");
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("The Escape Shuttle has been called. It will dock at " +
                    preferredDockingPoint + " in " + randTurns + " turns.", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        gameData.addEvent(new EscapeShuttleArrivesEvent(randTurns, gameData.getRound()));
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        this.preferredDockingPoint = args.get(0);
    }

    private class EscapeShuttleArrivesEvent extends Event {
        private final int arrivesInRound;

        public EscapeShuttleArrivesEvent(int randTurns, int round) {
            this.arrivesInRound = randTurns + round;
        }

        @Override
        public void apply(GameData gameData) {
            if (gameData.getRound() != arrivesInRound) {
                return;
            }
            List<DockingPoint> others = new ArrayList<>();
            DockingPoint dockingPoint = null;
            if (gameData.getRound() == arrivesInRound) {
                for (DockingPoint dp : gameData.getMap().getLevel(GameMap.STATION_LEVEL_NAME).getDockingPoints()) {
                    if (dp.getName().equals(preferredDockingPoint)) {
                        dockingPoint = dp;
                    }
                    others.add(dp);
                }
            }

            String redirect = "at ";
            if (dockingPoint == null) {
                dockingPoint = MyRandom.sample(others);
                redirect = ", it has been redirected to ";
            }

            try {
                ShuttleRoom shuttle = (ShuttleRoom)gameData.getRoom("Escape Shuttle");
                gameData.getMap().moveRoomToLevel(shuttle, GameMap.STATION_LEVEL_NAME, "center");
                shuttle.dockYourself(gameData, dockingPoint);
                try {
                    gameData.findObjectOfType(AIConsole.class).informOnStation("The Escape Shuttle has arrived on the station " +
                            redirect + shuttle.getDockingPointRoom().getName(), gameData);
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }




        }

        @Override
        public String howYouAppear(Actor performingClient) {
            return null;
        }

        @Override
        public SensoryLevel getSense() {
            return null;
        }

        @Override
        public boolean shouldBeRemoved(GameData gameData) {
            return gameData.getRound() >= arrivesInRound;
        }
    }
}
