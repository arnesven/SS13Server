package model.actions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.PutOnAction;
import model.actions.general.SensoryLevel;
import model.items.suits.SuitItem;
import sounds.Sound;
import util.Logger;

import java.util.List;

public class UnequipAction extends Action implements QuickAction {
    private SuitItem selectedSuit;

    public UnequipAction() {
        super("Unequip", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        if (selectedSuit == null) {
            return "unequipped something";
        }
        return "unequipped " + selectedSuit.getPublicName(whosAsking);
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return PutOnAction.makeReustleSound();
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (SuitItem s : whosAsking.getCharacter().getEquipment().getTopEquipmentAsList()) {
            opts.addOption(s.getPublicName(whosAsking));
        }
        return opts;
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
         for (SuitItem s : performingClient.getCharacter().getEquipment().getTopEquipmentAsList()) {
            Logger.log(s.getPublicName(performingClient));
            if (args.get(0).equals(s.getPublicName(performingClient))) {
                selectedSuit = s;
            }
        }
        if (selectedSuit == null) {
            Logger.log(Logger.CRITICAL, "Could not find suit to unequip! Looking for " + args.get(0));
        }
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selectedSuit == null) {
            performingClient.addTolastTurnInfo("What, nothing to unequip? " + failed(gameData, performingClient));
            return;
        }

        if (!performingClient.getCharacter().getEquipment().getSuitsAsList().contains(selectedSuit)) {
            performingClient.addTolastTurnInfo("What, the " + selectedSuit.getPublicName(performingClient) + " wasn't there? " + failed(gameData, performingClient));
            return;
        }

        performingClient.getCharacter().removeEquipment(selectedSuit);
        performingClient.getCharacter().giveItem(selectedSuit, performingClient.getAsTarget());
        performingClient.addTolastTurnInfo("You unequipped the " + selectedSuit.getFullName(performingClient));
    }

    @Override
    public boolean hasSpecialOptions() {
        return false;
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
