package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.BombDefusingFancyFrame;
import model.fancyframe.FancyFrame;
import model.items.general.BombItem;

import java.util.List;

public class ShowDefuseFancyFrame extends FreeAction {
    private final GameData gameData;
    private final BombItem bomb;

    public ShowDefuseFancyFrame(GameData gameData, Player forWhom, BombItem bombItem) {
        super("Try to Defuse", gameData, forWhom);
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
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        FancyFrame ff = new BombDefusingFancyFrame(p, gameData, bomb);
        p.setFancyFrame(ff);
        p.refreshClientData();
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
