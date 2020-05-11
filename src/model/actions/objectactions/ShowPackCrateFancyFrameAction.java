package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.FancyFrame;
import model.fancyframe.PackCrateFancyFrame;
import model.objects.general.CrateObject;

import java.util.List;

public class ShowPackCrateFancyFrameAction extends Action {

    private final GameData gameData;
    private final CrateObject crateObject;

    public ShowPackCrateFancyFrameAction(CrateObject crateObject, Actor cl, GameData gameData) {
        super("Pack/Unpack " + crateObject.getPublicName(cl), SensoryLevel.PHYSICAL_ACTIVITY);
        this.gameData = gameData;
        this.crateObject = crateObject;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return ""; // should not be needed
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player) {
            FancyFrame ff = new PackCrateFancyFrame((Player)performingClient, gameData, crateObject);
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
