package model.modes;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.crew.CaptainCharacter;
import model.characters.crew.HeadOfStaffCharacter;
import model.characters.general.GameCharacter;
import model.characters.visitors.CaptainsDaughter;
import model.fancyframe.SinglePageFancyFrame;
import util.HTMLText;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 04/12/16.
 */
public class MutinyGameMode extends GameMode {

    private static final String MUTINEER_MESSAGE = "You are a " +  HTMLText.makeLink(HTMLText.wikiURL + "/modes/mutiny", "<b>mutineer</b>") + "! Take over the station by killing the Captain. Everybody is in on the mutiny except for the Head of Staff and the Captain's Daughter.";
    private List<Actor> mutineers = new ArrayList<>();

    @Override
    public String getName() {
        return "Mutiny";
    }

    @Override
    protected void setUpOtherStuff(GameData gameData) {
        for (Actor a : gameData.getActors()) {
            if (a.getCharacter().isCrew()) {
                if (! a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof CaptainCharacter ||
                            gc instanceof CaptainsDaughter || gc instanceof HeadOfStaffCharacter)) {
                    mutineers.add(a);
                }
            }
        }
    }

     @Override
    protected void gameModeSpecificSetupForLateJoiner(Player newPlayer, GameData gameData) {
        mutineers.add(newPlayer);
    }

    @Override
    protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters,
                                    GameData gameData) {

    }


    protected GameOver getGameResult(GameData gameData) {
        if (gameData.isAllDead()) {
            return GameOver.ALL_DEAD;
        } else if (gameData.getRound() == gameData.getNoOfRounds()) {
            return GameOver.TIME_IS_UP;
        } else if (getCaptain().isDead()) {
            return GameOver.PROTAGONISTS_DEAD;
        }

        return null;
    }


    @Override
    public boolean gameOver(GameData gameData) {
        return getGameResult(gameData) != null;
    }

    @Override
    public void setStartingLastTurnInfo() {
        for (Actor a : mutineers) {
            a.addTolastTurnInfo(MUTINEER_MESSAGE);
        }
    }

    @Override
    protected void addAntagonistStartingMessage(Player c) {

        //c.addTolastTurnInfo(MUTINEER_MESSAGE);
        c.setFancyFrame(new SinglePageFancyFrame(c.getFancyFrame(), "Secret Role!", HTMLText.makeColoredBackground("#856509",
                HTMLText.makeCentered("<br/>" + HTMLText.makeImage(getCaptain().getCharacter().getSprite(c)) + "<br/><br/>" +
                        HTMLText.makeText("yellow", MUTINEER_MESSAGE))
                )));
    }

    @Override
    protected void addProtagonistStartingMessage(Player c) {
        String res = "There's a mutiny on the station! ";
        if (c.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof CaptainCharacter)) {
            res += "You must survive!";
        } else {
            res += "Protect the Captain!";
        }
        c.addTolastTurnInfo(res);
    }

    @Override
    public String getModeDescription() {
        return "There is a mutiny on the station! Everybody is in on it except the Captain, the Head of Staff and the Captain's daughter. To win, the crew must kill the captain!";
    }

    @Override
    public boolean isAntagonist(Actor c) {
        return mutineers.contains(c);
    }

    @Override
    public String getSummary(GameData gameData) {
        return (new MutinyGameStats(gameData, this)).toString();
    }

    @Override
    public Integer getPointsForPlayer(GameData gameData, Player value) {
        if (mutineers.contains(value) && getCaptain().isDead()) {
            return 1;
        } else if (!mutineers.contains(value) && !getCaptain().isDead()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String getSpectatorSubInfo(GameData gameData) {
        return "Mutineers left: " + countLiveMutineers();
    }

    private int countLiveMutineers() {
        int count = 0;
        for (Actor a : mutineers) {
            if (!a.isDead()) {
                count++;
            }
        }
        return count;
    }


    @Override
    public String getAntagonistName(Player p) {
        return "Mutineer";
    }
}
