package model;

import model.characters.general.GameCharacter;
import model.characters.visitors.VisitorCharacter;
import model.events.PayWagesEvent;
import model.items.general.MoneyStack;
import model.map.rooms.Room;
import model.modes.GameMode;
import model.objects.general.BankUser;
import model.objects.general.GameObject;
import util.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by erini02 on 17/11/16.
 */
public class Bank implements Serializable {

    private static GameMode gameModeRef;
    private static Bank instance = null;
    private Map<Actor, MoneyStack> accounts = new HashMap<>();
    private int stationMoney;

    public Bank(GameData gameData) {
        Logger.log("New bank instance creater, number of actors " + gameData.getActors().size());
        for (Actor a : gameData.getActors()) {
            if (a.getCharacter() != null) {
                if (hasAnAccountFromStart(a)) {
                    Logger.log("Setting up acount for an actor with char " + a.getBaseName());
                    accounts.put(a, new MoneyStack(0));
                }
            }
        }
        stationMoney = 14000;
        gameData.addEvent(new PayWagesEvent());
        for (Room r : gameData.getNonHiddenStationRooms()) {
            for (GameObject obj : r.getObjects()) {
                if (obj instanceof BankUser) {
                    ((BankUser) obj).setBankRef(this);
                }
            }
        }
    }

    private static boolean hasAnAccountFromStart(Actor actor) {
        return actor.isCrew() && !actor.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof VisitorCharacter));
    }

    public Map<Actor, MoneyStack> getAccounts() {
        return accounts;
    }

    public void setStationMoney(int stationMoney) {
        this.stationMoney = stationMoney;
    }

    public int getStationMoney() {
        return stationMoney;
    }

    public void subtractFromStationMoney(int amount) {
        stationMoney -= amount;
    }

    public void addToStationMoney(int cost) {
        stationMoney += cost;
    }

    public void addToAccount(Actor a, int wage) {
        MoneyStack s = accounts.get(a);
        if (s != null) {

            accounts.get(a).addTo(wage);
        }
    }

    public void addAccount(Actor actor) {
        if (hasAnAccountFromStart(actor)) {
            accounts.put(actor, new MoneyStack(0));
        }
    }
}
