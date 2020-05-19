package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.CrystalBallFancyFrame;
import model.fancyframe.FancyFrame;
import model.objects.CrystalBall;
import model.objects.general.GameObject;

import java.util.List;

public abstract class WalkUpToObjectAction extends Action {
    private final GameData gameData;
    private final GameObject someObj;

    public WalkUpToObjectAction(GameData gameData, Actor cl, CrystalBall crystalBall) {
        super("Peer into " + crystalBall.getPublicName(cl), SensoryLevel.OPERATE_DEVICE);
        this.gameData = gameData;
        this.someObj = crystalBall;
    }


    @Override
    protected String getVerb(Actor whosAsking) {
        return "used the " + someObj.getPublicName(whosAsking);
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player) {
            FancyFrame ff = getFancyFrame(performingClient, gameData, someObj);
            ((Player) performingClient).setFancyFrame(ff);
            ((Player) performingClient).setNextAction(new DoNothingAction());
            ((Player) performingClient).refreshClientData();
        }
    }

    protected abstract FancyFrame getFancyFrame(Actor performingClient, GameData gameData, GameObject someObj);

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
