package model.objects.consoles;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;
import model.objects.BarSign;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class BarSignAction extends Action {
    private BarSignControl barSignControl;
    private String selected;


    public BarSignAction(BarSignControl barSignControl) {
        super("Set Bar Sign", SensoryLevel.OPERATE_DEVICE);
        this.barSignControl = barSignControl;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "fiddled with " + barSignControl.getName();
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        boolean didStuff = false;
        List<Room> roomsToLookAt = new ArrayList<>();
        roomsToLookAt.addAll(barSignControl.getPosition().getNeighborList());
        roomsToLookAt.add(barSignControl.getPosition());
        for (Room r : roomsToLookAt) {
            for (GameObject obj : r.getObjects()) {
                if (obj instanceof BarSign) {
                    ((BarSign)obj).setAppearance(BarSignControl.getSigns().get(selected));
                    didStuff = true;
                }
            }
        }
        if (didStuff) {
            performingClient.addTolastTurnInfo("You set the bar's signs to '" + selected + "'");
        } else {
            performingClient.addTolastTurnInfo("No bar signs connected.");
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        selected = args.get(0);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (String signName : BarSignControl.getSigns().keySet()) {
            ActionOption innerOpt = new ActionOption(signName);

            opts.addOption(innerOpt);
        }
        return opts;
    }
}
