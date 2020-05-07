package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.general.DropAction;
import model.actions.general.MultiAction;
import model.actions.general.PickUpAction;
import model.items.general.GameItem;
import util.HTMLText;
import util.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManageInventoryFancyFrame extends ManageItemsFancyFrame {

    public ManageInventoryFancyFrame(Player performingClient, GameData gameData, GameItem preselectedItem) {
        super(performingClient, gameData, "Inventory - Dropping",
                performingClient, ((GameItem gi, Player pl) -> gi.getFullName(pl)), Integer.MAX_VALUE,
                "Room Items - Picking Up (max 1)", performingClient.getPosition(),
                ((GameItem gi, Player pl) -> gi.getPublicName(pl)), 1, preselectedItem);
    }


    @Override
    protected MultiAction getFinalAction(GameData gameData, Player player, Set<GameItem> puttings, Set<GameItem> gettings) {
        MultiAction multiAction =  new MultiAction("Manage Inventory");
        for (GameItem it : puttings) {
            multiAction.addAction(super.makeDropAction(it, player));
        }
        for (GameItem it : gettings) {
            multiAction.addAction(super.makePickUpAction(it, player));
        }
        return multiAction;
    }
}
