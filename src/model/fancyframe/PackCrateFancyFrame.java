package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.MultiAction;
import model.items.general.GameItem;
import model.objects.general.CrateObject;

import java.util.Set;

public class PackCrateFancyFrame extends ManageItemsFancyFrame {
    private final CrateObject crate;

    public PackCrateFancyFrame(Player performingClient, GameData gameData, CrateObject crateObject) {
        super(performingClient, gameData,
                "Inventory - Storing", performingClient, (GameItem gi, Player pl) -> gi.getFullName(pl), Integer.MAX_VALUE,
                "Crate - Retrieving ", crateObject, (GameItem gi, Player pl) -> gi.getPublicName(pl), Integer.MAX_VALUE,
                null);
        this.crate = crateObject;
    }

    @Override
    protected MultiAction getFinalAction(GameData gameData, Player player, Set<GameItem> puttings, Set<GameItem> gettings) {
        MultiAction ma = new MultiAction("Pack/Unpack Crate");
        for (GameItem gi : puttings) {
            ma.addAction(makeStoreAction(gi, gameData, player, crate));
        }

        for (GameItem gi : gettings) {
            ma.addAction(makeRetrieveAction(gi, crate, player));
        }
        return ma;
    }
}
