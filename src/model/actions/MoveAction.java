package model.actions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;
import util.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveAction extends Action {
    private Room destination;

    public MoveAction(Actor actor) {
        super("Move", SensoryLevel.PHYSICAL_ACTIVITY);
        Logger.log("Creating Move action object for " + actor.getPublicName());
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "moved";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        Set<Room> canMoveTo = findMoveToAblePositions(whosAsking);

        for (Room r : canMoveTo) {
            opts.addOption(r.getName());
        }

        return opts;
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (performingClient instanceof Player) {
            if (destination != null) {
                ((Player) performingClient).setNextMove(destination.getID());
                performingClient.addTolastTurnInfo("You are moving towards " + destination.getName());
            } else {
                Logger.log(Logger.CRITICAL, "Destination was null!");
            }
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        Logger.log("Searching for destination: " + args.get(0));
        for (Room r : findMoveToAblePositions(performingClient)) {
            if (args.get(0).equals(r.getName())) {
                Logger.log("  -> Destination found!");
                destination = r;
            }
        }
        if (destination == null) {
            Logger.log(Logger.CRITICAL, "Destination not found!");
        }
    }

    private Set<Room> findMoveToAblePositions(Actor whosAsking) {
        HashSet<Room> canMoveTo = new HashSet<>();
        canMoveTo.add(whosAsking.getPosition());
        int movement = whosAsking.getCharacter().getMovementSteps();
        for (int i = 1; i <= movement; ++i) {
            Set<Room> newLocations = new HashSet<>();
            for (Room r : canMoveTo) {
                newLocations.addAll(r.getNeighborList());
            }
            canMoveTo.addAll(newLocations);
        }
        canMoveTo.remove(whosAsking.getPosition());
        return canMoveTo;
    }
}
