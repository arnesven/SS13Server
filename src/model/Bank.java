package model;

import model.characters.general.GameCharacter;
import model.characters.visitors.VisitorCharacter;
import model.items.general.MoneyStack;
import model.modes.GameMode;
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

    private Bank(GameData gameData) {
        Logger.log("New bank instance creater, number of actors " + gameData.getActors().size());
        for (Actor a : gameData.getActors()) {
            Logger.log("Setting up acount for an actor with char " + a.getCharacter());
            if (a.getCharacter() != null) {
                if (hasAnAccountFromStart(a)) {
                    accounts.put(a, new MoneyStack(0));
                }
            }
        }
        stationMoney = 14000;
    }

    public static Bank getInstance(GameData gameData) {
        if (instance == null || gameModeRef != gameData.getGameMode()) {
            instance = new Bank(gameData);
            gameModeRef = gameData.getGameMode();
            return instance;
        }
        return instance;
    }

    private static boolean hasAnAccountFromStart(Actor actor) {
        return actor.getCharacter().isCrew() ||
                !actor.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof VisitorCharacter));
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
}
