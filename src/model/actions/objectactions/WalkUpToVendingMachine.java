package model.actions.objectactions;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.FancyFrame;
import model.fancyframe.VendingMachineFancyFrame;
import model.objects.general.VendingMachine;

import java.util.List;

public class WalkUpToVendingMachine extends WalkUpToElectricalMachineryAction {
    private final VendingMachine vending;

    public WalkUpToVendingMachine(GameData gameData, Player cl, VendingMachine vending) {
        super(gameData, cl, vending);
        this.vending = vending;
    }

    @Override
    protected FancyFrame getFancyFrame(GameData gameData, Actor performingClient) {
        return vending.getFancyFrame(performingClient);
    }
}
