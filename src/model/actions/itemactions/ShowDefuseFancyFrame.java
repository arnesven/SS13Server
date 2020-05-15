package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.BombDefusingFancyFrame;
import model.fancyframe.FancyFrame;
import model.items.general.BombItem;

import java.util.List;

public class ShowDefuseFancyFrame extends Action {
    private final GameData gameData;
    private final BombItem bomb;

    public ShowDefuseFancyFrame(GameData gameData, Player forWhom, BombItem bombItem) {
        super("Try to Defuse (Free Action)", SensoryLevel.OPERATE_DEVICE);
        this.gameData = gameData;
        this.bomb = bombItem;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        // should not be executed
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player) {
            FancyFrame ff = new BombDefusingFancyFrame((Player)performingClient, gameData, bomb);
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
