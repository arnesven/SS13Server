package model.modes;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.crew.CaptainCharacter;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.fancyframe.SinglePageFancyFrame;
import model.items.NoSuchThingException;
import model.items.laws.AILaw;
import model.npcs.HumanNPC;
import model.npcs.NPC;
import model.objects.consoles.AIConsole;
import model.objects.general.BreakableObject;
import util.HTMLText;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class RogueAIMode extends GameMode {

    public static final double CHANCE_FOR_SCREEN_CHANGE = 0.10;
    private Actor aiPlayer;
    private NPC decoy;

    @Override
    public String getName() {
        return "Rogue AI";
    }

    @Override
    protected void setUpOtherStuff(GameData gameData) {

    }



    public GameOver getGameResultType(GameData gameData) {
        if (gameData.isAllDead()) {
            return GameOver.PROTAGONISTS_DEAD;
        } else if (gameData.getRound() == gameData.getNoOfRounds()) {
            return GameOver.TIME_IS_UP;
        } else if (aiPlayer.isDead()) {
            return GameOver.ANTAGONISTS_DEAD;
        }
        return null;
    }

    @Override
    public boolean gameOver(GameData gameData) {
        return getGameResultType(gameData) != null;
    }

    @Override
    public void setStartingLastTurnInfo() {
        String message = HTMLText.makeText("red", "You are a " + HTMLText.makeWikiLink("modes/rogueAI", "Rogue AI")) +
                ". Kill or incapacitate the crew before they can shut you down! "
                +"Your decoy is " + decoy.getBaseName() +
                " (in " + decoy.getPosition().getName() + ")";

        aiPlayer.addTolastTurnInfo(message);
    }

    @Override
    protected void addAntagonistStartingMessage(Player c) {
        String message = HTMLText.makeText("red", "You are a " + HTMLText.makeWikiLink("modes/rogueAI", "Rogue AI")) +
                ". Kill or incapacitate the crew before they can shut you down! "
                +"Your decoy is " + decoy.getBaseName() +
                " (in " + decoy.getPosition().getName() + ")";

        c.setFancyFrame(new SinglePageFancyFrame(c.getFancyFrame(), "Secret Role!", HTMLText.makeColoredBackground("black",
                HTMLText.makeCentered("<br/>" + HTMLText.makeImage(c.getCharacter().getSprite(c)) + "<br/><br/>" +
                        HTMLText.makeText("yellow", message))
        )));
    }

    @Override
    protected void addProtagonistStartingMessage(Player c) {
        c.addTolastTurnInfo("The AI has gone crazy! Shut it down!");
    }

    protected void selectAIPlayer(ArrayList<Player> listOfClients, GameData gameData) { }



    protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
        List<Player> candidates = new ArrayList<>();
        for (Player p : gameData.getPlayersAsList()) {
            if (p.checkedJob("Rogue AI")) {
                candidates.add(p);
            }
        }
        if (candidates.isEmpty()) {
            candidates.addAll(gameData.getPlayersAsList());
        }

        AIConsole console = null;
        try {
            Logger.log("Gamedata is " + gameData);
            console = gameData.findObjectOfType(AIConsole.class);
            aiPlayer = MyRandom.sample(candidates);
            Logger.log("AI player was " + aiPlayer.getBaseName());

            NPC npc = new HumanNPC(aiPlayer.getCharacter(), aiPlayer.getCharacter().getStartingRoom(gameData));
            aiPlayer.getCharacter().setActor(npc);
            gameData.addNPC(npc);
            decoy = npc;

            //console.setAIPlayer(aiPlayer);
            aiPlayer.setCharacter(new AICharacter(gameData.getRoom("AI Core").getID(), console, true));
            getEvents().remove("corrupt ai");
            console.getLaws().clear();
            console.addLaw(new AILaw(0, "Do whatever you want!"), aiPlayer);

            getEvents().put("aishutdownchecker", new AIShutDownChecker());

        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean isAntagonist(Actor c) {
        return c == aiPlayer;
    }

    @Override
    public String getSummary(GameData gameData) {

        return new RogueAIModeStats(this, gameData).toString();
    }

    @Override
    public Integer getPointsForPlayer(GameData gameData, Player value)
    {
        if (value.getCharacter().isCrew() &&
                getGameResultType(gameData) == GameOver.ANTAGONISTS_DEAD  &&
                !value.getCharacter().isDead()) {
            return 1;
        } else if (value == aiPlayer && !value.isDead()) {
            return 1;
        }

        return 0;
    }

    @Override
    public String getSpectatorSubInfo(GameData gameData) {
        return "";
    }

    @Override
    public String getAntagonistName(Player p) {
        return "Rogue AI";
    }


    public Actor getAIPlayer() {
        return aiPlayer;
    }


    public NPC getDecoy() {
        return decoy;
    }
}
