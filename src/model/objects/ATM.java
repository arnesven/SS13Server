package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
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
    private Map<Actor, MoneyStack> accounts = new HashMap<>();

    public ATM(GameData gameData, Room room) {
        super("ATM", room);
        this.gameData = gameData;

        for (Actor a : gameData.getActors()) {
            if (hasAnAccountFromStart(a)) {
                accounts.put(a, new MoneyStack(0));
            }
        }

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
        return accounts.containsKey(a);
    }

    private static boolean hasAnAccountFromStart(Actor actor) {
        return actor.getCharacter().isCrew() ||
                !actor.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof VisitorCharacter));
    }


    public MoneyStack getAccountForActor(Actor whosAsking) {
        return accounts.get(whosAsking);
    }

    public void addToAccount(Actor a, int wage) {
        if (!accounts.containsKey(a)) {
            accounts.put(a, new MoneyStack(0));
        }
        accounts.get(a).addTo(wage);
    }
}
