package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.ItemHolder;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.FancyFrame;
import model.fancyframe.PackCrateFancyFrame;
import model.fancyframe.PackCrateInventoryFancyFrame;
import model.fancyframe.PackCrateRoomFloorFancyFrame;
import model.objects.general.CrateObject;
import model.objects.general.GameObject;

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
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        opts.addOption("Inventory");
        opts.addOption("Room Floor");

//        for (GameObject obj : whosAsking.getPosition().getObjects()) {
//            if (obj instanceof )
//        }
//
        return opts;
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
            FancyFrame ff;
            if (args.get(0).equals("Room Floor")) {
                ff = new PackCrateRoomFloorFancyFrame((Player)performingClient, gameData, crateObject);
            } else {
                ff = new PackCrateInventoryFancyFrame((Player)performingClient, gameData, crateObject);
            }

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
