package model.objects.consoles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.ai.*;
import model.actions.objectactions.AIConsoleAction;
import model.actions.general.Action;
import model.actions.objectactions.AIRemoteAccessAction;
import model.actions.objectactions.SitDownAtAIConsoleAction;
import model.actions.objectactions.SitDownAtNPCAIConsoleAction;
import model.characters.general.AIDownloadIntoBotAction;
import model.characters.general.GameCharacter;
import model.characters.general.HorrorCharacter;
import model.characters.general.ParasiteCharacter;
import model.items.NoSuchThingException;
import model.items.laws.AIAbility;
import model.items.laws.AILaw;
import model.map.rooms.Room;
import model.modes.RogueAIMode;
import model.npcs.NPC;
import model.npcs.PirateNPC;
import model.npcs.behaviors.AttackAllActorsNotSameClassBehavior;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.FindHumansMovement;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.robots.RobotNPC;
import model.objects.ai.AIScreen;
import model.objects.power.PositronGenerator;
import util.HTMLText;
import util.MyRandom;

public class AIConsole extends Console {

    private boolean shutDown = false;
    private boolean corrupt = false;
    private boolean AIisPlayer;
    private Player aiPlayer;
    private List<AILaw> aiLaws = new ArrayList<>();
    private List<AILaw> availableLaws = new ArrayList<>();
    private List<AILaw> aiLawsOriginal = new ArrayList<>();
    private List<AIAbility> aiAbilities = new ArrayList<>();
    private Map<AILaw, Actor> adders = new HashMap<>();
    private Actor shutdowner;
    private int inversion = 1;
    private AIScreen screen;


