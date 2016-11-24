package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.FrozenDecorator;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.items.NoSuchThingException;
import model.map.Room;
import model.objects.general.AirlockPanel;
import model.objects.general.GameObject;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 24/11/16.
 */
public class MoveToOtherAirlocks extends Action {
    private final Room pos;
    private List<Room> moveableRoomsTo = new ArrayList<>();
    private Room selected;

    public MoveToOtherAirlocks(Room position, GameData gameData) {
        super("Go EVA", SensoryLevel.PHYSICAL_ACTIVITY);
        this.pos = position;
        for (Room r : gameData.getRooms()) {
            for (GameObject obj : r.getObjects()) {
                if (obj instanceof AirlockPanel && r != pos) {
                    moveableRoomsTo.add(r);
                    break;
                }
            }
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
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.setCharacter(new FrozenDecorator(performingClient.getCharacter()));
        try {
            Room space = gameData.getRoom("Space");
            performingClient.moveIntoRoom(space);
            performingClient.addTolastTurnInfo("You went out through the airlock into SPACE.");
            gameData.addMovementEvent(new Event() {
                @Override
                public void apply(GameData gameData) {
                    Logger.log("Moving player into " + selected.getName());
                    performingClient.moveIntoRoom(selected);
                    performingClient.removeInstance((GameCharacter gc) -> gc instanceof FrozenDecorator);
                    performingClient.addTolastTurnInfo("You came back into the station.");
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
                    return true;
                }
            });
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
        Logger.log(Logger.CRITICAL, "Could not find target ROOM!");
    }
}
