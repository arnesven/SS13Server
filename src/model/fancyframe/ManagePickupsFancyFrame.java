package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.general.MultiAction;
import model.items.general.GameItem;

import java.util.Set;

public class ManagePickupsFancyFrame extends ManageItemsFancyFrame {
    public ManagePickupsFancyFrame(Player performingClient, GameData gameData, GameItem preselectedItem) {
        super(performingClient, gameData,
                "Room Items - Picking Up (max 1)", performingClient.getPosition(), ((GameItem gi, Player pl) -> gi.getPublicName(pl)), 1,
                "Inventory - Dropping", performingClient, ((GameItem gi, Player pl) -> gi.getFullName(pl)), Integer.MAX_VALUE,
                preselectedItem);

    }


    @Override
    protected MultiAction getFinalAction(GameData gameData, Player player, Set<GameItem> puttings, Set<GameItem> gettings) {
        MultiAction multiAction =  new MultiAction("Manage Inventory");
        for (GameItem it : puttings) {
            multiAction.addAction(super.makePickUpAction(it, player));
        }
        for (GameItem it : gettings) {
            multiAction.addAction(super.makeDropAction(it, player));
        }
        return multiAction;
    }
}
