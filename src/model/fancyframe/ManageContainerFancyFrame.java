package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.DropAction;
import model.actions.general.MultiAction;
import model.actions.objectactions.RetrieveAction;
import model.actions.objectactions.StoreItemAction;
import model.items.general.GameItem;
import model.objects.general.ContainerObject;
import sounds.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ManageContainerFancyFrame extends ManageItemsFancyFrame {
    private final ContainerObject container;

    public ManageContainerFancyFrame(Player performingClient, GameData gameData, ContainerObject container) {
        super(performingClient, gameData,
                "Storage - Retrieving (max 1)", container, ((GameItem gi, Player pl) -> gi.getPublicName(pl)), 1,
                "Inventory - Storing (max 1)", performingClient, ((GameItem gi, Player pl) -> gi.getFullName(pl)), 1, null);
        this.container = container;
    }

    @Override
    protected MultiAction getFinalAction(GameData gameData, Player player, Set<GameItem> puttings, Set<GameItem> gettings) {
        MultiAction multiAction = new MultiAction("Manage Storage");
        for (GameItem gi : puttings) {
               multiAction.addAction(super.makeRetrieveAction(gi, container, player));
        }

        for (GameItem gi : gettings) {
            multiAction.addAction(super.makeStoreAction(gi, gameData, player, container));

        }
        return multiAction;
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.equals("DISMISS")) {
            player.getSoundQueue().add(new Sound("crate_close"));
        }
    }
}
