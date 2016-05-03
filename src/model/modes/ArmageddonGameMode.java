package model.modes;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.events.SpontaneousExplosionEvent;
import model.events.ZombifierEvent;
import model.events.ambient.SpontaneousCrazyness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by erini02 on 03/05/16.
 */
public class ArmageddonGameMode extends GameMode {

    private ZombifierEvent zombieEvent;

    @Override
    public String getName() {
        return "Armageddon";
    }

    @Override
    protected void setUpOtherStuff(GameData gameData) {
        for (Event e : getEvents().values()) {
            if (e instanceof SpontaneousCrazyness || e instanceof SpontaneousExplosionEvent) {
                e.setProbability(e.getProbability() * 5.0);
            } else {
                e.setProbability(e.getProbability() * 2.0);
            }
        }
        zombieEvent = new ZombifierEvent();
        gameData.addEvent(zombieEvent);

    }

    @Override
    protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters,
                                    GameData gameData) {

    }

    public GameOver getGameResultType(GameData gameData) {
        if (gameData.isAllDead()) {
            return GameOver.ALL_DEAD;
        } else if (gameData.getRound() == gameData.getNoOfRounds()) {
            return GameOver.TIME_IS_UP;
        }
        return null;
    }

        @Override
    public boolean gameOver(GameData gameData) {
        return getGameResultType(gameData) != null;
    }

    @Override
    public void setStartingLastTurnInfo() {
        // none
    }

    @Override
    protected void addAntagonistStartingMessage(Player c) {
        // No antagonists in this game mode
    }

    @Override
    protected void addProtagonistStartingMessage(Player c) {
        c.addTolastTurnInfo("Armageddon has come! Just try to survive!");
    }

    @Override
    protected boolean isAntagonist(Player c) {
        return false;
    }

    @Override
    public String getSummary(GameData gameData) {
        return new ArmageddonGameStats(this, gameData).toString();
    }

    public ZombifierEvent getZombieEvent() {
        return zombieEvent;
    }

    public String getZombieKiller() {
        Map<String, Integer> map = new HashMap<>();
        for (Actor a : zombieEvent.getZombies()) {
            if (a.isDead()) {
                String key = a.getCharacter().getKillerString();
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + 1);
                } else if (key != null) {
                    map.put(key, 0);
                }
            }
        }


        int max = 0;
        String maxName = "Nobody";
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                maxName = e.getKey();
            }
        }
        return maxName;
    }
}
