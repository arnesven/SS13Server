package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.ItemDescriptionFancyFrame;
import model.items.general.GameItem;

import java.util.List;

public class ShowExamineFancyFrameAction extends Action {
    private final GameItem item;
    private final GameData gameData;

    public ShowExamineFancyFrameAction(GameData gameData, Player pl, GameItem gameItem) {
        super("Examine " + gameItem.getPublicName(pl), SensoryLevel.NO_SENSE);
        this.item = gameItem;
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "examined something";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        // Should not happen!
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player) {
            ((Player) performingClient).setFancyFrame(new ItemDescriptionFancyFrame((Player)performingClient, gameData, item));
            ((Player) performingClient).setNextAction(new DoNothingAction());
            ((Player) performingClient).refreshClientData();
        }
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
