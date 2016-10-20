package model.objects.consoles;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.objectactions.AIConsoleAction;
import model.actions.general.Action;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.characters.general.HorrorCharacter;
import model.characters.general.ParasiteCharacter;
import model.items.NoSuchThingException;
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

public class AIConsole extends Console {

    private boolean shutDown = false;
    private boolean corrupt = false;

    public AIConsole(Room pos) {
		super("AI Console", pos);
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
            at.add(new AIConsoleAction(this));
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
        if (!isCorrupt() && !isShutDown()) {
            for (Room r : gameData.getRooms()) {
                for (Actor a : r.getActors()) {
                    a.addTolastTurnInfo("AI; \"" + s + "\"");
                }
            }
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
}
