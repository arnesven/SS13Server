package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.FancyFrame;
import model.objects.general.ElectricalMachinery;

import java.util.List;

public abstract class WalkUpToElectricalMachineryAction extends Action {

    private final GameData gameData;
    private final ElectricalMachinery machine;

    public WalkUpToElectricalMachineryAction(GameData gameData, Actor pl, ElectricalMachinery machine) {
        super("Walk up to " + machine.getPublicName(pl) + " (free action)", SensoryLevel.PHYSICAL_ACTIVITY);
        this.gameData = gameData;
        this.machine = machine;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "walked up to " + machine.getPublicName(whosAsking);
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player) {
            ((Player) performingClient).setFancyFrame(getFancyFrame(gameData, performingClient));
            ((Player) performingClient).setNextAction(new DoNothingAction());
            ((Player) performingClient).refreshClientData();
        }
    }

    protected abstract FancyFrame getFancyFrame(GameData gameData, Actor performingClient);

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
