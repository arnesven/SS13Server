package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.PickUpAction;
import model.actions.objectactions.StoreItemAction;
import model.items.general.GameItem;
import model.objects.general.CrateObject;

import java.util.ArrayList;
import java.util.List;

public class PickUpAndStoreAction extends PickUpAction {
    private final CrateObject crate;
    private final GameItem item;

    public PickUpAndStoreAction(Actor clientActionPerformer, CrateObject crate, GameItem gi) {
        super(clientActionPerformer);
        this.crate = crate;
        this.item = gi;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        super.execute(gameData, performingClient);
        StoreItemAction sia = new StoreItemAction(crate, (Player)performingClient);
        List<String> args = new ArrayList<>();
        args.add(item.getFullName(performingClient));
        sia.setActionTreeArguments(args, performer);
        sia.doTheAction(gameData, performingClient);
    }
}
