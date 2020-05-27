package model.modes;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.characters.special.AlienCharacter;
import model.characters.special.SpectatorCharacter;
import model.fancyframe.SinglePageFancyFrame;
import model.npcs.NPC;
import util.HTMLText;
import util.MyRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuntGameMode extends ScoredGameMode {

    private static final String PROT_MESSAGE = "There's an alien infestation on the station! Exterminate them and all eggs before you are overrun!";
    private final HashMap<Player, NPC> decoys;
    private static final double ALIEN_FACTOR = 1.0/5.0;
    private final List<Player> aliens;


    public HuntGameMode() {
        decoys = new HashMap<>();
        aliens = new ArrayList<Player>();
    }
    
    @Override
    protected int getModeSpecificPoints(GameData gameData) {
        // TODO:
        // Points for alive crew
        // Points for dead aliens
        // Points for eggs
        return 0;
    }

    @Override
    public Integer getPointsForPlayer(GameData gameData, Player value) {
        // TODO:
        // 2 points for original winning alien if survived
        // 1 point for winning player
        return 0;
    }


    @Override
    public String getName() {
        return "Hunt";
    }

    @Override
    protected void setUpOtherStuff(GameData gameData) {
        // TODO: maybe something?
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
        return decoys;
    }

    @Override
    protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
        List<Player> alienPlayers = new ArrayList<>();

        for (Player p : gameData.getPlayersAsList()) {
            if (p.checkedJob("Alien") &&
                    !(p.getCharacter() instanceof AICharacter) &&
                    !(p.getCharacter() instanceof SpectatorCharacter)) {
                alienPlayers.add(p);
            }
        }

        if (alienPlayers.size() < getNoOfAliens(gameData)) {
            throw new GameCouldNotBeStartedException("Could not play hunt mode. Too few aliens.");
        }

        while (alienPlayers.size() > getNoOfAliens(gameData)) {
            // Too many operatives, remove some.
            alienPlayers.remove(MyRandom.sample(alienPlayers));
        }

        for (int i = 0; i < getNoOfAliens(gameData) && i < alienPlayers.size(); ++i) {
            Player p = alienPlayers.get(i);

            // Turn Character into decoy-npc
            //p.getCharacter().setClient(null);
            NPC npc = super.makeDecoy(p, gameData);
            decoys.put(p, npc);
            GameCharacter alChar = new AlienCharacter(getRandomStartRoom(gameData));
            p.setCharacter(alChar);
            aliens.add(p);
        }
    }

    protected int getNoOfAliens(GameData gameData) {
        return (int)Math.ceil(gameData.getTargetablePlayers().size() * ALIEN_FACTOR);
    }


    @Override
    public boolean gameOver(GameData gameData) {
        return getGameResult(gameData) != null;
    }


    @Override
    public void setStartingLastTurnInfo() {
            for (Player p : aliens) {
                p.addTolastTurnInfo("You are an " + HTMLText.makeLink(HTMLText.wikiURL + "/modes/hunt", "alien") +
                        ". Kill humans, lay eggs and wreak havoc on the station! " +
                        "When players die, they will come back to life as an alien if there are eggs available." +
                        "Your decoy is " + decoys.get(p).getBaseName() +
                        " (in " + decoys.get(p).getPosition().getName() + ")");
            }
    }

    @Override
    protected void addAntagonistStartingMessage(Player c) {
        StringBuilder data = new StringBuilder( HTMLText.makeCentered(HTMLText.makeText("White", "<br/><b>You are an " +
                HTMLText.makeLink(HTMLText.wikiURL + "/modes/hunt", "alien") + "!</b><br/>") +
                HTMLText.makeText("White", "Kill humans, lay eggs and wreak havoc on the station! " +
                        "When players die, they will come back to life as an alien if there are eggs available.<br/>" +
                        "You can pretend to be the " + decoys.get(c).getBaseName() +
                        " (in " + decoys.get(c).getPosition().getName() + ")<br/>")));

        c.setFancyFrame(new SinglePageFancyFrame(c.getFancyFrame(), "Secret Role!", HTMLText.makeColoredBackground("Green", data.toString())));
    }

    @Override
    protected void addProtagonistStartingMessage(Player c) {
        c.addTolastTurnInfo(PROT_MESSAGE);
    }

    @Override
    protected String getModeDescription() {
        return "There's an alien infestation on the station. Can the crew exterminate it before they are overrun?";
    }

    @Override
    protected String getImageURL() {
        return "https://www.ida.liu.se/~erini02/ss13/aliens.jpg";
    }

    @Override
    public boolean isAntagonist(Actor c) {
        return aliens.contains(c);
    }

    @Override
    public String getSummary(GameData gameData) {
        return new HuntModeStats(gameData, this).toString();
    }


    public List<Player> getAliens() {
        return aliens;
    }
}
