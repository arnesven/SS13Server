package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.objects.general.ContainerObject;

import java.util.List;

public class StoreItemAction extends Action {
    private final ContainerObject container;
    private GameItem selectedItem;

    public StoreItemAction(ContainerObject container, Player player) {
        super("Store Item", SensoryLevel.PHYSICAL_ACTIVITY);
        this.container = container;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "stored something";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selectedItem == null) {
            performingClient.addTolastTurnInfo("What, the item wasn't there? " + Action.FAILED_STRING);
        } else {
            performingClient.getItems().remove(selectedItem);
            selectedItem.setHolder(null);
            selectedItem.setPosition(container.getPosition());
            container.getInventory().add(selectedItem);
            performingClient.addTolastTurnInfo("You stored the " + selectedItem.getPublicName(performingClient) +
                    " in the " + container.getPublicName(performingClient) + ".");
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        for (GameItem it : performingClient.getItems()) {
            if (it.getFullName(performingClient).equals(args.get(0))) {
                selectedItem = it;
                break;
            }
        }
    }
}
