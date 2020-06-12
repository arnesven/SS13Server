package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.FancyFrame;
import model.fancyframe.ManageInventoryFancyFrame;
import model.fancyframe.ManagePickupsFancyFrame;
import model.fancyframe.UsingGameObjectFancyFrameDecorator;
import model.items.general.GameItem;

import java.util.List;

public class ManageInventoryAction extends FreeAction {
    private final GameData gameData;
    private final boolean pickupFirst;
    private final GameItem preselected;

    public ManageInventoryAction(String s, GameData gameData, boolean pickupFirst, GameItem preselected, Actor actor) {
        super(s + " " + preselected.getPublicName(actor), gameData, (Player)actor);
        this.gameData = gameData;
        this.pickupFirst = pickupFirst;
        this.preselected = preselected;
    }



    @Override
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        FancyFrame ff;
        if (pickupFirst) {
            ff = new ManagePickupsFancyFrame(p, gameData, preselected);
        } else {
            ff = new ManageInventoryFancyFrame(p, gameData, preselected);
        }
        p.setFancyFrame(ff);
    }


}
