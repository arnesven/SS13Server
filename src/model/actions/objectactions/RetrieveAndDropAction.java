package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.DropAction;
import model.items.general.GameItem;
import model.objects.general.CrateObject;

import java.util.ArrayList;
import java.util.List;

public class RetrieveAndDropAction extends RetrieveAction {
    private final GameItem item;

    public RetrieveAndDropAction(CrateObject crate, Player player, GameItem gi) {
        super(crate, player);
        this.item = gi;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        super.execute(gameData, performingClient);
        DropAction drop = new DropAction(performingClient);
        List<String> args = new ArrayList<>();
        args.add(item.getFullName(performingClient));
        drop.setActionTreeArguments(args, performer);
        drop.doTheAction(gameData, performingClient);
    }
}
