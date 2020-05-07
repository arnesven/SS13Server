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
            RetrieveAction ra = new RetrieveAction(container, player);
            List<String> args = new ArrayList<>();
            args.add(gi.getPublicName(player));
            ra.setActionTreeArguments(args, player);
            multiAction.addAction(ra);
        }

        for (GameItem gi : gettings) {
            StoreItemAction sia = new StoreItemAction(container, player);
            List<String> args = new ArrayList<>();
            args.add(gi.getFullName(player));
            sia.setActionTreeArguments(args, player);
            multiAction.addAction(sia);
        }

        return multiAction;
    }
}
