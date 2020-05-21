package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.APCFancyFrame;
import model.fancyframe.FancyFrame;
import model.objects.power.AreaPowerControl;

import java.util.List;

public class ShowInspectObjectFancyFrame extends Action {
    private final GameData gameData;
    private final AreaPowerControl apc;

    public ShowInspectObjectFancyFrame(Player cl, GameData gameData, AreaPowerControl areaPowerControl) {
        super("Inspect " + areaPowerControl.getPublicName(cl), SensoryLevel.OPERATE_DEVICE);
        this.gameData = gameData;
        this.apc = areaPowerControl;
    }


    @Override
    protected String getVerb(Actor whosAsking) {
        return "inspected";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player) {
            FancyFrame ff = new APCFancyFrame((Player)performingClient, gameData, apc);
            ((Player) performingClient).setFancyFrame(ff);
            ((Player) performingClient).setNextAction(new DoNothingAction());
            ((Player) performingClient).refreshClientData();
        }
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
