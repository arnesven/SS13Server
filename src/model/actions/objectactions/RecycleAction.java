package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.objects.recycling.RecyclingContainer;

import java.util.List;

public class RecycleAction extends Action {

    private GameItem selectedItem;

    public RecycleAction() {
        super("Recycle", SensoryLevel.PHYSICAL_ACTIVITY);
    }


    @Override
    protected String getVerb(Actor whosAsking) {
        if (selectedItem == null) {
            return "tried to recycle something.";
        }
        return "recycled " + selectedItem.getPublicName(whosAsking);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (GameItem gi : whosAsking.getItems()) {
            opts.addOption(gi.getFullName(whosAsking));
        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selectedItem == null) {
            performingClient.addTolastTurnInfo("What, the item was missing? " + Action.FAILED_STRING);
        } else {
            performingClient.getItems().remove(selectedItem);
            selectedItem.setHolder(null);
            try {
                RecyclingContainer cont = gameData.findObjectOfType(RecyclingContainer.class);
                cont.getInventory().add(selectedItem);
                selectedItem.setPosition(cont.getPosition());
                performingClient.addTolastTurnInfo("You recycled the " + selectedItem.getPublicName(performingClient) + ". Go you!");
            } catch (NoSuchThingException e) {
                performingClient.addTolastTurnInfo("You tried to recycle the " + selectedItem.getPublicName(performingClient) +", but the trash bin rejected it!");
            }
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        for (GameItem gi : performingClient.getItems()) {
            if (gi.getFullName(performingClient).equals(args.get(0))) {
                selectedItem = gi;
                break;
            }
        }
    }
}
