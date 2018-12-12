package model.actions.ai;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.ElectrifyObjectAction;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;
import model.objects.consoles.AIConsole;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 22/12/16.
 */
public class AIOverchargeAction extends Action {
    private final GameData gameData;
    private ElectricalMachinery selected;

    public AIOverchargeAction(GameData gameData) {
        super("Overcharge", SensoryLevel.NO_SENSE);
        this.gameData = gameData;
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);

        for (Room r :  gameData.getMap().getRoomsForLevel("ss13")) {
            for (GameObject ob : r.getObjects()) {
                if (ob instanceof ElectricalMachinery) {
                    opt.addOption(ob.getPublicName(whosAsking) + " (" + ob.getPosition().getName() + ")");
                }
            }
        }

        return opt;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "overchared";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selected == null) {
            performingClient.addTolastTurnInfo("What, object not found? " + Action.FAILED_STRING);
            return;
        }

        ElectrifyObjectAction ele = new ElectrifyObjectAction(selected);
        ele.setArguments(new ArrayList<>(), performingClient);
        ele.doTheAction(gameData, performingClient);
        performingClient.addTolastTurnInfo("The " + selected.getName() + " was overcharged. It'll shock whoever touches it!");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Room r :  gameData.getMap().getRoomsForLevel("ss13")) {
            for (GameObject ob : r.getObjects()) {
                if (ob instanceof ElectricalMachinery) {
                    if (args.get(0).contains(ob.getPublicName(performingClient) + " (" + ob.getPosition().getName() + ")")) {
                        selected = (ElectricalMachinery)ob;
                    }
                }
            }
        }
    }
}
