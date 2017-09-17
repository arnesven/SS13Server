package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.MoneyStack;
import model.items.general.ItemStackDepletedException;
import model.objects.general.ATM;

import java.util.List;

/**
 * Created by erini02 on 15/11/16.
 */
public class AccessAccountAction extends Action {

    private final ATM atm;
    private boolean withdraw;

    public AccessAccountAction(ATM atm) {
        super("Access Account", SensoryLevel.OPERATE_DEVICE);
        this.atm = atm;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "used the ATM";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        MoneyStack account = atm.getAccountForActor(whosAsking);
        opts.addOption("Withdraw ($$ " + account.getAmount() + ")");
        try {
            MoneyStack cash = MoneyStack.getActorsMoney(whosAsking);
            opts.addOption("Deposit ($$ " + cash.getAmount() + ")");
        } catch (NoSuchThingException e) {

        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        MoneyStack account = atm.getAccountForActor(performingClient);
        MoneyStack cash;
        try {
            cash = MoneyStack.getActorsMoney(performingClient);
        } catch (NoSuchThingException e) {
            cash = new MoneyStack(0);
        }
        if (withdraw) {
            int amount = account.getAmount();
            cash.addTo(amount);
            performingClient.addTolastTurnInfo("You withdrew $$ " + amount + " from your account.");
            if (!performingClient.getItems().contains(cash)) {
                performingClient.getCharacter().giveItem(cash, null);
            }
            try {
                account.subtractFrom(amount);
            } catch (ItemStackDepletedException e) {
                // should happen!
            }
        } else {
            int amount = cash.getAmount();
            try {
                cash.subtractFrom(amount);
            } catch (ItemStackDepletedException e) {
                // should happen!
                performingClient.getItems().remove(cash);
            }
            performingClient.addTolastTurnInfo("You deposited $$ " + amount + " into your account.");
            account.addTo(amount);
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        if (args.get(0).contains("Withdraw")) {
            this.withdraw = true;
        } else {
            this.withdraw = false;
        }
    }
}
