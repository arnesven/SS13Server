package model.modes;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.GodModeDecorator;
import model.characters.general.GameCharacter;
import model.events.Event;

import java.util.ArrayList;

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
    }

    @Override
    public String getSpectatorSubInfo(GameData gameData) {
        return "";
    }

    @Override
    protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
        for (Player p : gameData.getPlayersAsList()) {
            p.setCharacter(new GodModeDecorator(p.getCharacter(), gameData));
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
}
