package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.fancyframe.ATMFancyFrame;
import model.items.NoSuchThingException;
import model.items.general.MoneyStack;
import model.items.general.ItemStackDepletedException;
import model.objects.general.ATM;
import util.Logger;

import java.util.List;

/**
 * Created by erini02 on 15/11/16.
 */
public class AccessAccountAction extends Action {

    private final ATM atm;
    private final ATMFancyFrame ff;
    private int amount;
    private boolean withdraw;

    public AccessAccountAction(ATM atm, ATMFancyFrame fancyFrame) {
        super("Access Account", SensoryLevel.OPERATE_DEVICE);
        this.atm = atm;
        this.amount = -1;
        this.ff = fancyFrame;
    }

    public AccessAccountAction(ATM atm) {
        this(atm, null);
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
            if (this.amount == -1) {
                amount = account.getAmount();
            }
            performingClient.addTolastTurnInfo("You withdrew $$ " + amount + " from your account.");
            if (ff != null) {
                Logger.log("We have a fancy frame here...");
                atm.setMoneyShowing(new MoneyStack(amount));
                ff.finalizeTransaction(gameData, performingClient);
            } else if (!performingClient.getItems().contains(cash)) {
                cash.addTo(amount);
                performingClient.getCharacter().giveItem(cash, null);
            }
            try {
                account.subtractFrom(amount);
            } catch (ItemStackDepletedException e) {
                // should happen!
            }
        } else {
            int amount = cash.getAmount();
            if (ff != null) {
                ff.finalizeTransaction(gameData, performingClient);
                amount = this.amount;
            }
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
        if (args.size() > 1) {
            try {
                this.amount = Integer.parseInt(args.get(1));
            } catch (NumberFormatException nfe) {

            }
        }
    }

    @Override
    public boolean hasSpecialOptions() {
        return true;
    }
}
