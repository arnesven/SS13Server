package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.Bank;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.AccessAccountAction;
import model.events.PayWagesEvent;
import model.items.general.MoneyStack;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;
import java.util.ArrayList;

/**
 * Created by erini02 on 15/11/16.
 */
public class ATM extends ElectricalMachinery {
    private final GameData gameData;
    private final Bank bank;

    public ATM(GameData gameData, Room room) {
        super("ATM", room);
        this.gameData = gameData;

        this.bank = Bank.getInstance(gameData);


    }


    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("atm", "computer.png", 58, this);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (hasAnAccount(cl)) {
            at.add(new AccessAccountAction(this));
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
}
