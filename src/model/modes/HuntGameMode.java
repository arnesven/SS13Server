package model.modes;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.RespawnAsAlienAfterDeathDecorator;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.characters.special.AlienCharacter;
import model.characters.special.SpectatorCharacter;
import model.fancyframe.FancyFrame;
import model.fancyframe.SinglePageFancyFrame;
import model.npcs.NPC;
import model.objects.AlienEggObject;
import util.HTMLText;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuntGameMode extends ScoredGameMode {

    private static final String PROT_MESSAGE = "There's an alien infestation on the station! Exterminate them and all eggs before you are overrun!";
    private static final int POINTS_PER_AGE_LIVE_ALIEN = 10;
    private static final int POINTS_PER_EGG = 10;
    private static final int POINTS_FOR_EXTERMINATION = 300;
    private final HashMap<Player, NPC> decoys;
    private static final double ALIEN_FACTOR = 1.0/5.0;
    private final List<Player> originalAliens;
    private int originalNumberOfAliens;
    private int POINTS_PER_ALIEN_FOR_CREW = 500;


    public HuntGameMode() {
        decoys = new HashMap<>();
        originalAliens = new ArrayList<Player>();
    }
    
    @Override
    protected int getModeSpecificPoints(GameData gameData) {
        int result = 0;
        result += getPointsForAliveCrew(gameData);
        result += getPointsForAliveAliens(gameData);
        result += getPointsForEggs(gameData);
        result += getPointsForInfestationExterminated(gameData);
        return result;
    }

    public int getPointsForInfestationExterminated(GameData gameData) {
        if (AlienEggObject.getNoOfEggsOnStation(gameData) == 0 && getPointsForAliveAliens(gameData) == 0) {
            return POINTS_FOR_EXTERMINATION;
        }
        return 0;
    }

    public int getPointsForEggs(GameData gameData) {
        return -AlienEggObject.getNoOfEggsOnStation(gameData) * POINTS_PER_EGG;
    }

    public int getPointsForAliveAliens(GameData gameData) {
        int result = 0;
        for (Actor a : gameData.getActors()) {
            if (a.getInnermostCharacter() instanceof AlienCharacter && !a.isDead()) {
                AlienCharacter alChar = (AlienCharacter)a.getInnermostCharacter();
                result += alChar.getAge() * POINTS_PER_AGE_LIVE_ALIEN;
            }
        }

        return -result;
    }

    public int getPointsForAliveCrew(GameData gameData) {
        int result = 0;
        for (Actor a : gameData.getActors()) {
            if (a.isCrew() && !a.isDead()) {
                result += pointsPerAliveCrewMember(gameData);
            }
        }

        return result;
    }

    public int pointsPerAliveCrewMember(GameData gameData) {
        int noOfCrew = 0;
        for (Actor a : gameData.getActors()) {
            if (a.isCrew()) {
                noOfCrew++;
            }
        }
        return (originalNumberOfAliens * POINTS_PER_ALIEN_FOR_CREW) / noOfCrew;
    }

    @Override
    public Integer getPointsForPlayer(GameData gameData, Player value) {
        if (value.isDead()) {
            Logger.log(value.getBaseName() + " is dead and gets no points.");
            return 0;
        }
        int score = getScore(gameData);
        if (score > 0) { // CREW WINS
            if (isAntagonist(value)) {
                Logger.log(value.getBaseName() + " is losing alien and gets no points.");
                return 0;
            } else {
                Logger.log(value.getBaseName() + " is winning crew and gets 1 point.");
                return 1;
            }
        } else if (score < 0) {
            if (isAntagonist(value)) {
                if (originalAliens.contains(value)) {
                    Logger.log(value.getBaseName() + " is winning original alien and gets 2 points.");
                    return 2;
                } else {
                    Logger.log(value.getBaseName() + " is winning alien and gets 1 point.");
                    return 1;
                }
            } else {
                Logger.log(value.getBaseName() + " is losing crew and gets no points.");
                return 0;
            }
        }
        Logger.log(value.getBaseName() + " is surviving player in a draw game => 1 point.");
        return 1;
    }


    @Override
    public String getName() {
        return "Hunt";
    }

    @Override
    protected void setUpOtherStuff(GameData gameData) {
        for (Player p : gameData.getPlayersAsList()) {
            p.setCharacter(new RespawnAsAlienAfterDeathDecorator(p.getCharacter(), gameData, this));
        }
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
            throw new GameCouldNotBeStartedException("Could not play hunt mode. Too few originalAliens.");
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
            originalAliens.add(p);
        }
        originalNumberOfAliens = originalAliens.size();
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
            for (Player p : originalAliens) {
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
                HTMLText.makeImage(new Sprite("alienfficon", "alien2.png", 0, 19, 64, 32, null)) + "<br/>" +
                HTMLText.makeText("White", "Kill humans, lay eggs and wreak havoc on the station! " +
                        "When players die, they will come back to life as an alien if there are eggs available.<br/>" +
                        "You can pretend to be the " + decoys.get(c).getBaseName() + "<br/>" +
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
        return "https://www.ida.liu.se/~erini02/ss13/originalAliens.jpg";
    }

    @Override
    public boolean isAntagonist(Actor c) {
        return originalAliens.contains(c) || c.getInnermostCharacter() instanceof AlienCharacter;
    }

    @Override
    public String getSummary(GameData gameData) {
        return new HuntModeStats(gameData, this).toString();
    }


    public FancyFrame getJustDiedFancyFrame(Player c, GameData gameData) {
        StringBuilder data = new StringBuilder( HTMLText.makeCentered(HTMLText.makeText("White",
                HTMLText.makeText("White", "<b>You died!</b><br/><br/>But wait, what's that? There's light at the end of the tunnel!<br/>" +
                        "Hang tight and you might respawn as an alien.<br/><br/>" +
                "Current no. of eggs: " + AlienEggObject.getNoOfEggsOnStation(gameData)))));

        return new SinglePageFancyFrame(c.getFancyFrame(), "Secret Role!", HTMLText.makeColoredBackground("Green", data.toString()));

    }

    public FancyFrame getRespawnedFancyFrame(Player c) {
        StringBuilder data = new StringBuilder( HTMLText.makeCentered(HTMLText.makeText("White", "<br/><b>You are an " +
                HTMLText.makeLink(HTMLText.wikiURL + "/modes/hunt", "alien") + "!</b><br/>") +
                HTMLText.makeImage(new Sprite("alienfficon", "alien2.png", 0, 19, 64, 32, null)) + "<br/>" +
                HTMLText.makeText("White", "Kill humans, lay eggs and wreak havoc on the station! " +
                        "When players die, they will come back to life as an alien if there are eggs available.<br/>")));

        return new SinglePageFancyFrame(c.getFancyFrame(), "Secret Role!", HTMLText.makeColoredBackground("Green", data.toString()));
    }
}
