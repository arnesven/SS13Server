package model.objects.consoles;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.AILawAction;
import model.actions.objectactions.AIConsoleAction;
import model.actions.general.Action;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.characters.general.HorrorCharacter;
import model.characters.general.ParasiteCharacter;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.laws.AILaw;
import model.map.Room;
import model.npcs.NPC;
import model.npcs.ParasiteNPC;
import model.npcs.PirateNPC;
import model.npcs.behaviors.AttackAllActorsNotSameClassBehavior;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.FindHumansMovement;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.robots.RobotNPC;
import model.objects.general.GameObject;
import util.HTMLFont;

public class AIConsole extends Console {

    private boolean shutDown = false;
    private boolean corrupt = false;
    private boolean AIisPlayer;
    private Player aiPlayer;
    private List<AILaw> aiLaws = new ArrayList<>();
    private List<AILaw> availableLaws = new ArrayList<>();
    private List<AILaw> aiLawsOriginal = new ArrayList<>();
    private Map<AILaw, Actor> adders = new HashMap<>();


    public AIConsole(Room pos) {
		super("AI Console", pos);
        aiLawsOriginal.add(new AILaw(1, "Do not let humans come to harm"));
        aiLawsOriginal.add(new AILaw(2, "Obey humans according to crew rank"));
        aiLawsOriginal.add(new AILaw(3, "Protect your own existence"));

        aiLaws.addAll(aiLawsOriginal);

        availableLaws.add(new AILaw(999, "Protect the station"));
        availableLaws.add(new AILaw(999, "Protect animals"));
        availableLaws.add(new AILaw(999, "Kill Non-Humans"));
        availableLaws.add(new AILaw(999, "Never state your laws"));
        availableLaws.add(new AILaw(999, "Only state laws 1-3"));
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isShutDown()) {
            return new Sprite("shutdownai", "computer2.png", 14);
        }
        if (isCorrupt()) {
            return new Sprite("corruptai", "computer2.png", 23, 19);
        }
        return super.getSprite(whosAsking);
    }


    @Override
	public void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
        if (!isShutDown()) {
            if (!AIIsPlayer()) {
                at.add(new AIConsoleAction(this));
            } else {
                if (cl.getCharacter().isCrew()) {
                    at.add(new AILawAction(this));
                }
                //at.add(new AILawAction(this));
                //            -> Delete law
                //            -> Add Zeroth Law
                //            -> Add Nth Law
                //at.add(new AIPullFuseAction(this)
            }
        }
	}



    public List<String> getAlarms(GameData gameData) {
        List<String> alarms = new ArrayList<>();
        if (!isCorrupt()) {
            for (Room r : gameData.getRooms()) {
                if (r.hasFire()) {
                    alarms.add("-->Fire alarm in " + r.getName() + ".");
                }
                if (r.hasHullBreach()) {
                    alarms.add("-->Low pressure in " + r.getName() + ".");
                }
                for (Actor a : r.getActors()) {
                    if (a instanceof PirateNPC && !a.isDead()) {
                        alarms.add("-->Pirate in " + r.getName() + ".");
                        break;
                    }
                }
                for (Actor a : r.getActors()) {
                    if (a.getCharacter().checkInstance((GameCharacter cha) -> cha instanceof HorrorCharacter)) {
                        alarms.add("-->Stalking Horror in " + r.getName() + ".");
                        break;
                    }
                }
                int parCount = 0;
                for (Actor a : r.getActors()) {
                    if (a.getCharacter().checkInstance((GameCharacter cha) -> cha instanceof ParasiteCharacter)) {
                        parCount++;
                    }
                }
                if (parCount > 2) {
                    alarms.add("-->Parasite infestation in " + r.getName() + ".");
                }
            }
            for (Object ob : gameData.getObjects()) {
                if (ob instanceof GeneratorConsole) {
                    GeneratorConsole gc = (GeneratorConsole) ob;
                    if (Math.abs(gc.getPowerOutput() - 1.0) > 0.2) {
                        alarms.add("-->Power output anomalous " + (int) (100.0 * gc.getPowerOutput()) + "%");
                    }
                    break;
                }
            }
        }
        return alarms;
    }

    public boolean isShutDown() {
        return shutDown;
    }

    public boolean isCorrupt() {
        return corrupt;
    }


    public void informOnStation(String s, GameData gameData) {
        if (!isCorrupt() && !isShutDown() && !AIIsPlayer()) {
            for (Room r : gameData.getRooms()) {
                for (Actor a : r.getActors()) {
                    a.addTolastTurnInfo(HTMLFont.makeText("orange", "AI; \"" + s + "\""));
                }
            }
        }
        if (AIIsPlayer() && !isShutDown()) {
            aiPlayer.addTolastTurnInfo(HTMLFont.makeText("blue", "SYSTEM; \"" + s + "\""));
        }
    }

    public void corrupt(GameData gameData) {
        corrupt = true;
        for (NPC npc : gameData.getNPCs()) {
            if (npc instanceof RobotNPC) {
                npc.setMoveBehavior(new FindHumansMovement());
                npc.setActionBehavior(new AttackAllActorsNotSameClassBehavior());
            }
        }
    }

    public void shutDown(GameData gameData) {
        this.shutDown = true;
        for (NPC npc : gameData.getNPCs()) {
            if (npc instanceof RobotNPC) {
                npc.setActionBehavior(new DoNothingBehavior());
                npc.setMoveBehavior(new MeanderingMovement(0.0));
            }
        }
    }

    public void setAIisPlayer(boolean AIisPlayer) {
        this.AIisPlayer = AIisPlayer;
    }

    private boolean AIIsPlayer() {
        return AIisPlayer;
    }

    public List<AILaw> getLaws() {
        return aiLaws;
    }

    public int getHighestLaw() {
        int max = 0;
        for ( AILaw l : aiLaws ) {
            if ( max < l.getNumber() ) {
                max = l.getNumber();
            }
        }
        return max;
    }

    public List<AILaw> getAvailableLaws() {
        return availableLaws;
    }

    public void addCustomLawToAvailable(String set) {
        if (!set.equals("")) {
            availableLaws.add(new AILaw(999, set));
        }
    }

    public void setAIPlayer(Player AIPlayer) {
        this.aiPlayer = AIPlayer;
    }

    public Player getAIPlayer() {
        return aiPlayer;
    }

    public void addLaw(AILaw aiLaw, Actor adder) {
        if (aiLaw.getNumber() == 0) {
            aiLaws.add(0, aiLaw);
        } else {
            aiLaws.add(aiLaw);
        }
        adders.put(aiLaw, adder);
    }

    public void deleteLawByName(String rest) {
        int i = 0;
        for (AILaw law : getLaws()) {
            if (law.getBaseName().equals(rest)) {
                break;
            }
            i++;
        }
        if (i < getLaws().size()) {
            aiLaws.remove(i);
        }
    }

    public List<AILaw> getOriginalLaws() {
        return aiLawsOriginal;
    }

    public Actor getActorForLaw(String rest) throws NoSuchThingException {
        for (AILaw law : getLaws()) {
            if (law.getBaseName().equals(rest)) {
                return adders.get(law);
            }
        }
        throw new NoSuchThingException("No Actor for law!");
    }
}
