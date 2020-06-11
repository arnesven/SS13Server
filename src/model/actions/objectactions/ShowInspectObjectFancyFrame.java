package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.APCFancyFrame;
import model.fancyframe.FancyFrame;
import model.objects.power.AreaPowerControl;

import java.util.List;

public class ShowInspectObjectFancyFrame extends FreeAction {
    private final GameData gameData;
    private final AreaPowerControl apc;

    public ShowInspectObjectFancyFrame(Player cl, GameData gameData, AreaPowerControl areaPowerControl) {
        super("Inspect " + areaPowerControl.getPublicName(cl), gameData, cl);
        this.gameData = gameData;
        this.apc = areaPowerControl;
    }


    @Override
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        FancyFrame ff = new APCFancyFrame(p, gameData, apc);
        p.setFancyFrame(ff);

    }

}
