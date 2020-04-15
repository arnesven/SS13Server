package model.actions.objectactions;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.VendingMachineFancyFrame;
import model.objects.general.VendingMachine;

import java.util.List;

public class WalkUpToVendingMachine extends Action {
    private final VendingMachine vending;

    public WalkUpToVendingMachine(GameData gameData, Actor cl, VendingMachine vending) {
        super("Walk up to Vending Machine (Free Action)", SensoryLevel.PHYSICAL_ACTIVITY);
        this.vending = vending;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "walked up to the vending machine";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        // Should not be called, unless something weird happened.
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player) {
            ((Player) performingClient).setFancyFrame(vending.getFancyFrame(performingClient));
            ((Player) performingClient).setNextAction(new DoNothingAction());
            ((Player) performingClient).refreshClientData();
        }
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
