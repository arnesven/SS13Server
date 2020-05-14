package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.Bank;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.AccessAccountAction;
import model.actions.objectactions.WalkUpToATMAction;
import model.actions.objectactions.WalkUpToElectricalMachineryAction;
import model.events.PayWagesEvent;
import model.fancyframe.FancyFrame;
import model.fancyframe.SinglePageFancyFrame;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.items.general.MoneyStack;
import model.items.keycard.IdentCardItem;
import model.map.rooms.Room;
import model.objects.SinglePersonUseMachine;
import model.objects.general.ElectricalMachinery;
import util.Logger;

import java.util.ArrayList;

/**
 * Created by erini02 on 15/11/16.
 */
public class ATM extends SinglePersonUseMachine implements BankUser {
    private final GameData gameData;
    private Bank bank;
    private IdentCardItem insertedCard;
    private MoneyStack moneyShowing;
    private boolean ejectedCard;

    public ATM(GameData gameData, Room room) {
        super("ATM", room);
        this.gameData = gameData;
        insertedCard = null;
        ejectedCard = false;

    }

    public void setBankRef(Bank b) {
        this.bank = b;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("atm", "computer.png", 143, this);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
//        if (hasAnAccount(cl)) {
//            at.add(new AccessAccountAction(this));
//        } else {
//            Logger.log(cl.getPublicName() + " DOESN'T HAVE AN ACCOUNT!");
//        }
        if (cl instanceof Player) {
            at.add(new WalkUpToATMAction((Player)cl, gameData, ATM.this));
        }
    }

    public boolean hasAnAccount(Actor a) {
        return bank.getAccounts().containsKey(a);
    }


    public MoneyStack getAccountForActor(Actor whosAsking) {
        return bank.getAccounts().get(whosAsking);
    }

    public void addToAccount(Actor a, int wage) {
        if (!bank.getAccounts().containsKey(a)) {
            bank.getAccounts().put(a, new MoneyStack(0));
        }
        bank.getAccounts().get(a).addTo(wage);
    }

    public void insertCard(IdentCardItem card) {
        this.insertedCard = card;
    }

    public IdentCardItem getInsertedCard() {
        return insertedCard;
    }

    public IdentCardItem removeCard() {
        IdentCardItem cardToReturn = insertedCard;
        insertedCard = null;
        ejectedCard = false;
        return cardToReturn;
    }

    public int getBalanceForInsertedCardPerson() {
        return getAccountForActor(insertedCard.getBelongsTo()).getAmount();
    }

    public void setMoneyShowing(MoneyStack moneyStack) {
        this.moneyShowing = moneyStack;
    }

    public MoneyStack getMoneyShowing() {
        return moneyShowing;
    }

    public void setEjectedCard(boolean b) {
        this.ejectedCard = b;
    }

    public boolean isCardEjected() {
        return ejectedCard;
    }
}
