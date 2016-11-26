package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.MoneyStack;
import model.npcs.MerchantNPC;
import model.npcs.NPC;
import model.objects.MerchantWaresCrate;

import java.util.List;

/**
 * Created by erini02 on 15/11/16.
 */
public class ShopFromMerchantAction extends Action {

    private final MerchantWaresCrate crate;
    private final MerchantNPC merchant;
    private GameItem selectedItem = null;

    public ShopFromMerchantAction(MerchantWaresCrate merchantWaresCrate,
                                  MerchantNPC merchant) {
        super("Shop From Merchant", SensoryLevel.SPEECH);
        this.crate = merchantWaresCrate;
        this.merchant = merchant;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "purchased something from the merchant";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (GameItem gi : crate.getInventory()) {
            opts.addOption(gi.getPublicName(whosAsking) + " ($$ " + gi.getCost() + ")");
        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selectedItem == null || !merchant.getItems().contains(selectedItem)) {
            performingClient.addTolastTurnInfo(merchant.getPublicName() + "; Sorry, I'm sold out of that item.");
            return;
        }

        MoneyStack buyersMoney = null;
        try {
            buyersMoney = (MoneyStack) GameItem.getItemFromActor(performingClient, new MoneyStack(1));
            if (buyersMoney.getAmount() < selectedItem.getCost()) {
                performingClient.addTolastTurnInfo(merchant.getPublicName() + "; You do not have enough money!");
                return;
            }

            buyersMoney.transferBetweenActors(performingClient, merchant, selectedItem.getCost() + "");
            performingClient.addTolastTurnInfo(" You bought a " + selectedItem.getPublicName(performingClient) + ".");
            performingClient.addItem(selectedItem, merchant);
            crate.getInventory().remove(selectedItem);


        } catch (NoSuchThingException e) {
            performingClient.addTolastTurnInfo(merchant.getPublicName() + "; You don't have any money!");

        }


    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (GameItem gi : crate.getInventory()) {
            if (args.get(0).contains(gi.getPublicName(performingClient))) {
                selectedItem = gi;
            }
        }
    }
}
