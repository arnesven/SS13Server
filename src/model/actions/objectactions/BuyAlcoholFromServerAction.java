package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.MoneyStack;
import model.objects.general.Cabinet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 15/11/16.
 */
public class BuyAlcoholFromServerAction extends Action {
    private final Actor server;
    private final Cabinet cabinet;
    private GameItem selected = null;

    public BuyAlcoholFromServerAction(Actor server, Cabinet cabinet) {
        super("Order Drink", SensoryLevel.SPEECH);
        this.server = server;
        this.cabinet = cabinet;

    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "ordered a drink";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);
        List<String> added = new ArrayList<>();
        for (GameItem gi : cabinet.getInventory()) {
            String optName = gi.getPublicName(whosAsking) + " ($$ " + gi.getCost() + ")";
            if (!added.contains(optName)) {
                opt.addOption(optName);
                added.add(optName);
            }
        }
        return opt;
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selected == null) {
            performingClient.addTolastTurnInfo(server.getPublicName() + "; Sorry, we're out.");
            return;
        }

        try {
            MoneyStack buyersMoney = (MoneyStack)GameItem.getItemFromActor(performingClient, new MoneyStack(1));
            if (buyersMoney.getAmount() < selected.getCost()) {
                performingClient.addTolastTurnInfo(server.getPublicName() + "; You do not have enough money!");
                return;
            }

            buyersMoney.transferBetweenActors(performingClient, server, selected.getCost() + "");
            server.addTolastTurnInfo("You served " + selected.getPublicName(server) + " to " + performingClient.getPublicName() + ".");
            performingClient.addTolastTurnInfo(server.getPublicName() + " served you " + selected.getPublicName(performingClient) + ".");
            performingClient.addItem(selected, server.getAsTarget());
            cabinet.getInventory().remove(selected);

        } catch (NoSuchThingException e) {
           performingClient.addTolastTurnInfo(server.getPublicName() + "; You have no money!");
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (GameItem gi : cabinet.getInventory()) {
            if (args.get(0).contains(gi.getPublicName(performingClient))) {
                selected = gi;
            }
        }
    }
}
