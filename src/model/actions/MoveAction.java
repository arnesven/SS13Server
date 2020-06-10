package model.actions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;
import sounds.Sound;
import util.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveAction extends Action {
    private final GameData gameData;
    private Room destination;

    public MoveAction(GameData gameData, Actor actor) {
        super("Move", SensoryLevel.PHYSICAL_ACTIVITY);
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "moved";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        Set<Room> canMoveTo = whosAsking.findMoveToAblePositions(gameData);
        String optstr = "";
        for (Room r : canMoveTo) {
            opts.addOption(r.getName());
            optstr += r.getName() + ",";
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
        for (Room r : performingClient.findMoveToAblePositions(gameData)) {
            if (args.get(0).equals(r.getName())) {
                Logger.log("  -> Destination found!");
                destination = r;
            }
        }
        if (destination == null) {
            Logger.log(Logger.CRITICAL, "Destination not found!");
        }
    }


    protected void setDestination(Room r) {
        destination = r;
    }

}
