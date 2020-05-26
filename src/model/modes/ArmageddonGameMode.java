package model.modes;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.events.SpontaneousExplosionEvent;
import model.events.ZombifierEvent;
import model.events.ambient.SpontaneousCrazyness;
import model.npcs.NPC;
import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by erini02 on 03/05/16.
 */
public class ArmageddonGameMode extends GameMode {

    private static final String PROT_MESSAGE = "Armageddon has come! Just try to survive!";
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
        c.addTolastTurnInfo(PROT_MESSAGE);
    }

    @Override
    public String getModeDescription() {
        return PROT_MESSAGE;
    }

    @Override
    protected String getImageURL() {
        return "https://www.ida.liu.se/~erini02/ss13/armageddon.jpg";
    }

    @Override
    public boolean isAntagonist(Actor c) {
        return false;
    }

    @Override
    public String getSummary(GameData gameData) {
        return new ArmageddonGameStats(this, gameData).toString();
    }

    @Override
    public Integer getPointsForPlayer(GameData gameData, Player value) {
        if (!value.isDead()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String getSpectatorSubInfo(GameData gameData) {
        StringBuffer buf = new StringBuffer();
        for (Actor a : zombieEvent.getZombies()) {
            buf.append(a.getBaseName() + ", ");
        }

        return "Zombies: " + buf.toString();
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

    @Override
    public String getAntagonistName(Player p) {
        return "Nobody";
    }

    @Override
    public Map<Player, NPC> getDecoys() {
        return new HashMap<>();
    }
}
