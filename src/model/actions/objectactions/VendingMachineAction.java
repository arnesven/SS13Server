package model.actions.objectactions;

import model.Actor;
import model.Bank;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.ItemStackDepletedException;
import model.items.general.MoneyStack;
import model.objects.general.VendingMachine;

import java.util.List;

public class VendingMachineAction extends Action {
    private VendingMachine vendingMachine;

    private GameItem selectedItem;
    private int cost;


    public VendingMachineAction(VendingMachine vendingMachine) {
        super("Use " + vendingMachine.getName(), SensoryLevel.OPERATE_DEVICE);
        this.vendingMachine = vendingMachine;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with Vending Machine";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        MoneyStack money;
        try {
            money = MoneyStack.getActorsMoney(performingClient);
        } catch (NoSuchThingException e) {
            performingClient.addTolastTurnInfo("You don't have any money!");
            return;
        }
        if (cost > money.getAmount()) {
            performingClient.addTolastTurnInfo("Sorry, you don't have enough money.");
            return;
        }

        try {
            money.subtractFrom(cost);
            Bank.getInstance(gameData).addToStationMoney(cost);
        } catch (ItemStackDepletedException e) {
            performingClient.getItems().remove(money);
        }

        performingClient.addItem(selectedItem.clone(), null);
        performingClient.addTolastTurnInfo("You got a " + selectedItem.getBaseName() + " from the Vending Machine.");
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption aop = super.getOptions(gameData, whosAsking);
        for (GameItem it : vendingMachine.getItems()) {
            aop.addOption(it.getBaseName() + " ($$ " + it.getCost() + ")");
        }
        return aop;
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (GameItem it : vendingMachine.getItems()) {
            if (args.get(0).contains(it.getBaseName())) {
                selectedItem = it;
                String costStr = args.get(0).split("\\$\\$ ")[1];
                cost = Integer.parseInt(costStr.substring(0, costStr.length()-1));
                break;
            }
        }
    }

}
