package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.ItemHolder;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.objects.general.ContainerObject;

import java.util.List;

public class StoreItemAction extends Action {
    private final ItemHolder container;
    private String requestedItem;

    public StoreItemAction(ItemHolder container, Player player) {
        super("Store Item", SensoryLevel.PHYSICAL_ACTIVITY);
        this.container = container;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "stored something";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        GameItem selectedItem = null;
        for (GameItem it : performingClient.getItems()) {
            if (it.getFullName(performingClient).equals(requestedItem)) {
                selectedItem = it;
            }
        }

        if (selectedItem == null) {
            performingClient.addTolastTurnInfo("What, the item wasn't there? " + failed(gameData, performingClient));
        } else {
            performingClient.getItems().remove(selectedItem);
            selectedItem.setHolder(null);
            selectedItem.setPosition(container.getPosition());
            container.getItems().add(selectedItem);
                performingClient.addTolastTurnInfo("You stored the " + selectedItem.getPublicName(performingClient) +
                        " in the " + container.getPublicName(performingClient) + ".");
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        requestedItem = args.get(0);
    }
}
