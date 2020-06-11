package model.actions.itemactions;

import com.sun.syndication.feed.rss.Item;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.ItemDescriptionFancyFrame;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.laws.ObeyHumansAccordingToCrewRankAILaw;

import java.util.List;

public class ShowExamineFancyFrameAction extends FreeAction {
    private final GameItem item;
    private final GameData gameData;

    public ShowExamineFancyFrameAction(GameData gameData, Player pl, GameItem gameItem) {
        super("Examine " + gameItem.getPublicName(pl), gameData, pl);
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
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        p.setFancyFrame(getFancyFrame(p));

    }

    protected ItemDescriptionFancyFrame getFancyFrame(Actor performingClient) {
        return new ItemDescriptionFancyFrame((Player)performingClient, gameData, item);
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
