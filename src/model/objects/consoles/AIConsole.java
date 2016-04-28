package model.objects.consoles;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.objectactions.AIConsoleAction;
import model.actions.general.Action;
import model.characters.general.HorrorCharacter;
import model.items.NoSuchThingException;
import model.map.Room;
import model.npcs.ParasiteNPC;
import model.npcs.PirateNPC;
import model.objects.general.GameObject;

public class AIConsole extends Console {

	public AIConsole(Room pos) {
		super("AI Console", pos);
	}

	@Override
	public void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		at.add(new AIConsoleAction(this));
	}

    public List<String> getAlarms(GameData gameData) {
        List<String> alarms = new ArrayList<>();
        for (Room r : gameData.getRooms()) {
            if (r.hasFire()) {
                alarms.add("-->Fire alarm in " + r.getName() + ".");
            }
            if (r.hasHullBreach()) {
                alarms.add("-->Low pressure in " + r.getName() + ".");
            }
            for (Actor a : r.getActors()) {
                if (a instanceof PirateNPC) {
                    alarms.add("-->Pirate in " + r.getName() + ".");
                    break;
                }
            }
            for (Actor a : r.getActors()) {
                if (a.getCharacter() instanceof HorrorCharacter) {
                    alarms.add("-->Stalking Horror in " + r.getName() + ".");
                    break;
                }
            }
            int parCount = 0;
            for (Actor a : r.getActors()) {
                if (a instanceof ParasiteNPC) {
                    parCount++;
                }
            }
            if (parCount > 4) {
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
        return alarms;
    }

    public static AIConsole find(GameData gameData) throws NoSuchThingException {
        for (GameObject obj : gameData.getObjects()) {
            if (obj instanceof AIConsole) {
                return (AIConsole)obj;
            }
        }
        throw new NoSuchThingException("No AI Console found!");
    }
}
