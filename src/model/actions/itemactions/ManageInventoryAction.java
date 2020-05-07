package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.FancyFrame;
import model.fancyframe.ManageInventoryFancyFrame;
import model.fancyframe.UsingGameObjectFancyFrameDecorator;

import java.util.List;

public class ManageInventoryAction extends Action {
    private final GameData gameData;
    private final boolean pickupFirst;

    public ManageInventoryAction(String s, GameData gameData, boolean pickupFirst) {
        super(s, SensoryLevel.NO_SENSE);
        this.gameData = gameData;
        this.pickupFirst = pickupFirst;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        // should not be executed...
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player) {
            FancyFrame ff = new ManageInventoryFancyFrame((Player)performingClient, gameData, pickupFirst);
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
