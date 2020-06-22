package model.modes;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.GodModeDecorator;
import model.characters.decorators.NPCCommanderDecorator;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.items.NoSuchThingException;
import model.npcs.NPC;
import model.npcs.ParasiteNPC;
import util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by erini02 on 17/12/16.
 */
public class CreativeGameMode extends GameMode {
    @Override
    public String getName() {
        return "Creative";
    }

    @Override
    protected void setUpOtherStuff(GameData gameData) {
        for (Event e : getEvents().values()) {
            e.setProbability(0);
        }
    }

    @Override
    protected void spawnParasites(GameData gameData) {
        // no parasites in this game mode.
//        NPC parasite = null;
//        try {
//            parasite = new ParasiteNPC(gameData.getMap().getRoom("Bridge"));
//            gameData.addNPC(parasite);
//            allParasites.add(parasite);
//        } catch (NoSuchThingException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public String getSpectatorSubInfo(GameData gameData) {
        return "";
    }

    @Override
    protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
        for (Player p : gameData.getPlayersAsList()) {
            p.setCharacter(new GodModeDecorator(p.getCharacter(), gameData));
            p.setCharacter(new NPCCommanderDecorator(p.getCharacter()));
        }
    }

    @Override
    public boolean gameOver(GameData gameData) {
        return gameData.isAllDead();
    }

    @Override
    public void setStartingLastTurnInfo() {

    }

    @Override
    protected void addAntagonistStartingMessage(Player c) {

    }

    @Override
    protected void addProtagonistStartingMessage(Player c) {

    }

    @Override
    public String getModeDescription() {
        return "You are the Captain and everybody else. You've got all the gear. Do what you want!";
    }

    @Override
    protected String getImageURL() {
        return "https://justsomething.co/wp-content/uploads/2014/04/creative-people.jpg";
    }

    @Override
    public boolean isAntagonist(Actor c) {
        return false;
    }

    @Override
    public String getSummary(GameData gameData) {
        return (new CreativeModeStats(gameData, this)).toString();
    }

    @Override
    public Integer getPointsForPlayer(GameData gameData, Player value) {
        return 0;
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
