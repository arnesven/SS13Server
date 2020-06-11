package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.objects.general.ContainerObject;
import sounds.Sound;
import util.Logger;

import java.util.List;

public class RetrieveAction extends Action implements QuickAction {

    private ContainerObject containerObject;
    private String requestedItem;
    private GameItem selectedItem;

    public RetrieveAction(ContainerObject containerObject, Actor cl) {
        super("Retrieve from " + containerObject.getPublicName(cl), SensoryLevel.PHYSICAL_ACTIVITY);
        this.containerObject = containerObject;
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return  selectedItem.getPickUpSound();
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
        requestedItem = args.get(0);
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        this.selectedItem = null;
        for (GameItem it : containerObject.getInventory()) {
            if (requestedItem.contains(it.getPublicName(performingClient))) {
                selectedItem = it;
            }
        }

        if (selectedItem == null) {
            performingClient.addTolastTurnInfo("What, the item is not there?" + failed(gameData, performingClient));
        } else {
            performingClient.getCharacter().giveItem(selectedItem, null);
            containerObject.getInventory().remove(selectedItem);
            performingClient.addTolastTurnInfo("You retrieved the " +
                            selectedItem.getPublicName(performingClient));
        }
    }

    @Override
    public void performQuickAction(GameData gameData, Player performer) {
        execute(gameData, performer);
    }

    @Override
    public boolean isValidToExecute(GameData gameData, Player performer) {
        return true;
    }

    @Override
    public List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer) {
        return performer.getPosition().getClients();
    }
}
