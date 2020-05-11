package model.fancyframe;

import model.GameData;
import model.ItemHolder;
import model.Player;
import model.actions.general.Action;
import model.actions.general.MultiAction;
import model.actions.general.PickUpAction;
import model.actions.itemactions.PickUpAndStoreAction;
import model.actions.objectactions.RetrieveAction;
import model.actions.objectactions.RetrieveAndDropAction;
import model.items.general.GameItem;
import model.objects.general.CrateObject;

import java.util.ArrayList;
import java.util.List;

public class PackCrateRoomFloorFancyFrame extends PackCrateFancyFrame {
    public PackCrateRoomFloorFancyFrame(Player performingClient, GameData gameData, CrateObject crateObject) {
        super(performingClient, gameData, crateObject, performingClient.getPosition(), "Room Floor");
    }

    @Override
    protected void makeRelevantPuttingsAction(MultiAction ma, GameItem gi, Player player, GameData gameData, ItemHolder otherItemHolder, CrateObject crate) {
        ma.addAction(makePickupAndStoreAction(gi, gameData, player, crate));
    }

    private Action makePickupAndStoreAction(GameItem gi, GameData gameData, Player player, CrateObject crate) {
        PickUpAction puas = new PickUpAndStoreAction(player, crate, gi);
        List<String> args = new ArrayList<>();
        args.add(gi.getPublicName(player));
        puas.setActionTreeArguments(args, player);
        return puas;
    }

    @Override
    protected void makeRelevantGettingsAction(MultiAction ma, GameItem gi, Player player, GameData gameData, ItemHolder otherItemHolder, CrateObject crate) {
        ma.addAction(makeRetrieveAndDropAction(gi, crate, player));
    }

    private Action makeRetrieveAndDropAction(GameItem gi, CrateObject crate, Player player) {
        RetrieveAction ra = new RetrieveAndDropAction(crate, player, gi);
        List<String> args = new ArrayList<>();
        args.add(gi.getPublicName(player));
        ra.setActionTreeArguments(args, player);
        return ra;
    }


}
