package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.objects.general.ContainerObject;
import util.Logger;

import java.util.List;

public class RetrieveAction extends Action {

    private ContainerObject containerObject;
    private GameItem selectedItem;

    public RetrieveAction(ContainerObject containerObject, Actor cl) {
        super("Retrieve from " + containerObject.getPublicName(cl), SensoryLevel.PHYSICAL_ACTIVITY);
        this.containerObject = containerObject;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "retrieved something from the " +
                containerObject.getPublicName(whosAsking).toLowerCase() + ".";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);
        for (GameItem it : containerObject.getInventory()) {
            opt.addOption(it.getPublicName(whosAsking));
        }
        return opt;
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (GameItem it : containerObject.getInventory()) {
            if (args.get(0).contains(it.getPublicName(performingClient))) {
                selectedItem = it;
                return;
            }
        }

        selectedItem = null;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selectedItem == null) {
            performingClient.addTolastTurnInfo(failed(gameData, performingClient));
        } else {
            performingClient.getCharacter().giveItem(selectedItem, null);
            containerObject.getInventory().remove(selectedItem);
            performingClient.addTolastTurnInfo("You retrieved the " +
                            selectedItem.getPublicName(performingClient));
        }
    }
}
