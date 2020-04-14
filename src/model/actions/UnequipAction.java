package model.actions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.suits.SuitItem;
import util.Logger;

import java.util.List;

public class UnequipAction extends Action {
    private SuitItem selectedSuit;

    public UnequipAction() {
        super("Unequip", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "unequipped " + selectedSuit.getPublicName(whosAsking);
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
            performingClient.addTolastTurnInfo("What, nothing to unequip? " + Action.FAILED_STRING);
            return;
        }

        if (!performingClient.getCharacter().getEquipment().getSuitsAsList().contains(selectedSuit)) {
            performingClient.addTolastTurnInfo("What, the " + selectedSuit.getPublicName(performingClient) + " wasn't there? " + Action.FAILED_STRING);
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
}