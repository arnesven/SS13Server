package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.Bank;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.AccessAccountAction;
import model.characters.general.GameCharacter;
import model.characters.visitors.VisitorCharacter;
import model.events.PayWagesEvent;
import model.items.general.MoneyStack;
import model.map.Room;
import model.objects.general.ElectricalMachinery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        gameData.addEvent(new PayWagesEvent(gameData, this));
    }


    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("atm", "computer.png", 58);
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
