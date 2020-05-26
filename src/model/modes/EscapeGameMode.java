package model.modes;

import model.Actor;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import model.characters.escape.EscapeAlienCharacter;
import model.characters.escape.EscapeCrewCharacter;
import model.characters.general.GameCharacter;
import model.npcs.NPC;
import util.MyRandom;

import java.util.*;

public class EscapeGameMode extends GameMode {
    private List<Actor> humans;
    private List<Actor> aliens;

    public EscapeGameMode() {
        this.humans = new ArrayList<>();
        this.aliens = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "Escape from the Aliens";
    }

    @Override
    protected void setUpOtherStuff(GameData gameData) {
        getEvents().clear();
        for (Player p : gameData.getPlayersAsList()) {
            p.getSettings().set(PlayerSettings.ACTIVATE_MOVEMENT_POWERS, false);
        }
    }

    @Override
    protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
        List<Player> players = new ArrayList<>();
        players.addAll(gameData.getPlayersAsList());

        int noOfAliens = players.size() / 2;
        for (int i = noOfAliens; i > 0; --i) {
            Player alien = MyRandom.sample(players);
            aliens.add(alien);
            players.remove(alien);
            alien.setCharacter(new EscapeAlienCharacter());
        }
        humans.addAll(players);
        for (Actor p : humans) {
            p.setCharacter(new EscapeCrewCharacter());
        }

    }

    @Override
    protected void addNPCs(GameData gameData, List<GameCharacter> remainingChars) {
        // No NPCs in this mode
    }

    @Override
    protected void selectAIPlayer(ArrayList<Player> listOfClients, GameData gameData) {
        // No AI in this mode...
    }

    @Override
    protected void addStuffToRooms(GameData gameData) {
        // No extra stuff in the rooms
    }

    @Override
    protected void addPersonalGoals(GameData gameData) {
        // No personal goals.
    }

    @Override
    public boolean gameOver(GameData gameData) {
        return false;
    }

    @Override
    public void setStartingLastTurnInfo() {

    }

    @Override
    protected void addAntagonistStartingMessage(Player c) {
        c.addTolastTurnInfo("You are an alien. Find the humans and kill them before they reach the escape pods!");
    }

    @Override
    protected void addProtagonistStartingMessage(Player c) {
        c.addTolastTurnInfo("You are a human. Reach an escape pod before the aliens kill you!");
    }

    @Override
    public String getModeDescription() {
        return "A deprecated game mode...";
    }

    @Override
    protected String getImageURL() {
        return "";
    }

    @Override
    public boolean isAntagonist(Actor c) {
        return aliens.contains(c);
    }

    @Override
    public String getSummary(GameData gameData) {
        return "";
    }

    @Override
    public Integer getPointsForPlayer(GameData gameData, Player value) {
        return 0;
    }

    @Override
    public String getSpectatorSubInfo(GameData gameData) {
        return "";
    }

    @Override
    public String getAntagonistName(Player p) {
        return "Alien";
    }

    @Override
    public Map<Player, NPC> getDecoys() {
        return new HashMap<>();
    }
}
