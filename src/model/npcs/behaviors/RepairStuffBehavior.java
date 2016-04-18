package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.events.Event;
import model.events.ambient.HullBreach;
import model.npcs.NPC;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;
import model.objects.general.Repairable;

/**
 * Created by erini02 on 18/04/16.
 */
public class RepairStuffBehavior implements ActionBehavior {
    public RepairStuffBehavior(GameData gameData) {

    }

    @Override
    public void act(NPC npc, GameData gameData) {
        boolean didFixBreach = fixHullBreach(npc, gameData);
        if (!didFixBreach) {
            boolean didReapairEq = fixDamagedEquipment(npc, gameData);
            if (!didReapairEq) {
                fixRepairableActor(npc, gameData);
            }
        }

    }

    private void fixRepairableActor(NPC npc, GameData gameData) {
        for (Actor a : npc.getPosition().getActors()) {
            if (a instanceof Repairable && ((Repairable) a).isDamaged()) {
                a.addToHealth(1.0);
                informPublic(npc, " repaired " + a.getPublicName() + ".");
            }
        }
    }

    private boolean fixDamagedEquipment(NPC npc, GameData gameData) {
        for (GameObject ob : npc.getPosition().getObjects()) {
            if (ob instanceof BreakableObject) {
                if (((BreakableObject)ob).isDamaged() || ((BreakableObject)ob).isBroken()) {
                    ((BreakableObject)ob).addToHealth(1.0);
                    informPublic(npc, " repaired the " + ((BreakableObject) ob).getName());
                    return true;
                }
            }
        }
        return false;
    }

    private boolean fixHullBreach(NPC npc, GameData gameData) {
        if (npc.getPosition().hasHullBreach()) {
            for (Event e : npc.getPosition().getEvents()) {
                if (e instanceof HullBreach) {
                    ((HullBreach) e).fix();
                    informPublic(npc, " sealed the hull breach.");
                    return true;
                }
            }
        }
        return false;
    }

    private void informPublic(NPC npc, String s) {
        for (Actor a : npc.getPosition().getActors()) {
            if (a != npc) {
                a.addTolastTurnInfo(npc.getPublicName() + " " + s);
            }
        }
    }
}
