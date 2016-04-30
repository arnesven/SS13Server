package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.map.Room;
import model.objects.general.Dumbwaiter;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by erini02 on 29/04/16.
 */
public class DumbwaiterAction extends Action {
    private final Dumbwaiter dumbwaiter;
    private String whichItem;
    private String whichPlace;

    public DumbwaiterAction(Dumbwaiter dumbwaiter) {
        super("Dumbwaiter", SensoryLevel.OPERATE_DEVICE);
        this.dumbwaiter = dumbwaiter;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Used dumbwaiter";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);

        for (GameItem it : whosAsking.getItems()) {
            ActionOption opt2 = new ActionOption("Send " + it.getFullName(whosAsking));

            for (Room r : dumbwaiter.getDestinations(gameData)) {
                opt2.addOption("To " + r.getName());
            }
            opt.addOption(opt2);
        }
        return opt;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        Room dest = null;
        for (Room r : dumbwaiter.getDestinations(gameData)) {
            if (whichPlace.contains(r.getName())) {
                dest = r;
            }
        }
        if (dest == null) {
            throw new NoSuchElementException("Did not find place to send item to!");
        }

        for (GameItem it : performingClient.getItems()) {
            if (whichItem.contains(it.getFullName(performingClient))) {
                performingClient.getItems().remove(it);
                dest.addItem(it);
                performingClient.addTolastTurnInfo("You sent " + it.getBaseName() + " to " + dest.getName() + ".");
                return;
            }
        }
        performingClient.addTolastTurnInfo("What? something was missing! Your action failed!");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        whichItem = args.get(0);
        whichPlace = args.get(1);
    }
}
