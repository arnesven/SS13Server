package model.fancyframe;

import model.GameData;
import model.ItemHolder;
import model.Player;
import model.actions.general.MultiAction;
import model.items.general.GameItem;
import model.objects.general.CrateObject;

public class PackCrateInventoryFancyFrame extends PackCrateFancyFrame {
    public PackCrateInventoryFancyFrame(Player performingClient, GameData gameData, CrateObject crateObject) {
        super(performingClient, gameData, crateObject, performingClient, "Inventory");
    }

    @Override
    protected void makeRelevantPuttingsAction(MultiAction ma, GameItem gi, Player player, GameData gameData, ItemHolder otherItemHolder, CrateObject crate) {
        ma.addAction(makeStoreAction(gi, gameData, player, crate));
    }

    @Override
    protected void makeRelevantGettingsAction(MultiAction ma, GameItem gi, Player player, GameData gameData, ItemHolder otherItemHolder, CrateObject crate) {
        ma.addAction(makeRetrieveAction(gi, crate, player));
    }


}
