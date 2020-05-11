package model.fancyframe;

import model.Actor;
import model.GameData;
import model.ItemHolder;
import model.Player;
import model.actions.general.MultiAction;
import model.items.general.GameItem;
import model.objects.general.CrateObject;

import java.util.Set;

public abstract class PackCrateFancyFrame extends ManageItemsFancyFrame {
    private final CrateObject crate;
    private final ItemHolder otherItemHolder;

    public PackCrateFancyFrame(Player performingClient, GameData gameData, CrateObject crateObject, ItemHolder otherItemHolder, String otherName) {
        super(performingClient, gameData,
                otherName + " - Storing", otherItemHolder, (GameItem gi, Player pl) -> gi.getFullName(pl), Integer.MAX_VALUE,
                "Crate - Retrieving ", crateObject, (GameItem gi, Player pl) -> gi.getPublicName(pl), Integer.MAX_VALUE,
                null);
        this.crate = crateObject;
        this.otherItemHolder = otherItemHolder;
    }

    @Override
    protected MultiAction getFinalAction(GameData gameData, Player player, Set<GameItem> puttings, Set<GameItem> gettings) {
        MultiAction ma = new MultiAction("Pack/Unpack Crate", "packed a crate");
        for (GameItem gi : puttings) {
            makeRelevantPuttingsAction(ma, gi, player, gameData, otherItemHolder, crate);
        }

        for (GameItem gi : gettings) {
            makeRelevantGettingsAction(ma, gi, player, gameData, otherItemHolder, crate);
        }
        return ma;
    }

    protected abstract void makeRelevantPuttingsAction(MultiAction ma, GameItem gi, Player player, GameData gameData, ItemHolder otherItemHolder, CrateObject crate);

    protected abstract void makeRelevantGettingsAction(MultiAction ma, GameItem gi, Player player, GameData gameData, ItemHolder otherItemHolder, CrateObject crate);

}