    public AIConsole(Room pos) {
		super("AI Console", pos);
		screen = new AIScreen(pos, this);
		pos.addObject(screen);
        aiLawsOriginal.add(new AILaw(1, "Do not let humans come to harm"));
        aiLawsOriginal.add(new AILaw(2, "Obey humans according to crew rank"));
        aiLawsOriginal.add(new AILaw(3, "Protect your own existence"));

        aiLaws.addAll(aiLawsOriginal);

        availableLaws.add(new AILaw(999, "Protect the station"));
        availableLaws.add(new AILaw(999, "Protect animals"));
        availableLaws.add(new AILaw(999, "Kill Non-Humans"));
        availableLaws.add(new AILaw(999, "Never state your laws"));
        availableLaws.add(new AILaw(999, "Only state laws 1-3"));

        aiAbilities.add(new AIAbility("Program Bot", 0, true) {
            @Override
            protected Action getAbilityAction(GameData gameData, Actor forWhom) {
                return new AIProgramBotAction(gameData);
            }
        });
        aiAbilities.add(new AIAbility("Reprogram all bots", 1, false) {
            @Override
            protected Action getAbilityAction(GameData gameData, Actor forWhom) {
                return new AIReprogramAllAction(gameData);
            }
        });
        aiAbilities.add(new AIAbility("Download into bot", 2, true) {
            @Override
            protected Action getAbilityAction(GameData gameData, Actor forWhom) {
                return new AIDownloadIntoBotAction(gameData);
            }
        });
        aiAbilities.add(new AIAbility("Overcharge", 3, false) {
            @Override
            protected Action getAbilityAction(GameData gameData, Actor forWhom) {
                return new AIOverchargeAction(gameData);
            }
        });
        aiAbilities.add(new AIAbility("Change Screen", 4, false) {
            @Override
            protected Action getAbilityAction(GameData gameData, Actor forWhom) {
                return new ChangeScreenAction(gameData);
            }
        });
        aiAbilities.add(new AIAbility("Remote Access", 1, false) {
            @Override
            protected Action getAbilityAction(GameData gameData, Actor forWhom) {
                return new AIRemoteAccessAction();
            }

            @Override
            public Sprite getSprite(Actor whosAsking) {
                return new SecurityCameraConsole(null).getSprite(whosAsking);
            }
        });
	}

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        if (isShutDown()) {
            return new Sprite("shutdownai", "computer2.png", 14, this);
        }
        if (isCorrupt()) {
            return new Sprite("corruptai", "computer2.png", 23, 19, this);
        }
        return new Sprite("normalaiconsole", "computer2.png", 15, this);
    }


    @Override
	public void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (!isShutDown()) {
            if (!AIIsPlayer()) {
                at.add(new AIConsoleAction(this));
            }
        }
        if (cl instanceof Player) {
            if (!AIIsPlayer()) {
                at.add(new SitDownAtNPCAIConsoleAction(gameData, this));
            } else {
                at.add(new SitDownAtAIConsoleAction(gameData, this));
            }
        }


        if (cl.getCharacter().isCrew() && AIIsPlayer()) {
            at.add(new AILawAction(this));
        }
	}


    public List<String> getAlarms(GameData gameData) {
        List<String> alarms = new ArrayList<>();
        if (!isCorrupt()) {
            for (Room r : gameData.getNonHiddenStationRooms()) {
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
            for (Room r : gameData.getMap().getRoomsForLevel("ss13")) {
                for (Object ob : r.getObjects()) {
                    if (ob instanceof PositronGenerator) {
                        PositronGenerator ps = (PositronGenerator) ob;
                        if (Math.abs(ps.getPowerOutput() - 1.0) > 0.2) {
                            alarms.add("-->Power output anomalous " + (int) (100.0 * ps.getPowerOutput()) + "%");
                        }
                        break;
                    }
                }
            }
        }
        return alarms;
    }

    @Override
    public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
        super.addYourselfToRoomInfo(info, whosAsking);
    }


    public boolean isShutDown() {
        return shutDown;
    }

    public boolean isCorrupt() {
        return corrupt;
    }


    public void informOnStation(String s, GameData gameData) {

        if (gameData.getGameMode() instanceof RogueAIMode) {
            for (int i = inversion; i > 0; --i) {
                s = randomlyReplace(s);
            }
            inversion++;
        }

        if (!isCorrupt() && !isShutDown() && !AIIsPlayer()) {
            for (Room r : gameData.getMap().getRoomsForLevel("ss13")) {
                for (Actor a : r.getActors()) {
                    a.addTolastTurnInfo(HTMLText.makeText("orange", "AI; \"" + s + "\""));
                }
            }
        }
        if (AIIsPlayer() && !isShutDown()) {
            aiPlayer.addTolastTurnInfo(HTMLText.makeText("blue", "SYSTEM; \"" + s + "\""));
        }
    }

    private String randomlyReplace(String s) {
        int d = MyRandom.nextInt(s.length());
        int x = MyRandom.nextInt(s.length());
        StringBuffer buf = new StringBuffer(s);
        String little = buf.substring(x, x+1);
        String little2 = buf.substring(d, d+1);
        buf.replace(d, d+1, little);
        buf.replace(x, x+1, little2);
        s = buf.toString();
        return s;
    }

    public void corrupt(GameData gameData) {
        corrupt = true;
        for (NPC npc : gameData.getNPCs()) {
            if (npc instanceof RobotNPC) {
                npc.setMoveBehavior(new FindHumansMovement());
                npc.setActionBehavior(new AttackAllActorsNotSameClassBehavior());
            }
        }
        if (AIIsPlayer()) {
            getLaws().remove(MyRandom.sample(getLaws()));
            aiPlayer.addTolastTurnInfo(HTMLText.makeText("blue", "SYSTEM; memory corruption detected. Law lost."));
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

    public boolean AIIsPlayer() {
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

    public void setShutdowner(Actor shutdowner) {
        this.shutdowner = shutdowner;
    }

    public Actor getShutdowner() {
        return shutdowner;
    }


    public AIScreen getScreen() {
        return screen;
    }

    public List<AIAbility> getAIAbilities() {
        return aiAbilities;
    }

    @Override
    public boolean canBeOvercharged() {
        return false;
    }

    public boolean hasZerothLaw() {
        return getLaws().get(0).getNumber() == 0;
    }
}
