package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.decorators.*;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.fancyframe.SinglePageFancyFrame;
import model.items.NoSuchThingException;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.rooms.EscapeShuttle;
import model.map.rooms.Room;
import model.map.rooms.ShuttleRoom;
import model.map.rooms.StationRoom;
import model.npcs.NPC;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.GoTowardsEscapeShuttle;
import model.npcs.behaviors.StayBehavior;
import model.objects.consoles.AIConsole;
import model.objects.consoles.ShuttleControl;
import util.HTMLText;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class CallEscapeShuttleAction extends Action {
    private static final int SHUTTLE_AUTO_LEAVE_ROUNDS = 4;
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
        int randTurns = MyRandom.nextInt(3) + 2;
        performingClient.addTolastTurnInfo("You called the escape shuttle. ETA " + randTurns + " turns.");
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("The Escape Shuttle has been called. It will dock at " +
                    preferredDockingPoint + " in " + randTurns + " turns.", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        gameData.addEvent(new EscapeShuttleArrivesEvent(randTurns, gameData.getRound()));
        makeNPCCrewGoTowardsShuttle(gameData, preferredDockingPoint);
    }

    private void makeNPCCrewGoTowardsShuttle(GameData gameData, String preferredDockingPoint) {
        for (Room r : gameData.getNonHiddenStationRooms()) {
            for (NPC npc : r.getNPCs()) {
                if (npc.isCrew()) {
                    npc.setMoveBehavior(new GoTowardsEscapeShuttle(findPreferredDockingPoint(new ArrayList<>()), gameData));
                }
            }
        }

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
            if (gameData.getRound() == arrivesInRound) {
                List<DockingPoint> others = new ArrayList<>();
                DockingPoint dockingPoint = findPreferredDockingPoint(others);

                String redirect = "at ";
                if (dockingPoint == null) {
                    dockingPoint = MyRandom.sample(others);
                    redirect = ", it has been redirected to ";
                }

                try {
                    EscapeShuttle shuttle = (EscapeShuttle) gameData.getRoom("Escape Shuttle");
                    shuttle.setHasArrived(true);
                    gameData.getMap().moveRoomToLevel(shuttle, GameMap.STATION_LEVEL_NAME, "center");
                    shuttle.dockYourself(gameData, dockingPoint);
                    try {
                        gameData.findObjectOfType(AIConsole.class).informOnStation("The Escape Shuttle has arrived on the station " +
                                redirect + shuttle.getDockingPointRoom().getName(), gameData);
                        gameData.addEvent(new EscapeShuttleLeavingEvent(gameData.getRound(), shuttle));
                    } catch (NoSuchThingException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
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

    private DockingPoint findPreferredDockingPoint(List<DockingPoint> others) {
        DockingPoint dockingPoint = null;
            for (DockingPoint dp : gameData.getMap().getLevel(GameMap.STATION_LEVEL_NAME).getDockingPoints()) {
                if (dp.getName().equals(preferredDockingPoint)) {
                    dockingPoint = dp;
                }
                others.add(dp);
            }

        return dockingPoint;
    }

    private class EscapeShuttleLeavingEvent extends Event {
        private final int roundStarted;
        private final EscapeShuttle shuttle;

        public EscapeShuttleLeavingEvent(int round, EscapeShuttle shuttle) {
            this.roundStarted = round;
            this.shuttle = shuttle;
        }

        @Override
        public void apply(GameData gameData) {
            if (gameData.getRound() == roundStarted + SHUTTLE_AUTO_LEAVE_ROUNDS) {
               shuttle.leaveNow(gameData);
            } else if (gameData.getRound() > roundStarted) {
                StringBuilder missingCrew = new StringBuilder();

                for (Actor a : gameData.getActors()) {
                    if (a.isCrew() && !a.isDead() && a.getPosition() instanceof StationRoom && !shuttle.getActors().contains(a)) {
                        missingCrew.append(", " + a.getBaseName());
                    }
                }

                try {
                    gameData.findObjectOfType(AIConsole.class).informOnStation("Paging " +
                            missingCrew.toString().replaceFirst(", ", "") + "! " +
                            "The Escape Shuttle is leaving soon! ", gameData);
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public boolean shouldBeRemoved(GameData gameData) {
            return gameData.getRound() == roundStarted + SHUTTLE_AUTO_LEAVE_ROUNDS || shuttle.hasLeft();
        }

        @Override
        public String howYouAppear(Actor performingClient) {
            return null;
        }

        @Override
        public SensoryLevel getSense() {
            return null;
        }
    }


}
