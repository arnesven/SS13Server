package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.AttackAction;
import model.npcs.NPC;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/04/16.
 */
public class AttackAllActorsNotSameClassBehavior extends AttackIfPossibleBehavior {
    @Override
    protected List<Target> getTargets(Actor npc, GameData gameData, AttackAction atk) {
        List<Target> targets = new ArrayList<Target>();
        targets.addAll(atk.getTargets());

        for (Target t : atk.getTargets()) {
            if (t instanceof Actor) {
                Actor targetAsActor = (Actor)t;
                if (npc.getClass().isInstance(targetAsActor)) {
                    targets.remove(t);
                }
            } else {
                targets.remove(t);
            }
        }
        return targets;
    }
}
