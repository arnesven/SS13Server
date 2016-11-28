package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.FrozenDecorator;
import model.characters.decorators.TumblingThroughSpaceDecorator;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.Room;
import model.map.RoomType;
import model.objects.general.AirlockPanel;
import model.objects.general.GameObject;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 24/11/16.
 */
public class MoveToOtherAirlocks extends Action {
    private final Room pos;
    private List<Room> moveableRoomsTo = new ArrayList<>();
    private Room selected;
    private boolean jumpAway = false;

    public MoveToOtherAirlocks(Room position, GameData gameData) {
        super("Go EVA", SensoryLevel.PHYSICAL_ACTIVITY);
        this.pos = position;
        try {
            for (Room r : gameData.getMap().getRoomsForLevel(gameData.getMap().getLevelForRoom(position))) {
                for (GameObject obj : r.getObjects()) {
                    if (obj instanceof AirlockPanel && r != pos) {
                        moveableRoomsTo.add(r);
                        break;
                    }
                }
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "went out through the airlock";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts =  super.getOptions(gameData, whosAsking);
        for (Room r : moveableRoomsTo) {
            opts.addOption("to " + r.getName());
        }
        opts.addOption("Jump away from station");
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.setCharacter(new FrozenDecorator(performingClient.getCharacter()));
        try {
            if (!jumpAway) {
                Room space = gameData.getRoom("Space");
                performingClient.moveIntoRoom(space);
                performingClient.addTolastTurnInfo("You went out through the airlock into SPACE.");
                gameData.addMovementEvent(new MovingThroughSpaceEvent(performingClient));
            } else {
                performingClient.setCharacter(new TumblingThroughSpaceDecorator(performingClient.getCharacter()));
                gameData.getMap().tumbleIntoLevel(gameData, performingClient, MyRandom.sample(GameMap.getDirectionStrings()));
                performingClient.addTolastTurnInfo("You tumbled through space.");

                if (performingClient.getPosition().getType() != RoomType.space) {
                    performingClient.removeInstance((GameCharacter gc) -> gc instanceof TumblingThroughSpaceDecorator);
                }
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        Logger.log(Logger.INTERESTING, "Finding for args " + args + " rooms is " + moveableRoomsTo.toString());
        for (Room r : moveableRoomsTo) {
           if (args.get(0).contains(r.getName())) {
               selected = r;
               return;
           }
        }
        if (args.get(0).contains("Jump")) {
            jumpAway = true;
            return;
        }
        Logger.log(Logger.CRITICAL, "Could not find target ROOM!");
    }

    private class MovingThroughSpaceEvent extends Event {
        private final Actor performingClient;
        public boolean slipped;
        public static final double SLIP_CHANCE = 0.1;

        public MovingThroughSpaceEvent(Actor performingClient) {
            this.performingClient = performingClient;
            slipped = false;
        }

        @Override
        public void apply(GameData gameData) {
            if (slipped) {

            } else if (MyRandom.nextDouble() < SLIP_CHANCE) {
                this.slipped = true;
                performingClient.addTolastTurnInfo("You lost your grip! You're drifting away!");
                Room spaceRoom = null;
                try {
                    spaceRoom = gameData.getRoom("Deep Space");
                    Logger.log("Moving player into " + spaceRoom.getName());
                    performingClient.moveIntoRoom(spaceRoom);
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
                performingClient.removeInstance((GameCharacter gc) -> gc instanceof FrozenDecorator);
                performingClient.setCharacter(new TumblingThroughSpaceDecorator(performingClient.getCharacter()));

            } else {
                Logger.log("Moving player into " + selected.getName());
                performingClient.moveIntoRoom(selected);
                performingClient.removeInstance((GameCharacter gc) -> gc instanceof FrozenDecorator);
                performingClient.addTolastTurnInfo("You came back into the station.");
            }
        }

        @Override
        public String howYouAppear(Actor performingClient) {
            return "";
        }

        @Override
        public SensoryLevel getSense() {
            return SensoryLevel.NO_SENSE;
        }

        @Override
        public boolean shouldBeRemoved(GameData gameData) {
            return !slipped;
        }
    }
}
