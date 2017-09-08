package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.foods.FoodItem;
import model.items.foods.PoisonedConsumable;
import model.items.general.GameItem;
import model.items.general.PoisonSyringe;

import java.util.List;

/**
 * Created by erini02 on 08/09/17.
 */
public class PoisonFoodAction extends Action {
    private FoodItem selectedItem;


    public PoisonFoodAction() {
        super("Poison Consumable", SensoryLevel.OPERATE_DEVICE);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Poisoned a consumable";
    }



    @Override
    protected void execute(GameData gameData, Actor performingClient) {

        if (performingClient.getItems().contains(selectedItem)) {

            try {
                PoisonSyringe ps = GameItem.getItemFromActor(performingClient, new PoisonSyringe());
                performingClient.addTolastTurnInfo("You poisoned the " + selectedItem.getPublicName(performingClient));
                ps.setFilled(false);
                ps.setOutOfPoison(true);
                performingClient.getItems().remove(selectedItem);
                performingClient.addItem(new PoisonedConsumable(selectedItem, performingClient), null);

            } catch (NoSuchThingException e) {
                performingClient.addTolastTurnInfo("What, the poison syringe is gone? " + Action.FAILED_STRING);
            }
        } else {
            performingClient.addTolastTurnInfo("What, the consumable is gone? " + Action.FAILED_STRING);
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (GameItem it : performingClient.getItems()) {
            if (it instanceof FoodItem) {
                if (it.getPublicName(performingClient).equals(args.get(0))) {
                    this.selectedItem = (FoodItem)it;
                }
            }
        }
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);

        for (GameItem it : whosAsking.getItems()) {
            if (it instanceof FoodItem) {
                opt.addOption(it.getPublicName(whosAsking));
            }
        }

        return opt;
    }
}
