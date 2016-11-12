package model.actions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import model.objects.general.RemotelyOperateable;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 25/10/16.
 */
public abstract class RemoteAccessAction extends Action {

    private List<GameObject> remotes;
    private String selectedAction;
    private List<String> args;

    public RemoteAccessAction(SensoryLevel sl) {
        super("Remote Access", sl);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        remotes = getRemotes(gameData);
        ActionOption opt = super.getOptions(gameData, whosAsking);
        for (GameObject o : remotes) {
            for (Action a : getActionsForRemote(gameData, whosAsking, o)) {
                a.setSense(SensoryLevel.NO_SENSE);
                opt.addOption(a.getOptions(gameData, whosAsking));
            }

        }

        return opt;
    }



    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        for (GameObject o : getRemotes(gameData)) {
            for (Action a : getActionsForRemote(gameData, performingClient, o)) {
                if (selectedAction.contains(a.getName())) {
                    a.setArguments(args, performingClient);
                    a.doTheAction(gameData, performingClient);
                    break;
                }
            }
        }

    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        selectedAction = args.get(0);
        this.args = new ArrayList<>(args.subList(1, args.size()));
    }

    private List<GameObject> getRemotes(GameData gameData) {
        List<GameObject> remotes = new ArrayList<>();
        for (GameObject o : gameData.getObjects()) {
            if (o instanceof RemotelyOperateable) {
                if (!(o instanceof ElectricalMachinery) ||
                        ElectricalMachinery.isPowered(gameData, (ElectricalMachinery)o)) {
                    remotes.add(o);
                }
            }
        }
        return remotes;
    }

    private List<Action> getActionsForRemote(GameData gameData, Actor whosAsking, GameObject ob) {
        ArrayList<Action> at = new ArrayList<>();
        ob.addSpecificActionsFor(gameData, (Player)whosAsking, at);
        return at;
    }

}